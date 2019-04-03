package model.shapes;

import java.awt.*;

import static java.lang.Math.abs;

public class Ornament extends Shape{

    private String value = "This is gona be awesome";
    private String position;
    private Shape shape;

    public Ornament(Shape shape){
        this.shape = shape;
        this.position = "left";
    }

    @Override
    public void draw(Graphics g) {
        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawString(value,getShapeStart().x, getShapeStart().y);

            switch (position){
                case "left":

                    break;
                case "rigt":

                    break;
            }
        }
    }

    @Override
    public void fill(Graphics g) {

    }

    public void addToShape() {
        int width = abs(shape.getShapeEnd().x - shape.getShapeStart().x);
        int height = abs(shape.getShapeEnd().y - shape.getShapeStart().y);
        int offset = 5;

        switch (position){
            case "top":
                setShapeStart(new Point(shape.getShapeStart().x + width/2, shape.getShapeStart().y + offset));
                break;
            case "bottom":
                setShapeStart(new Point(shape.getShapeStart().x + width/2, shape.getShapeEnd().y + offset));
                break;
            case "left":
                setShapeStart(new Point(shape.getShapeStart().x, shape.getShapeStart().y + height/2 + offset));
                break;
            case "rigt":
                setShapeStart(new Point(shape.getShapeEnd().x, shape.getShapeStart().y + height/2 + offset));
                break;
        }
    }
}
