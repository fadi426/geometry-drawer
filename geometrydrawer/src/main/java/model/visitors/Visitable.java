package model.visitors;

public interface Visitable {

    /**
     * Aceept the visitor that wants to get the
     * reference of the object so it can do stuff with it
     * @param v The visitor to accept
     */
    void accept(Visitor v);
}
