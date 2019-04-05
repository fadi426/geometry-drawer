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
    private List<Figure> figures;
    private OrnamentDecorator ornament;
    private List<OrnamentDecorator> ornaments = new ArrayList<>();
    private String text;
    private String position;

    public OrnamentCommand(){
        this.canvas = SingletonCanvas.getInstance();
        this.figures = new ArrayList<>();
        figures.addAll(canvas.getSelectedShapes());
    }

    @Override
    public void Execute() {
        CreateOrnament();
        addOrnament();
    }

    @Override
    public void Undo() {
       for (OrnamentDecorator od : ornaments){
           od.deleteOrnament();
       }
    }

    @Override
    public void Redo() {
        for (int i = 0; i < figures.size(); i++){
            ornaments.get(i).addOrnament(figures.get(i), text, position);
        }
    }

    private void addOrnament(){
        if(figures.size() == 0)
            return;

            for (Figure f : figures){
                if (f instanceof Ornament)
                    continue;

                ornament = (new OrnamentDecorator(f));
                ornament.addOrnament(f, text, position);
                ornaments.add(ornament);
            }
        canvas.repaint();
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
