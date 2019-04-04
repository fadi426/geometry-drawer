package model.shapes;

import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;

public class Ornament extends Shape{

    private String value;
    private String position;
    private Figure parent;

    public Ornament(String value, String position, Figure parent){
        this.position = position;
        this.value = value;
        this.parent = parent;
    }

    @Override
    public void draw(Graphics g) {
        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
            updateOrnament();
            g2.drawString(value, getShapeStart().x, getShapeStart().y);
        }
    }

    @Override
    public void fill(Graphics g) {

    }

    @Override
    public void addOrnament(Ornament ornament){
        return;
    }

    public void updateOrnament() {
        Point start, end;
        int width, height;
        int offset = 10;

        if (parent instanceof Group) {
            Group group = (Group) parent;
            List<Point> points = group.CalculateBoundary();
            start = points.get(0);
            end = points.get(1);
        }
        else {
            Shape shape = (Shape) parent;
            start = shape.getShapeStart();
            end = shape.getShapeEnd();
        }


        width = abs(end.x - start.x);
        height = abs(end.y - start.y);
        switch (position) {
            case "top":
                setShapeStart(new Point(start.x + width / 2, start.y + offset));
                break;
            case "bottom":
                setShapeStart(new Point(start.x + width / 2, end.y + offset));
                break;
            case "left":
                setShapeStart(new Point(start.x, start.y + height / 2 + offset));
                break;
            case "right":
                setShapeStart(new Point(end.x, start.y + height / 2 + offset));
                break;
        }
    }
}
