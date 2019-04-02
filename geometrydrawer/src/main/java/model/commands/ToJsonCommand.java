package model.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.CanvasController;
import model.adapters.InterfaceAdapter;
import model.shapes.Circle;
import model.shapes.Group;
import model.shapes.Rectangle;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ToJsonCommand implements Command {

    private CanvasController canvas;
    private String FilePath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/GeometryDrawer/%s.json";

    public ToJsonCommand(){
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute(){
        try {
            Save("test");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Undo() {

    }

    @Override
    public void Redo() {

    }

    private void Save(String fileName) throws IOException {
        String filePath = CreateFile(fileName);
        String content = ParseContent();

        WriteFile(filePath, content);
    }

    private String CreateFile(String fileName){
        String filePath = String.format(FilePath, fileName);
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        return filePath;
    }

    private void WriteFile(String filePath, String content) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }

    private String ParseContent(){
        Group group = canvas.getMainGroup();

        if(group.getSubShapes().size() == 0) return null;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Shape.class, new InterfaceAdapter<Shape>())
                .create();

        String json = gson.toJson(group, Shape.class);
        return json;
    }

}
