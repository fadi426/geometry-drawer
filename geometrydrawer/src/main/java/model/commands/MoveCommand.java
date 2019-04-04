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
    private List<Figure> flatShapes;
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
        for (Figure figure : flatShapes) {
            figure.setShapeStart(figure.getPreviousShapeStart());
            figure.setShapeEnd(figure.getPreviousShapeEnd());
//            for (Ornament ornament : figure.getOrnaments()) {
//                figure.updateOrnament(ornament);
//            }
        }

//        for (Shape figure : groupList) {
//            Group group = (Group) figure;
//            group.CalculateBoundary();
//            for (Ornament ornament : figure.getOrnaments()) {
//                figure.updateOrnament(ornament);
//            }
//        }
    }

    @Override
    public void Redo() {
        for (int i = 0; i< flatShapes.size(); i++) {
            Figure figure = flatShapes.get(i);
            figure.setShapeStart(points.get(i).get(0));
            figure.setShapeEnd(points.get(i).get(1));
//            for (Ornament ornament : figure.getOrnaments()) {
//                figure.updateOrnament(ornament);
//            }
        }

//        for (Shape figure : groupList) {
//            Group group = (Group) figure;
//            group.CalculateBoundary();
//            for (Ornament ornament : figure.getOrnaments()) {
//                figure.updateOrnament(ornament);
//            }
//        }
    }

    public void flatMap(List<Figure> figures){
        for (Figure f : figures){
            if (f instanceof Group){
                Group group = (Group) f;
                groupList.add(f);
                flatMap(group.getSubShapes());
            }
            else {
                flatShapes.add(f);
                List<Point> temp_point = new ArrayList<>();
                temp_point.add(f.getShapeStart());
                temp_point.add(f.getShapeEnd());
                points.add(temp_point);
            }
        }
    }
}
