package model.commands;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private List<Shape> shapes;
    private List<Shape> flatShapes;
    private List<List<Point>> points;

    public MoveCommand(List<Shape> shapes){
        this.shapes = shapes;
        flatShapes = new ArrayList<>();
        points = new ArrayList<>();
    }

    @Override
    public void Execute() {
        flatMap(shapes);
    }

    @Override
    public void Undo() {
        for (Shape shape : flatShapes) {
            shape.setShapeStart(shape.getPreviousShapeStart());
            shape.setShapeEnd(shape.getPreviousShapeEnd());
        }
    }

    @Override
    public void Redo() {
        for (int i = 0; i< flatShapes.size(); i++) {
            Shape shape = flatShapes.get(i);
            shape.setShapeStart(points.get(i).get(0));
            shape.setShapeEnd(points.get(i).get(1));
        }
    }

    public void flatMap(List<Shape> shapes){
        for (Shape s : shapes){
            if (s.getSubShapes().size() > 0){
                flatMap(s.getSubShapes());
            }
            else {
                flatShapes.add(s);
                List<Point> temp_point = new ArrayList<>();
                temp_point.add(s.getShapeStart());
                temp_point.add(s.getShapeEnd());
                points.add(temp_point);
            }
        }
    }
}
