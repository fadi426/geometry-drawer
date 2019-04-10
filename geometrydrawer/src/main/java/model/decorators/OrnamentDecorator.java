package model.decorators;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;
import model.visitors.Visitor;

import javax.swing.*;
import java.awt.*;

public class OrnamentDecorator extends FigureDecorator {

    private Ornament ornament;
    private CanvasController canvas;
    private String text;
    private String position;

    public OrnamentDecorator(Figure decoratedShape) {
        super(decoratedShape);
        this.canvas = SingletonCanvas.getInstance();
        createOrnament();
        ornament = new Ornament(text, position, decoratedShape);
    }

    @Override
    public void draw(Graphics g){

        decoratedFigure.draw(g);
    }

    @Override
    public void fill(Graphics g) {

    }

    @Override
    public void setColor(Color currentColor) {

    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean contain(Point point) {
        return false;
    }

    @Override
    public void accept(Visitor v) {

    }

    public Figure getOrnament(){
        return ornament;
    }
    private void createOrnament(){
        text = setText();
        while ((text.length() == 0)){
            text = setText();
        }
        position = setPosition();
    }

    private String setText(){
        return JOptionPane.showInputDialog(canvas, "Ornament Text");
    }

    private String setPosition() {
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
