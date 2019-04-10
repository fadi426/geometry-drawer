package model.commands;

import com.sun.org.apache.xpath.internal.operations.Or;
import controller.CanvasController;
import model.decorators.OrnamentDecorator;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.util.ArrayList;
import java.util.List;

public class OrnamentCommand implements Command {

    private CanvasController canvas;
    private List<Figure> selectedFigures;
    private List<Figure> oldFigures = new ArrayList<>();
    private List<Figure> newFigures = new ArrayList<>();
    private Ornament ornament;

    public OrnamentCommand(){
        this.canvas = SingletonCanvas.getInstance();
        this.selectedFigures = new ArrayList<>();
        selectedFigures.addAll(canvas.getSelectedShapes());
    }

    @Override
    public void Execute() {
        addNewOrnament();
    }

    @Override
    public void Undo() {
        oldFigures.clear();
        oldFigures.addAll(newFigures);
        for (Figure figure : newFigures){
            OrnamentDecorator ornamentDecorator = (OrnamentDecorator) figure;
            ornament = (Ornament) ornamentDecorator.getOrnament();
            canvas.removeElementFromList(ornament);
        }
    }

    @Override
    public void Redo() {
        canvas.setCanvasLists(findParent(canvas.mainGroup, ornament));
    }
    private void addNewOrnament(){
        if(selectedFigures.size() == 0)
            return;

        for (Figure f : selectedFigures){
            if (f instanceof Ornament)
                continue;

            Figure figure = new OrnamentDecorator(f);
            newFigures.add(figure);
            Ornament ornament = (Ornament) ((OrnamentDecorator) figure).getOrnament();
//            canvas.addElementToList(ornament);
            canvas.setCanvasLists(findParent(canvas.mainGroup, ornament));
        }
    }

    private List<Figure> findParent(Group group, Ornament ornament){
        Group newGroup = new Group();
        for (Figure figure : group.getSubShapes()) {

            if (figure instanceof Group){
                Group g = (Group) figure;
                newGroup.addFigure(g);
                List<Figure> temp_figures = findParent(g, ornament);
                g.clear();
                g.addFigures(temp_figures);
            }
            else
                newGroup.addFigure(figure);
        }
        for (int i = 0; i < newGroup.getSubShapes().size(); i++) {
            Figure figure = newGroup.getSubShapes().get(i);
            if (ornament.getParent() == figure)
                newGroup.addFigure(ornament);
        }
        return newGroup.getSubShapes();
    }
}
