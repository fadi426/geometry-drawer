package controller;


import model.Polygon;
import model.Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CanvasController extends JPanel {

    public Class<?> shapeType;
    public Shape currShape;
    private Color drawingColor = Color.BLACK;

    public DefaultListModel<Shape> listmodel = new DefaultListModel<Shape>();

    public CanvasController(Color bg){
        this.setBackground(bg);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    currShape=(Shape) shapeType.newInstance();
                    currShape.setCurrentColor(drawingColor);
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException e3){
                    JOptionPane.showMessageDialog(getParent(), "No Shape Selected!");
                }
                if(currShape!=null){
                    currShape.setShapeStart(e.getPoint());
                }
            }

            public void mouseClicked(MouseEvent e){
                if(currShape instanceof Polygon){
                    currShape.setShapeEnd(e.getPoint());
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent e){
                if(currShape != null){
                    listmodel.addElement(currShape);
                    currShape=null;
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e){
                if(currShape!= null){
                    currShape.setShapeEnd(e.getPoint());
                    repaint();
                }
            }
        });
    }

    public void setCurrShape(Class<?> toDraw){
        shapeType = toDraw;
    }

    public void changeShapePoints(Point newStart,Point newEnd,int shapeIndex){
        Shape shaperef = listmodel.elementAt(shapeIndex);
        if(shaperef instanceof Polygon)
            return;
        shaperef.setShapeStart(newStart);
        shaperef.setShapeEnd(newEnd);
    }

    public void clear(){
        listmodel.clear();
        repaint();
    }

    public void setDrawingColor(Color toDraw){
        drawingColor = toDraw;
    }

    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0;i < listmodel.getSize();i++){
            listmodel.elementAt(i).draw(g);
            if(listmodel.elementAt(i).isFilled()){
                listmodel.elementAt(i).fill(g);
            }
        }

        if(currShape != null){
            currShape.draw(g);
        }
    }
}
