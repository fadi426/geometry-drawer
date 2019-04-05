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

public class MoveCommand implements Command {
    private List<Figure> figures;
    private List<Shape> flatShapes;
    private List<List<Point>> points;
    private List<Figure> groupList;

    public MoveCommand(List<Figure> figures){
        this.figures = figures;
        flatShapes = new ArrayList<>();
        points = new ArrayList<>();
        groupList = new ArrayList<>();
    }

    @Override
    public void Execute() {
        flatMap(figures);
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

    public void flatMap(List<Figure> figures){
        for (Figure f : figures){
            if (f instanceof Group){
                Group group = (Group) f;
                groupList.add(f);
                flatMap(group.getSubShapes());
            }
            else {
                Shape shape = (Shape) f;
                flatShapes.add(shape);
                List<Point> temp_point = new ArrayList<>();
                temp_point.add(shape.getShapeStart());
                temp_point.add(shape.getShapeEnd());
                points.add(temp_point);
            }
        }
    }
}
