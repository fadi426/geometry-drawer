package model.visitors;

import model.shapes.Figure;

public interface Visitor {
    /**
     * Visits the given shape to accept to visitor
     * @param figure The shape to visit
     */
    void visit(Figure figure);
}
