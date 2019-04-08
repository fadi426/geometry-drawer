package model.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.CanvasController;
import model.adapters.InterfaceAdapter;
import model.shapes.*;
import model.shapes.Rectangle;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;
import model.singleObjects.SingletonCmdMng;
import model.strategies.ShapeContext;
import model.strategies.ShapeStrategy;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadCommand implements Command {

    private CanvasController canvas;
    private CommandManager commandManager;

    public LoadCommand(){
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

    private String pickFile(){
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        return dialog.getDirectory() + dialog.getFile();
    }

    private void LoadContent(String filePath){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Figure.class, new InterfaceAdapter<Figure>())
                .create();

        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<Figure>(){}.getType();
        Figure figure = gson.fromJson(content, type);

        Instantiate(figure);

    }

    private void Instantiate(Figure figure){
        Group group = (Group) figure;
        canvas.clear();
        canvas.clearSelect();
        canvas.setMainGroup(group);
        canvas.insertFromFile(group.getSubShapes());
    }
}
