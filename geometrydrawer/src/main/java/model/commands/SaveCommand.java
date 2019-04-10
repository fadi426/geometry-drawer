package model.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.CanvasController;
import model.adapters.InterfaceAdapter;
import model.shapes.Figure;
import model.shapes.Group;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveCommand implements Command {

    private CanvasController canvas;

    public SaveCommand() {
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvas.clearSelect();
    }

    @Override
    public void Undo() {

    }

    @Override
    public void Redo() {

    }

    private void Save() throws IOException {
        String filePath = CreateFile();
        String content = ParseContent();

        WriteFile(filePath, content);
    }

    private String CreateFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open", FileDialog.SAVE);
        dialog.setFile("*.json");
        dialog.setVisible(true);


        String path = dialog.getDirectory() + dialog.getFile();
        File file = new File(path);
        file.getParentFile().mkdirs();
        return path;
    }

    private void WriteFile(String filePath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }

    private String ParseContent() {
        Group group = canvas.mainGroup;
        List<Figure> subshapes = group.getSubShapes();

        if (subshapes.size() == 0) return null;

        resetColors(subshapes);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Figure.class, new InterfaceAdapter<Figure>())
                .create();

        String json = gson.toJson(group, Figure.class);
        return json;
    }

    private void resetColors(List<Figure> figures) {
        for (Figure f : figures)
            f.setColor(Color.BLACK);
    }
}
