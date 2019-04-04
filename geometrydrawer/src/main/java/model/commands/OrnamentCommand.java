package model.commands;

import controller.CanvasController;
import model.decorators.OrnamentDecorator;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OrnamentCommand implements Command {

    private CanvasController canvas;
    private List<Shape> shapes;
    private List<OrnamentDecorator> ornaments;
    private String text;
    private String position;

    public OrnamentCommand(){
        this.canvas = SingletonCanvas.getInstance();
        this.shapes = new ArrayList<>();
        this.ornaments = new ArrayList<>();
        shapes.addAll(canvas.getSelectedShapes());
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
        DefaultListModel<Shape> listmodel = canvas.listmodel;

        if(shapes.size() == 0)
            return;

        for (Shape s : shapes) {
            if (listmodel.contains(s))
                listmodel.removeElement(s);

            ornaments.add(new OrnamentDecorator(s, text, position));
            listmodel.addElement(s);
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
