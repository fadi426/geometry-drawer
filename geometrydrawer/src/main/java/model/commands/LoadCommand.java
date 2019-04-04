package model.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.CanvasController;
import model.adapters.InterfaceAdapter;
import model.shapes.Circle;
import model.shapes.Group;
import model.shapes.Rectangle;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;
import model.singleObjects.SingletonCmdMng;

import java.awt.*;
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
        Gson gson = new GsonBuilder().registerTypeAdapter(Shape.class, new InterfaceAdapter<Shape>()).create();

        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<Group>(){}.getType();
        Group group = gson.fromJson(content, type);

        Instantiate(group);

    }

    private void Instantiate(Group group){
        canvas.clear();
        canvas.clearSelect();
        canvas.setMainGroup(group);
        canvas.insertFromFile(group.getSubShapes());
    }
}
