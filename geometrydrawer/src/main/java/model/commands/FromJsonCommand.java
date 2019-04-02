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

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FromJsonCommand implements Command {

    private CanvasController canvas;
    private CommandManager commandManager;
    private String FilePath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/GeometryDrawer/%s.json";

    public FromJsonCommand(){
        this.canvas = SingletonCanvas.getInstance();
        this.commandManager = SingletonCmdMng.getInstance();
    }

    @Override
    public void Execute() {
        LoadContent("test");
    }

    @Override
    public void Undo() {

    }

    @Override
    public void Redo() {

    }

    private void LoadContent(String fileName){
        Gson gson = new GsonBuilder().registerTypeAdapter(Shape.class, new InterfaceAdapter<Shape>()).create();

        //read from filepath
        String filePath = String.format(FilePath, "test");
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<Shape>(){}.getType();
        Shape group = gson.fromJson(content, type);

        Instantiate(group);

    }

    private void Instantiate(Shape group){
        canvas.setMainGroup(group);
        canvas.insertFromFile(group.getSubShapes());
    }
}
