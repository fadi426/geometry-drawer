package model.commands;

import controller.CanvasController;
import model.shapes.Group;
import model.shapes.Ornament;
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
    private List<Shape> groupList;

    public ResizeCommand(List<Shape> shapes){
        this.shapes = shapes;
        flatShapes = new ArrayList<>();
        points = new ArrayList<>();
        groupList = new ArrayList<>();
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
            shape.setShapeEnd(points.get(i));
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
                flatMap(s.getSubShapes());
                groupList.add(s);
            }
            else {
                flatShapes.add(s);
                points.add(s.getShapeEnd());
            }
        }
    }
}
