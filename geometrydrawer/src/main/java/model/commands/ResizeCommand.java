package model.commands;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResizeCommand implements Command {
    private List<Shape> shapes;
    private List<Shape> flatShapes;
    private CanvasController canvas;
    private List<Point> points;

    public ResizeCommand(List<Shape> shapes){
        this.shapes = shapes;
        flatShapes = new ArrayList<>();
        points = new ArrayList<>();
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        flatMap(shapes);
    }

    @Override
    public void Undo() {
        for (Shape shape : flatShapes) {
            shape.setShapeEnd(shape.getPreviousShapeEnd());
        }
    }

    @Override
    public void Redo() {
        for (int i = 0; i< flatShapes.size(); i++) {
            Shape shape = flatShapes.get(i);
            shape.setShapeEnd(points.get(i));
        }
    }

    public void flatMap(List<Shape> shapes){
        for (Shape s : shapes){
            if (s.getSubShapes().size() > 0){
                flatMap(s.getSubShapes());
            }
            else {
                flatShapes.add(s);
                points.add(s.getShapeEnd());
            }
        }
    }
}
