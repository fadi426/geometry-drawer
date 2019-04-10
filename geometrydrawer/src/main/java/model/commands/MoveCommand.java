package model.commands;

import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private List<Figure> figures;
    private List<Shape> flatShapes;
    private List<List<Point>> previousPoints;

    private List<List<Point>> newPoints;

    public MoveCommand(List<Figure> figures) {
        this.figures = figures;
        flatShapes = new ArrayList<>();
        previousPoints = new ArrayList<>();
        newPoints = new ArrayList<>();
    }

    @Override
    public void Execute() {
        flatMap(figures);
    }

    @Override
    public void Undo() {
        newPoints.clear();
        for (int i = 0; i < flatShapes.size(); i++) {
            Shape shape = flatShapes.get(i);
            newPoints.add(createCurrentPoisition(shape));
            shape.setStartPoint(previousPoints.get(i).get(0));
            shape.setEndPoint(previousPoints.get(i).get(1));
        }
    }

    @Override
    public void Redo() {
        for (int i = 0; i < flatShapes.size(); i++) {
            Shape shape = flatShapes.get(i);
            shape.setStartPoint(newPoints.get(i).get(0));
            shape.setEndPoint(newPoints.get(i).get(1));
        }
        System.out.println("Redo MOVE");
    }

    /**
     * Flatten the selected shapes
     * @param figures the figures to flatten
     */
    public void flatMap(List<Figure> figures) {
        for (Figure f : figures) {
            if (f instanceof Group) {
                Group group = (Group) f;
                flatMap(group.getSubShapes());
            }
            if (f instanceof Shape) {
                Shape shape = (Shape) f;
                flatShapes.add(shape);
                previousPoints.add(createCurrentPoisition(shape));
            }
        }
    }

    /**
     * Create list of 2 points that holds the current start and end point of the shape given.
     * @param shape the shape create a position of
     * @return A list of 2 points
     */
    public List<Point> createCurrentPoisition(Shape shape) {
        List<Point> temp_points = new ArrayList<>();
        temp_points.add(shape.getStartPoint());
        temp_points.add(shape.getEndPoint());
        return temp_points;
    }
}
