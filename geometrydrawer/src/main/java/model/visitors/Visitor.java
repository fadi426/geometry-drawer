package model.visitors;

import model.shapes.Shape;

public interface Visitor {
    /**
     * Visits the given shape to accept to visitor
     * @param shape The shape to visit
     */
    void visit(Shape shape);
}
