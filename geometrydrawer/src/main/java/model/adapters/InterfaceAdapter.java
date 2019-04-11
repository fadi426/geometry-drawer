package model.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    /**
     * Trasforms Java object to Json Object
     * @param object is a java object
     * @param interfaceType is the common superinterface for all types in the Java
     * @param context is the Json element
     * @return the current member that is being built
     */
    @Override
    public final JsonElement serialize(final T object, final Type interfaceType, final JsonSerializationContext context) {
        final JsonObject member = new JsonObject();

        member.addProperty("type", object.getClass().getName());
        member.add("data", context.serialize(object));

        return member;
    }

    /**
     * Transforms Json object to Java object
     * @param elem is the Json object
     * @param interfaceType is the common superinterface for all types in the Java
     * @param context is the Json element
     * @return a Java object
     * @throws JsonParseException
     */
    @Override
    public final T deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) elem;
        final JsonElement typeString = get(member, "type");
        final JsonElement data = get(member, "data");
        final Type actualType = typeForName(typeString);

        return context.deserialize(data, actualType);
    }

    /**
     * Searches for the typeName of a figure to use it in the gson serializer
     * @param typeElem the className of the figure
     * @return the classType
     */
    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * Gets the Json element from the complete Json
     * @param wrapper is the Json wrapper
     * @param memberName the name of the JsonObject
     * @return the specific JsonObject in the complete Json
     */
    private JsonElement get(final JsonObject wrapper, final String memberName) {

        final JsonElement elem = wrapper.get(memberName);

        if (elem == null) {
            throw new JsonParseException(
                    "no '" + memberName + "' member found in json file.");
        }
        return elem;
    }

}
