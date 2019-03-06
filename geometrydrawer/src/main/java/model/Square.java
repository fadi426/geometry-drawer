package model;

public class Square {

    private int squareX;
    private int squareY;
    private int squareW;
    private int squareH;

    public Square(int squareX, int squareY, int squareW, int squareH) {
        this.squareX = squareX;
        this.squareY = squareY;
        this.squareW = squareW;
        this.squareH = squareH;
    }

    public int getSquareX() {
        return squareX;
    }

    public int getSquareY() {
        return squareY;
    }

    public int getSquareW() {
        return squareW;
    }

    public int getSquareH() {
        return squareH;
    }

    public void setSquareX(int squareX) {
        this.squareX = squareX;
    }

    public void setSquareY(int squareY) {
        this.squareY = squareY;
    }
}
