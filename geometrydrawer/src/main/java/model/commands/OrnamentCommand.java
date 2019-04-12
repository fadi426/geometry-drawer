package model.commands;

import controller.CanvasController;
import model.decorators.OrnamentDecorator;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;

import java.util.ArrayList;
import java.util.List;

public class OrnamentCommand implements Command {

    private CanvasController canvas;
    private List<Figure> selectedFigures;
    private List<Figure> oldFigures = new ArrayList<>();
    private List<Figure> newFigures = new ArrayList<>();
    private List<Ornament> ornaments;

    public OrnamentCommand() {
        this.canvas = SingletonCanvas.getInstance();
        this.selectedFigures = new ArrayList<>();
        this.ornaments = new ArrayList<>();
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
        for (Figure figure : newFigures) {
            OrnamentDecorator ornamentDecorator = (OrnamentDecorator) figure;
            Ornament ornament = (Ornament) ornamentDecorator.getOrnament();
            ornaments.add(ornament);
            canvas.removeElementFromList(ornament);
        }
    }

    @Override
    public void Redo() {
        List<Ornament> tempOrnaments = new ArrayList<>();
        for (Ornament ornament : ornaments) {
            tempOrnaments.add(ornament);
            canvas.setCanvasLists(addInOrder(canvas.getMainGroup(), ornament)
            );
        }
        ornaments.removeAll(tempOrnaments);
    }

    /**
     * Add a new ornament
     */
    private void addNewOrnament() {
        if (selectedFigures.size() == 0)
            return;

        for (Figure f : selectedFigures) {
            if (f instanceof Ornament)
                continue;

            Figure figure = new OrnamentDecorator(f);
            newFigures.add(figure);
            Ornament ornament = (Ornament) ((OrnamentDecorator) figure).getOrnament();
//            canvas.addElementToList(ornament);
            ornaments.add(ornament);
            canvas.setCanvasLists(addInOrder(canvas.getMainGroup(), ornament));
            canvas.addToSelected(ornament);
        }
    }

    /**
     * Look for the group where the parent of the ornament is in and add to it
     * @param group group to find in
     * @param ornament ornament to find its parent for
     * @return
     */
    private List<Figure> addInOrder(Group group, Ornament ornament) {
        Group newGroup = new Group();
        for (Figure figure : group.getSubShapes()) {

            if (figure instanceof Group) {
                Group g = (Group) figure;
                newGroup.addFigure(g);
                if (findParent(figure, ornament)) {
                    newGroup.addFigure(ornament);
                }
                List<Figure> temp_figures = addInOrder(g, ornament);
                g.clear();
                g.addFigures(temp_figures);
            } else {
                newGroup.addFigure(figure);
                if (findParent(figure, ornament)) {
                    newGroup.addFigure(ornament);
                }
            }
        }
        return newGroup.getSubShapes();
    }

    private Boolean findParent(Figure figure, Ornament ornament){
            if (ornament.getParent() == figure) {
                return true;
            }
            return false;
        }
    }
