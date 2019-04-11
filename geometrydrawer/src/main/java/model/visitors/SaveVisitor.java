package model.visitors;

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

public class SaveVisitor implements Visitor{
    private CanvasController canvas;
    private Figure figure;

    public SaveVisitor(){
        canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void visit(Figure figure) {
        this.figure = figure;
        canvas.clearSelect();

        try {
            Save();
        } catch (IOException e) {
            System.out.println("Failed to save");
        }
    }
    /**
     * Save the canvas to a file
     * @throws IOException
     */
    private void Save() throws IOException {
        String filePath = CreateFile();
        String content = ParseContent();

        WriteFile(filePath, content);
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
        Group group = (Group) this.figure;
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
