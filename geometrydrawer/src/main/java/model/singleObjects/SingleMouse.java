package model.singleObjects;

import model.adapters.Mouse;

public class SingleMouse {
    private static Mouse instance;

    /**
     * Gets the instance of the Mouse
     * @return Returns the Mouse.
     */
    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }
}
