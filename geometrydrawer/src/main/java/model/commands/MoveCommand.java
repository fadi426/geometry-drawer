package model.commands;

import controller.CanvasController;
import model.shapes.Group;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private List<Shape> shapes;
    private List<Shape> flatShapes;
    private List<List<Point>> points;
    private List<Shape> groupList;

    public MoveCommand(List<Shape> shapes){
        this.shapes = shapes;
        flatShapes = new ArrayList<>();
        points = new ArrayList<>();
        groupList = new ArrayList<>();
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
            for (Ornament ornament : shape.getOrnaments()) {
                shape.updateOrnament(ornament);
            }
        }

        for (Shape shape : groupList) {
            Group group = (Group) shape;
            group.CalculateBoundary();
            for (Ornament ornament : shape.getOrnaments()) {
                shape.updateOrnament(ornament);
            }
        }
    }

    @Override
    public void Redo() {
        for (int i = 0; i< flatShapes.size(); i++) {
            Shape shape = flatShapes.get(i);
            shape.setShapeStart(points.get(i).get(0));
            shape.setShapeEnd(points.get(i).get(1));
            for (Ornament ornament : shape.getOrnaments()) {
                shape.updateOrnament(ornament);
            }
        }

        for (Shape shape : groupList) {
            Group group = (Group) shape;
            group.CalculateBoundary();
            for (Ornament ornament : shape.getOrnaments()) {
                shape.updateOrnament(ornament);
            }
        }
    }

    public void flatMap(List<Shape> shapes){
        for (Shape s : shapes){
            if (s.getSubShapes().size() > 0){
                groupList.add(s);
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
