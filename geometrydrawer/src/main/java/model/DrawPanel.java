package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    private List<Square> shapes = new ArrayList<Square>();

    public DrawPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        shapes.add(new Square(50,50,20,20));
        shapes.add(new Square(100,100,20,20));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

    }


    private void moveSquare(int x, int y) {
        int OFFSET = 1;
        if ((this.shapes.get(0).getSquareX()!=x) || (this.shapes.get(0).getSquareY()!=y)) {
            repaint(this.shapes.get(0).getSquareX(),this.shapes.get(0).getSquareY(),this.shapes.get(0).getSquareW()+OFFSET,this.shapes.get(0).getSquareH()+OFFSET);
            this.shapes.get(0).setSquareX(x);
            this.shapes.get(0).setSquareY(y);
            repaint(this.shapes.get(0).getSquareX(),this.shapes.get(0).getSquareY(),this.shapes.get(0).getSquareW()+OFFSET,this.shapes.get(0).getSquareH()+OFFSET);
        }
    }

    public void addShape(Square shape) {
        shapes.add(shape);
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapes.stream()
                .forEach(shape -> paintShape(g,shape));
        g.drawString("This is my custom Panel!",10,20);
    }

    protected void paintShape (Graphics g, Square square){
        g.setColor(Color.RED);
        g.fillRect(square.getSquareX(),square.getSquareY(),square.getSquareW(),square.getSquareH());
        g.setColor(Color.BLACK);
        g.drawRect(square.getSquareX(),square.getSquareY(),square.getSquareW(),square.getSquareH());
    }
}
