package model.commands;

import model.shapes.Figure;
import model.shapes.Shape;

import java.awt.*;

public class UnselectCommand implements Command {

    private Figure figure;

    public UnselectCommand(Figure figure){
        this.figure = figure;
    }

    @Override
    public void Execute() {
        figure.setColor(Color.BLACK);
    }

    @Override
    public void Undo() {
        figure.setColor(Color.RED);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.BLACK);
    }
}
