package model.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.CanvasController;
import model.adapters.InterfaceAdapter;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadCommand implements Command {

    private CanvasController canvas;
    private String filePath;
    private List<Figure> oldFigures;

    public LoadCommand() {
        this.canvas = SingletonCanvas.getInstance();
        oldFigures = new ArrayList<>();
    }

    @Override
    public void Execute() {
        oldFigures = canvas.toList();
        filePath = pickFile();
        LoadContent(filePath);
    }

    @Override
    public void Undo() {
        canvas.setCanvasLists(oldFigures);
    }

    @Override
    public void Redo() {
        oldFigures = canvas.toList();
        LoadContent(filePath);
    }

    /**
     * Selects the file to be loaded
     *
     * @return the file path
     */
    private String pickFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        return dialog.getDirectory() + dialog.getFile();
    }

    /**
     * Load the content from the file
     *
     * @param filePath the path of the file
     */
    private void LoadContent(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Figure.class, new InterfaceAdapter<Figure>())
                .create();

        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<Figure>() {
        }.getType();
        Figure figure = gson.fromJson(content, type);

        Group mainGroup = (Group) figure;
        setOrnaments(mainGroup.getSubShapes());
        Instantiate(mainGroup);
    }

    /**
     * Add the content from the file to the canvas
     *
     * @param group the group the add
     */
    private void Instantiate(Group group) {
        canvas.clear();
        canvas.clearSelect();
        canvas.setMainGroup(group);
        canvas.insertFromFile(group.getSubShapes());
    }

    /**
     * Set all the ornaments
     *
     * <p>
     * When you save the file, the parent reference from a ornament is not saved.
     * To fix this we check if the parent is the same with any figure that
     * is also loaded.
     * if that is the case set the parent of the ornament recursively to the figure
     * so that the file is correctly restored.
     * </p>
     *
     * @param groupFigures the figures that you want to look in to
     */
    private void setOrnaments(List<Figure> groupFigures) {
        for (Figure f : groupFigures) {
            if (f instanceof Ornament) {
                Ornament o = (Ornament) f;
                for (Figure f2 : groupFigures) {
                    if (f2 instanceof Shape) {
                        Shape shape = (Shape) f2;

                        if (o.getParent() instanceof Shape) {
                            Shape oParent = (Shape) o.getParent();
                            if (oParent.getStartPoint().x == shape.getStartPoint().x
                                    && oParent.getStartPoint().y == shape.getStartPoint().y
                                    && oParent.getEndPoint().x == shape.getEndPoint().x
                                    && oParent.getEndPoint().y == shape.getEndPoint().y
                            )
                                o.setParent(f2);
                        }
                    }
                    if (f2 instanceof Group) {
                        Group group = (Group) f2;

                        if (o.getParent() instanceof Group) {
                            Group oParent = (Group) o.getParent();
                            List<Point> oParentPoints = oParent.CalculateBoundary();
                            List<Point> groupPoints = group.CalculateBoundary();
                            if (oParentPoints.get(0).x == groupPoints.get(0).x
                                    && oParentPoints.get(0).y == groupPoints.get(0).y
                                    && oParentPoints.get(1).x == groupPoints.get(1).x
                                    && oParentPoints.get(0).y == groupPoints.get(0).y
                            )
                                o.setParent(f2);
                        }
                        setOrnaments(group.getSubShapes());
                    }
                }
            }
        }
    }
}
