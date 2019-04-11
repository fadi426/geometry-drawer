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

    private int currentX;
    private int currentY;
    private int endX;
    private int endY;

    /**
     * sets the canvas to the singleton canvas object
     * @param canvas is a singleton canvas
     */
    public void setCanvas(CanvasController canvas) {
        this.canvas = canvas;
    }

    /**
     * Sets the operation the mouse should do when clicked in the canvas
     * @param operation the operation it needs to execute when a click happens
     */

    /**
     * Sets the current operation that the user is using to perform a action
     * @param operation is a action the user wants to perform
     */
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
            case "move":
                firstTurn = true;
                if (canvas.getSelectedShapes().size() > 0)
                    commandManager.Execute(new MoveCommand(canvas.getSelectedShapes()));
                break;
            case "resize":
                firstTurn = true;
                if (canvas.getSelectedShapes().size() > 0)
                    commandManager.Execute(new ResizeCommand(canvas.getSelectedShapes()));
                break;

        }
        canvas.flatMap(canvas.getSelectedShapes());
        currentX = e.getPoint().x;
        currentY = e.getPoint().y;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        canvas.getFlatEditableShapes().clear();
        canvas.getFlatPointsEditableShapes().clear();

        Shape currentShape = canvas.getCurrentShape();
        if (currentShape == null)
            return;

        canvas.clearSelect();
        canvas.addElementToList(currentShape);
        canvas.setCurrentShape(null);
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

        endX = e.getPoint().x;
        endY = e.getPoint().y;

        switch (operation) {
            case "draw":
                Shape currentShape = canvas.getCurrentShape();
                if (currentShape != null) {
                    currentShape.setEndPoint(e.getPoint());
                }
                break;
            case "move":
                MoveVisitor moveVisitor = new MoveVisitor();
                for (Figure f : canvas.getSelectedShapes()) {
                    f.accept(moveVisitor);
                }
                firstTurn = false;
                break;
            case "resize":
                ResizeVisitor resizeVisitor = new ResizeVisitor();
                for (Figure f : canvas.getSelectedShapes()) {
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

    /**
     * Gives back the current X position of the mouse on the canvas
     * @return the current X position
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * Sets the current mouse X Position to the target X mouse position
     * @param currentX is to be changed mouse X position
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    /**
     * Gives back the current Y position of the mouse on the canvas
     * @return the current Y position
     */
    public int getCurrentY(){
        return currentY;
    }

    /**
     * Sets the current mouse Y Position to the target X mouse position
     * @param currentY is to be changed mouse Y position
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    /**
     * Gives back the current end X position of the mouse on the canvas
     * @return the current end X position
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Gives back the current end Y position of the mouse on the canvas
     * @return the current end X position
     */
    public int getEndY(){
        return endY;
    }

}
