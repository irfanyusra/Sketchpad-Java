
import java.awt.*;
import java.lang.Math;

public class Rectangle extends Shape implements ShapeInterface {
    public int w;
    public int h;
    public boolean isSquare;

    public Rectangle(int coordx1, int coordy1, int coordx2, int coordy2, Color clr, boolean isSquare) {

        w = Math.abs(coordx2 - coordx1);
        h = Math.abs(coordy2 - coordy1);
        this.isSquare = isSquare;
        // inherited from Shape
        // if x1 < x2 choose x1 otherwise choose x2
        this.startP.coordx = (coordx1 < coordx2) ? coordx1 : coordx2;
        // if y1 < y2 choose y1 otherwise choose y2
        this.startP.coordy = (coordy1 < coordy2) ? coordy1 : coordy2;

        this.t = "typeRectangle";

        if (isSquare) {
            double hyp = Math.sqrt((w * w) + (h * h));
            this.h = (int) hyp;
            this.w = (int) hyp;
            this.t = "typeSquare";
        } else {
            this.t = "typeRectangle";
        }

        this.clr = clr;
    }

    Rectangle(Rectangle rectangle) {
        this.w = rectangle.w;
        this.h = rectangle.h;
        this.isSquare = rectangle.isSquare;

        // inherited from Shape
        // x coordinates
        this.startP.coordx = rectangle.startP.coordx;
        this.endP.coordx = rectangle.endP.coordx;
        // y coordinates
        this.startP.coordy = rectangle.startP.coordy;
        this.endP.coordy = rectangle.endP.coordy;

        // this.t = "typeRectangle";
        if (isSquare)
            this.t = "typeSquare";
        else
            this.t = "typeRectangle";

        this.clr = rectangle.clr;

    }

    @Override
    public Rectangle selected(int xcoord, int ycoord) {
        int sumX = startP.coordx + w;
        int sumY = startP.coordy + h;
        return (startP.coordx <= xcoord && startP.coordy <= ycoord && xcoord <= sumX && ycoord <= sumY) ? this : null;

    }

    @Override
    public void move(int xcoord, int ycoord) {
        startP.move(xcoord, ycoord);
    }

}
