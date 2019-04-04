package model.shapes;

import model.visitors.Visitor;

import java.awt.*;

import static java.lang.Math.abs;

public class Ornament extends Shape{

    private String value;
    private String position;

    public Ornament(String value, String position){
        this.position = position;
        this.value = value;
    }

    @Override
    public void draw(Graphics g) {
        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
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

    public String getPosition() {
        return position;
    }
}
