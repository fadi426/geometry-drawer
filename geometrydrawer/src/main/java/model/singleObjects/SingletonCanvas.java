package model.singleObjects;

import controller.CanvasController;

import java.awt.*;

public class SingletonCanvas {
    private static CanvasController instance;

    /**
     * Gets the instance of the CanvasController.
     * @return Returns the CanvasController.
     */
    public static CanvasController getInstance() {
        if (instance == null) {
            instance = new CanvasController(Color.WHITE);
        }
        return instance;
    }
}
