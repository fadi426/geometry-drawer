package model;

import java.awt.*;

public class UnselectCommand implements Command {

    private Shape shape;

    public UnselectCommand(Shape shape){
        this.shape = shape;
    }

    @Override
    public void Execute() {
        shape.setColor(Color.BLACK);
    }

    @Override
    public void Undo() {
        shape.setColor(Color.RED);
    }

    @Override
    public void Redo() {
        shape.setColor(Color.BLACK);
    }
}
