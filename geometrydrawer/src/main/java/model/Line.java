package model;

import java.awt.Graphics;
import java.awt.Point;

public class Line extends Shape {

	public Line(Point start, Point end){
		setShapeStart(start);
		setShapeEnd(end);
	}
	
	public Line(){
		setShapeStart(new Point(0,0));
		setShapeEnd(new Point(0,0));
	}

	@Override
	public void draw(Graphics g) {		
		int x1,y1,x2,y2;
		
			x1 = getShapeStart().x;
			y1 = getShapeStart().y;
		
			x2 = getShapeEnd().x;
			y2 = getShapeEnd().y;
			
		if(x1 == x2 && y1 == y2){
			return;
		}

        int d = 0;
        int dy = Math.abs(y2 - y1);
        int dx = Math.abs(x2 - x1);

        int dy2 = (dy << 1);
        int dx2 = (dx << 1);

        int ix = x1 < x2 ? 1 : -1;
        int iy = y1 < y2 ? 1 : -1;

        if (dy <= dx) {
            do{
            	drawPoint(g,x1, y1);
                x1 += ix;
                d += dy2;
                if (d > dx) {
                    y1 += iy;
                    d -= dx2;
                }
            }while(x1!=x2);
        } else {
           do{
                drawPoint(g,x1, y1);
                y1 += iy;
                d += dx2;
                if (d > dy) {
                    x1 += ix;
                    d -= dy2;
                }
            }while(y1 != y2);
        }

	}

	@Override
	public boolean contain(int x, int y) {
		return false;
	}

	@Override
	public String toString(){
		return "Line: (" + getShapeStart().x + ","+getShapeStart().y + ")"
				+ "-("+getShapeEnd().x + ","+getShapeEnd().y + ")";
	}

	@Override
	public void fill(Graphics g) {

	}
}
