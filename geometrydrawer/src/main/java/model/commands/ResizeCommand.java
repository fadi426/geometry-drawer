package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResizeCommand implements Command {
    private List<Figure> figures;
    private List<Shape> flatShapes;
    private CanvasController canvas;
    private List<Point> points;
    private List<Figure> groupList;

    public ResizeCommand(List<Figure> figures){
        this.figures = figures;
        flatShapes = new ArrayList<>();
        points = new ArrayList<>();
        groupList = new ArrayList<>();
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        flatMap(figures);
    }

    @Override
    public void Undo() {
        for (Shape shape : flatShapes) {
            shape.setShapeEnd(shape.getPreviousShapeEnd());
        }

        for (Figure figure : groupList) {
        }
    }

    @Override
    public void Redo() {
        for (int i = 0; i< flatShapes.size(); i++) {
            Shape shape = flatShapes.get(i);
            shape.setShapeEnd(points.get(i));
        }
    }

    public void flatMap(List<Figure> figures){
        for (Figure f : figures){
            if (f instanceof Group){
                Group group = (Group) f;
                flatMap(group.getSubShapes());
                groupList.add(f);
            }
            else {
                Shape shape = (Shape) f;
                flatShapes.add(shape);
                points.add(shape.getShapeEnd());
            }
        }
    }
}
