package model.singleObjects;

import model.Mouse;

public class SingleMouse {
    private static Mouse instance;

    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }
}
