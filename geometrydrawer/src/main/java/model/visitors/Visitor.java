package model.visitors;

import model.shapes.Shape;

public interface Visitor {
    void visit(Shape shape);
}
