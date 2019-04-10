package model.visitors;

public interface Visitable {

    void accept(Visitor v);
}
