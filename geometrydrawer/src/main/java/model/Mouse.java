package model;

import controller.CanvasController;
import model.commands.CommandManager;
import model.commands.MoveCommand;
import model.commands.ResizeCommand;
import model.shapes.Figure;
import model.shapes.Shape;
import model.singleObjects.SingletonCmdMng;
import model.visitors.MoveVisitor;
import model.visitors.ResizeVisitor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse extends MouseAdapter implements MouseListener, MouseMotionListener {

    String operation;
    CanvasController canvas;
    Boolean firstTurn = true;
    private CommandManager commandManager = SingletonCmdMng.getInstance();

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
        if (operation == "move") {
            firstTurn = true;
            if (canvas.selectedShapes.size() > 0)
            commandManager.Execute(new MoveCommand(canvas.selectedShapes));

        }
        if (operation == "resize"){
            firstTurn = true;
            if (canvas.selectedShapes.size() > 0)
            commandManager.Execute(new ResizeCommand(canvas.selectedShapes));
        }

        if(canvas.currShape == null)
            return;

        canvas.clearSelect();
        canvas.addElementToList(canvas.currShape);
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
                for (Figure f : canvas.selectedShapes) {
                    if (firstTurn)
                        canvas.setPreviousPosition(f);

                    f.accept(moveVisitor);
                }
                firstTurn = false;
                break;
            case "resize":
                ResizeVisitor resizeVisitor = new ResizeVisitor();
                canvas.endX = e.getPoint().x;
                canvas.endY = e.getPoint().y;
                for (Figure f : canvas.selectedShapes) {
                    if (firstTurn)
                        canvas.setPreviousPosition(f);

                    f.accept(resizeVisitor);
                }
                firstTurn = false;
                break;
        }
        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
