package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UnselectCommand implements Command {

    private Figure figure;
    private CanvasController canvas;
    private List<Figure> ornaments;

    public UnselectCommand(Figure figure) {
        this.figure = figure;
        canvas = SingletonCanvas.getInstance();
        ornaments = new ArrayList<>();
    }

    @Override
    public void Execute() {
        figure.setColor(Color.BLACK);
        canvas.removeFromSelected(figure);
        deleteOrnaments();
    }

    @Override
    public void Undo() {
        figure.setColor(Color.RED);
        canvas.addToSelected(figure);
        addOrnaments();
    }

    @Override
    public void Redo() {
        figure.setColor(Color.BLACK);
        canvas.addToSelected(figure);
        deleteOrnaments();
    }

    /**
     * Find all the ornaments that belong to the selected shapes
     */
    private void findOrnaments() {
        for (Figure f : canvas.toList()) {
            if (f instanceof Ornament) {
                Ornament ornament = (Ornament) f;
                if (ornament.getParent() == figure) {
                    ornaments.add(ornament);
                }
            }
        }
    }

    /**
     * Add the selected ornaments to the selected list from the canvas
     */
    private void addOrnaments() {
        ornaments.clear();
        findOrnaments();
        if (ornaments.size() == 0)
            return;

        for (Figure o : ornaments) {
            o.setColor(Color.RED);
            canvas.addToSelected(o);
        }
    }

    /**
     * Delete the ornaments from the selected list of the canvas
     */
    private void deleteOrnaments() {
        if (ornaments.size() == 0)
            return;

        for (Figure o : ornaments) {
            o.setColor(Color.BLACK);
            canvas.addToSelected(o);
        }
    }
}
