package model.decorators;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;

public class OrnamentDecorator extends ShapeDecorator {

    private Ornament ornament;
    private CanvasController canvas;

    public OrnamentDecorator(Figure decoratedShape) {
        super(decoratedShape);
        this.canvas = SingletonCanvas.getInstance();
    }

    public void addOrnament(Figure figure, String value, String position) {
        ornament = new Ornament(value, position, figure);
        canvas.listmodel.addElement(ornament);
        canvas.mainGroup.addFigure(ornament);
    }

    public void deleteOrnament(){
        canvas.listmodel.removeElement(ornament);
        canvas.mainGroup.removeFigure(ornament);
    }
}
