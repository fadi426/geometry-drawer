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

    public void addOrnament(Figure figure, String text, String position) {
        ornament = new Ornament(text, position, figure);
        canvas.listmodel.addElement(ornament);
    }

    public void deleteOrnament(){
        canvas.listmodel.removeElement(ornament);
    }
}
