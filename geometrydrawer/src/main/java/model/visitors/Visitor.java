package model.visitors;

import model.shapes.Figure;
import model.shapes.Shape;

public interface Visitor {
    public void visit(Shape shape);
}
