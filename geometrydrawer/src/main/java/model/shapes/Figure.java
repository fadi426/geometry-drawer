package model.shapes;

import model.visitors.Visitable;
import model.visitors.Visitor;

import java.awt.*;

public interface Figure extends Visitable {

    /**
     * Execute specific draw function for the figure
     * @param g Graphics object from the object Jpanel
     */
    void draw(Graphics g);

    /**
     * @return get the current color of the figure
     */
    Color getColor();

    /**
     * Set the current color of the figure
     * @param currentColor Color object to determine the color of the edges of the figure
     */
    void setColor(Color currentColor);

    /**
     * Checks if the current position of the mouse is inside the figure
     * @param point is from the Mouse Adapter object and is the current position of the mouse on the JPanel
     * @return a boolean with true if the current position of the mouse is inside figure, and false if this isn't the case
     */
    boolean contain(Point point);

    /**
     * Visits the figure from the Visitor interface
     * @param v references to the Visitor object
     */
    void accept(Visitor v);

    /**
     * Overrides the Java toString method to return a custom message when triggered
     * @return a custom message for every individual figure
     */
    @Override
    String toString();
}
