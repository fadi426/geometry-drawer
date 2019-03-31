package model.commands;

import model.shapes.Shape;

import java.awt.*;

public class SelectCommand implements Command {

    private model.shapes.Shape shape;

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
