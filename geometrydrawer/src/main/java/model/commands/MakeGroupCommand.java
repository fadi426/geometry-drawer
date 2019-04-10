package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Group;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MakeGroupCommand implements Command {

    private List<Figure> figures = new ArrayList<>();
    private CanvasController canvas;
    private Group group;

    public MakeGroupCommand(List<Figure> figures) {
        this.figures.addAll(figures);
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        group = new Group();
        group.addFigures(figures);
        group.setColor(Color.BLACK);

        RemoveShapes();

        canvas.addElementToList(group);
    }

    @Override
    public void Undo() {
        canvas.removeLastElement();

        for (Figure f : figures) {
            canvas.addElementToList(f);
        }
    }

    @Override
    public void Redo() {
        RemoveShapes();

        canvas.addElementToList(group);
    }

    private void RemoveShapes() {
        List<Figure> mainGroupShapes = canvas.getMainGroup().getSubShapes();
        List<Figure> listmodel = canvas.toList();

        for (Figure f : figures) {
            if (listmodel.contains(f) && mainGroupShapes.contains(f)) {
                canvas.listmodel.removeElement(f);
                canvas.mainGroup.removeFigure(f);
            }
        }
    }
}
