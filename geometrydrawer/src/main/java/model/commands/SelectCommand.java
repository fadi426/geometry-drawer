package model.commands;

import com.sun.org.apache.xpath.internal.operations.Or;
import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {

    private Figure figure;
    private List<Figure> ornaments;
    private CanvasController canvas;

    public SelectCommand(Figure figure){
        this.figure = figure;
        this.canvas = SingletonCanvas.getInstance();
        ornaments = new ArrayList<>();
    }

    @Override
    public void Execute() {
        figure.setColor(Color.RED);
        canvas.selectedShapes.add(figure);
        addOrnaments();
    }

    @Override
    public void Undo() {
        figure.setColor(Color.BLACK);
        canvas.selectedShapes.remove(figure);
        deleteOrnaments();
    }

    @Override
    public void Redo() {
        figure.setColor(Color.RED);
        canvas.selectedShapes.add(figure);
        addOrnaments();
    }

    private void findOrnaments(){
        for (Figure f : canvas.toList()){
            if (f instanceof Ornament){
                Ornament ornament = (Ornament) f;
                if (ornament.getParent() == figure){
                    ornaments.add(ornament);
                }
            }
        }
    }

    private void addOrnaments(){
        ornaments.clear();
        findOrnaments();
        if (ornaments.size() == 0)
            return;

        for (Figure o : ornaments){
            o.setColor(Color.RED);
            canvas.selectedShapes.add(o);
        }
    }

    private void deleteOrnaments(){
        if (ornaments.size() == 0)
            return;

        for (Figure o : ornaments){
            o.setColor(Color.BLACK);
            canvas.selectedShapes.add(o);
        }
    }
}
