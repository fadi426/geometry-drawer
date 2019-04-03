package model.shapes;

import java.awt.*;

import static java.lang.Math.abs;

public class Ornament extends Shape{

    private String value = "This is gona be awesome";
    private String position = "bottom";
    private Shape shape;

    @Override
    public void draw(Graphics g) {
        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.drawString(value,getShapeStart().x, getShapeStart().y);
        }
    }

    @Override
    public void fill(Graphics g) {

    }

    public void addToShape() {
        int width = abs(shape.getShapeEnd().x - shape.getShapeStart().x);
        int height = abs(shape.getShapeEnd().y - shape.getShapeStart().y);

        switch (position){
            case "top":
                shape.setShapeStart(new Point());
                break;
            case "bottom":
                shape.setShapeStart(new Point(getShapeStart().x, getShapeStart().y));
            case "left":
                shape.setShapeStart(new Point(getShapeStart().x, getShapeStart().y));
                break;
            case "rigt":
                shape.setShapeStart(new Point(getShapeStart().x, getShapeStart().y));
        }
    }
}
