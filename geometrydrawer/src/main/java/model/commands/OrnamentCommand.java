package model.commands;

import controller.CanvasController;
import model.decorators.OrnamentDecorator;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OrnamentCommand implements Command {

    private CanvasController canvas;
    private List<Figure> selectedFigures;
    private List<Figure> oldFigures = new ArrayList<>();
    private List<Figure> newFigures = new ArrayList<>();
    private String text;
    private String position;

    public OrnamentCommand(){
        this.canvas = SingletonCanvas.getInstance();
        this.selectedFigures = new ArrayList<>();
        selectedFigures.addAll(canvas.getSelectedShapes());
    }

    @Override
    public void Execute() {
        CreateOrnament();
        addOrnament();
    }

    @Override
    public void Undo() {
        oldFigures.clear();
        oldFigures.addAll(newFigures);
        for (Figure figure : newFigures){
            OrnamentDecorator ornamentDecorator = (OrnamentDecorator) figure;
            canvas.removeElementFromList((ornamentDecorator.getOrnament()));
        }
    }

    @Override
    public void Redo() {
        for (Figure figure : oldFigures){
            OrnamentDecorator ornamentDecorator = (OrnamentDecorator) figure;
            canvas.addElementToList((ornamentDecorator.getOrnament()));
        }
    }

    private void addOrnament(){
        if(selectedFigures.size() == 0)
            return;

        for (Figure f : selectedFigures){
            if (f instanceof Ornament)
                continue;

            Figure figure = new OrnamentDecorator(f, text, position);
            newFigures.add(figure);
            Ornament ornament = (Ornament) ((OrnamentDecorator) figure).getOrnament();
            canvas.addElementToList(ornament);
        }
    }

    private void CreateOrnament(){
        text = SetText();
        position = SetPosition();

        if ((text == null) || (text.length() == 0) || position == null || position.length() == 0) {
            return;
        }
    }

    private String SetText(){
        return JOptionPane.showInputDialog(canvas, "Ornament Text");
    }

    private String SetPosition() {
        Object[] possibilities = {"top", "bottom", "left", "right"};

        String position = (String) JOptionPane.showInputDialog(
                canvas,
                "Ornament Position",
                "Ornament Position Chooser",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "top");

        return position;
    }
}
