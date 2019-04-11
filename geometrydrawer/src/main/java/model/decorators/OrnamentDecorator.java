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
    public void draw(Graphics g) {
        decoratedFigure.draw(g);
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(Color currentColor) {
    }

    @Override
    public boolean contain(Point point) {
        return false;
    }

    @Override
    public void accept(Visitor v) {
    }

    /**
     * @return the current ornament
     */
    public Figure getOrnament() {
        return ornament;
    }

    /**
     * Build the current ornament with its text and positionreference,
     * check if text is filled in before you continue.
     */
    private void createOrnament() {
        text = setText();
        while ((text.length() == 0)) {
            text = setText();
        }
        position = setPosition();
    }

    /**
     * Trigger a input dialog from the Jpanel object canvas
     * @return text from the dialog
     */
    private String setText() {
        return JOptionPane.showInputDialog(canvas, "Ornament Text");
    }

    /**
     * Trigger a dropdown dialog from the Jpanel object canvas
     * @return position from the dialog
     */
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
