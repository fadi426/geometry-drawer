package model.commands;

import controller.CanvasController;
import model.decorators.OrnamentDecorator;
import model.shapes.Figure;
import model.singleObjects.SingletonCanvas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OrnamentCommand implements Command {

    private CanvasController canvas;
    private List<Figure> figures;
    private List<OrnamentDecorator> ornaments;
    private String text;
    private String position;

    public OrnamentCommand(){
        this.canvas = SingletonCanvas.getInstance();
        this.figures = new ArrayList<>();
        this.ornaments = new ArrayList<>();
        figures.addAll(canvas.getSelectedShapes());
    }

    @Override
    public void Execute() {
        CreateOrnament();
        addOrnament();
    }

    @Override
    public void Undo() {
        for (OrnamentDecorator o : ornaments){
            o.deleteOrnament();
        }
        ornaments.clear();
    }

    @Override
    public void Redo() {
        addOrnament();
    }

    private void addOrnament(){
        DefaultListModel<Figure> listmodel = canvas.listmodel;

        if(figures.size() == 0)
            return;

        for (Figure f : figures) {
            if (listmodel.contains(f))
                listmodel.removeElement(f);

            ornaments.add(new OrnamentDecorator(f, text, position));
            listmodel.addElement(f);
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
