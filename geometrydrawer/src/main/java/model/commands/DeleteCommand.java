package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Group;
import model.singleObjects.SingletonCanvas;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements Command {
    CanvasController canvas;
    List<Figure> oldFigures;

    public DeleteCommand(){
        canvas = SingletonCanvas.getInstance();
        oldFigures = new ArrayList<>();
    }
    @Override
    public void Execute() {
        oldFigures.clear();
        oldFigures.addAll(canvas.getMainGroup().getSubShapes());
        canvas.setCanvasLists(deleteSelected(canvas.toList()));
    }

    @Override
    public void Undo() {
        canvas.setCanvasLists(oldFigures);
    }

    @Override
    public void Redo() {
        oldFigures.clear();
        oldFigures.addAll(canvas.getMainGroup().getSubShapes());
        canvas.setCanvasLists(deleteSelected(canvas.toList()));
    }

    private List<Figure> deleteSelected(List<Figure> figures) {
        Group newGroup = new Group();
        for (Figure figure : figures) {

            newGroup.addFigure(figure);
            if (canvas.getSelectedShapes().contains(figure)) {
                newGroup.removeFigure(figure);
                continue;
            }

            if (figure instanceof Group) {
                Group g = (Group) figure;
                newGroup.addFigure(g);
                List<Figure> temp_figures = deleteSelected(g.getSubShapes());
                g.clear();
                g.addFigures(temp_figures);
            }
        }
        return newGroup.getSubShapes();
    }
}
