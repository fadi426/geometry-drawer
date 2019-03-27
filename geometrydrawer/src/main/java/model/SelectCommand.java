package model;

import java.awt.*;

public class SelectCommand implements Command {

    private Shape shape;

    public SelectCommand(Shape shape){
        this.shape = shape;
    }

    @Override
    public void Execute() {
        shape.setColor(Color.RED);
    }

    @Override
    public void Undo() {
        shape.setColor(Color.BLACK);
    }

    @Override
    public void Redo() {
        shape.setColor(Color.RED);
    }
}
