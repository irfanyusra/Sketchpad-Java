
import java.awt.*;

public class Ellipse extends Shape implements ShapeInterface {
    public int w; // width
    public int h; // height
    public boolean isCircle;

    Ellipse(int coordx1, int coordy1, int coordx2, int coordy2, Color clr, boolean isCircle) {

        // inherited from Shape

        // if x1 < x2 choose x1 otherwise choose x2
        this.startP.coordx = (coordx1 < coordx2) ? coordx1 : coordx2;
        // if y1 < y2 choose y1 otherwise choose y2
        this.startP.coordy = (coordy1 < coordy2) ? coordy1 : coordy2;
        this.isCircle = isCircle;
        w = Math.abs(coordx2 - coordx1);
        h = Math.abs(coordy2 - coordy1);
        if (isCircle) {
            double hyp = Math.sqrt((w * w) + (h * h));
            this.h = (int) hyp;
            this.w = (int) hyp;
            this.t = "typeCircle";
        } else {
            this.t = "typeEllipse";
        }

        this.clr = clr;
    }

    Ellipse(Ellipse ellipse) {

        this.h = ellipse.h;
        this.w = ellipse.w;
        // inherited from Shape
        this.startP.coordx = ellipse.startP.coordx;
        this.startP.coordy = ellipse.startP.coordy;

        this.isCircle = ellipse.isCircle;
        if (isCircle)
            this.t = "typeCircle";
        else
            this.t = "typeEllipse";

        this.clr = ellipse.clr;
    }

    @Override
    public Ellipse selected(int xcoord, int ycoord) {
        int sumX = startP.coordx + w;
        int sumY = startP.coordy + h;
        return (startP.coordx <= xcoord && startP.coordy <= ycoord && xcoord <= sumX && ycoord <= sumY) ? this : null;
    }

    @Override
    public void move(int xcoord, int ycoord) {
        startP.move(xcoord, ycoord);
    }

}
