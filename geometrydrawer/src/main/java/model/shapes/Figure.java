package model.shapes;

import model.visitors.Visitor;

import java.awt.*;

public interface Figure {

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
}
