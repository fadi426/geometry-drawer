package model.visitors;

import model.shapes.Figure;
import model.shapes.Shape;

public interface Visitor {
    /**
     * Visits the given shape to accept to visitor
     * @param figure The shape to visit
     */
    void visit(Figure figure);
}
