package model;

import controller.CanvasController;
import model.shapes.Shape;
import model.visitors.MoveVisitor;
import model.visitors.ResizeVisitor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse extends MouseAdapter implements MouseListener, MouseMotionListener {

    String operation;
    CanvasController canvas;

    public void setCanvas(CanvasController canvas) {
        this.canvas = canvas;
    }

    public String getOperation(){
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
            switch (operation) {
                case "draw":
                    canvas.draw(e);
                    break;
                case "select":
                    canvas.selectShape(e);
                    break;
        }
        canvas.currentX = e.getPoint().x;
        canvas.currentY = e.getPoint().y;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(canvas.currShape == null)
            return;


        canvas.clearSelect();
        canvas.addElementToList(canvas.currShape);
        canvas.mainGroup.addShape(canvas.currShape);
        canvas.currShape = null;
        canvas.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(canvas.currShape!= null){
            canvas.currShape.setShapeEnd(e.getPoint());
            canvas.repaint();
        }
        switch (operation) {
            case "move":
                MoveVisitor moveVisitor = new MoveVisitor();
                canvas.endX = e.getPoint().x;
                canvas.endY = e.getPoint().y;
                for (Shape s : canvas.selectedShapes) {
                    s.accept(moveVisitor);
                }
                break;
            case "resize":
                ResizeVisitor resizeVisitor = new ResizeVisitor();
                canvas.endX = e.getPoint().x;
                canvas.endY = e.getPoint().y;
                for (Shape s : canvas.selectedShapes) {
                    s.accept(resizeVisitor);
                }
                break;
        }
        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
