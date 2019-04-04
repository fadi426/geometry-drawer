package model.commands;

import model.shapes.Figure;
import model.shapes.Shape;

import java.awt.*;

public class SelectCommand implements Command {

    private Figure figure;

    public SelectCommand(Figure figure){
        this.figure = figure;
    }

    @Override
    public void Execute() {
        figure.setColor(Color.RED);
    }

    @Override
    public void Undo() {
        figure.setColor(Color.BLACK);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.RED);
    }
}
