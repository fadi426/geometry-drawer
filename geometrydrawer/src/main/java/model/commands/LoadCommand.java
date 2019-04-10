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
import model.singleObjects.SingletonCmdMng;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LoadCommand implements Command {

    private CanvasController canvas;
    private CommandManager commandManager;

    public LoadCommand() {
        this.canvas = SingletonCanvas.getInstance();
        this.commandManager = SingletonCmdMng.getInstance();
    }

    @Override
    public void Execute() {
        String filePath = pickFile();
        LoadContent(filePath);
    }

    @Override
    public void Undo() {

    }

    @Override
    public void Redo() {

    }

    private String pickFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        return dialog.getDirectory() + dialog.getFile();
    }

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
//        List<Figure> figures = group.getSubShapes();
        setOrnaments(mainGroup.getSubShapes());
        Instantiate(mainGroup);
    }

    private void Instantiate(Group group) {
        canvas.clear();
        canvas.clearSelect();
        canvas.setMainGroup(group);
        canvas.insertFromFile(group.getSubShapes());
    }

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
