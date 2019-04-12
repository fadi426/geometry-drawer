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
    List<Figure> newFigures;
    List<Figure> oldSelected;


    public DeleteCommand(){
        canvas = SingletonCanvas.getInstance();
        oldFigures = new ArrayList<>();
        oldSelected = new ArrayList<>();
        newFigures = new ArrayList<>();
    }
    @Override
    public void Execute() {
        oldFigures.addAll(canvas.getMainGroup().getSubShapes());
        oldSelected.addAll(canvas.getSelectedShapes());
        canvas.setCanvasLists(deleteSelected(canvas.toList()));
        newFigures.addAll(canvas.getMainGroup().getSubShapes());
    }

    @Override
    public void Undo() {
        canvas.setCanvasLists(oldFigures);
        canvas.setSelectedShapes(oldSelected);
    }

    @Override
    public void Redo() {
        canvas.setCanvasLists(newFigures);
    }

    /**
     * Deletes the figures in the selectedShapes map inside the canvas from the mainGroup of the canvas
     * @param figures is the listmodel
     * @return a new list without the elements from the selectedList
     */
    private List<Figure> deleteSelected(List<Figure> figures) {
        Group newGroup = new Group();
        for (Figure figure : figures) {

            newGroup.addFigure(figure);
            if (canvas.getSelectedShapes().contains(figure)) {
                newGroup.removeFigure(figure);
            }
        }
        return newGroup.getSubShapes();
    }
}
