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
    private String filePath;
    private String content;

    public SaveCommand() {
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        try {
            Save();
        } catch (IOException e) {
            System.out.println("Failed to add the file to the directory");
        }
        canvas.clearSelect();
    }

    @Override
    public void Undo() {
        deleteFile();
    }

    @Override
    public void Redo() {
        try {
            WriteFile(filePath, content);
        } catch (IOException e) {
            System.out.println("Failed to add the file to the directory");
        }
    }

    /**
     * Save the canvas to a file
     * @throws IOException
     */
    private void Save() throws IOException {
        filePath = CreateFile();
        content = ParseContent();

        WriteFile(filePath, content);
    }

    /**
     * Deletes the file that you have saved
     */
    private void deleteFile() {
        File file = new File(filePath);
        file.delete();
    }

    /**
     * Create the file that you want save the contents to
     * @return returns a filepath of the file
     */
    private String CreateFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open", FileDialog.SAVE);
        dialog.setFile("*.json");
        dialog.setVisible(true);


        String path = dialog.getDirectory() + dialog.getFile();
        File file = new File(path);
        file.getParentFile().mkdirs();
        return path;
    }

    /**
     * write the content to a file
     * @param filePath filepath of the file
     * @param content the json string
     * @throws IOException
     */
    private void WriteFile(String filePath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }

    /**
     * Parse the canvas to a json file
     * @return the json string of the content
     */
    private String ParseContent() {
        Group group = canvas.getMainGroup();
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

    /**
     * Reset the colors of the shapes so that they won't be saved in a select state.
     * @param figures the figures to reset the colors of
     */
    private void resetColors(List<Figure> figures) {
        for (Figure f : figures)
            f.setColor(Color.BLACK);
    }
}
