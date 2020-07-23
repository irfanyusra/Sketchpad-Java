
import java.awt.*;
import java.util.ArrayList;

public class Scribble extends Shape implements ShapeInterface {

    public ArrayList<Point> listOfPoints = new ArrayList<Point>();
    private int smX;
    private int bgX;
    private int smY;
    private int bgY;

    Scribble(int xcoord, int ycoord, Color clr) {
        smX = xcoord;
        bgX = xcoord;
        smY = ycoord;
        bgY = ycoord;

        // inherited from Shape
        this.startP.coordx = xcoord;
        this.startP.coordy = ycoord;
        this.t = "typeScribble";
        this.clr = clr;

        listOfPoints.add(this.startP);

        
    }

    Scribble(Scribble scribble) {
        // inherited from Shape
        this.startP.coordx = scribble.startP.coordx;
        this.startP.coordy = scribble.startP.coordy;
        this.t = "typeScribble";
        this.clr = scribble.clr;

        this.smX = scribble.smX;
        this.smY = scribble.smY;
        this.bgX = scribble.bgX;
        this.bgY = scribble.bgY;

        this.listOfPoints = new ArrayList<Point>();
        for (Point p : scribble.listOfPoints) {
            this.listOfPoints.add(p);
        }

    }

    @Override
    public Scribble selected(int xcoord, int ycoord) {
        return (xcoord <= bgX && ycoord <= bgY && xcoord >= smX && ycoord >= smY) ? this : null;
    }

    @Override
    public void move(int xcoord, int ycoord) {

        smX += xcoord;
        bgX += xcoord;
        smY += ycoord;
        bgY += ycoord;

        for (Point p : listOfPoints) {
            p.move(xcoord, ycoord);
        }
    }

    public void addPoints(int xcoord, int ycoord) {
        Point point = new Point(xcoord, ycoord);
        listOfPoints.add(point);

        smX = xcoord < smX ? xcoord : smX;
        smY = ycoord < smY ? ycoord : smY;
        bgX = xcoord > bgX ? xcoord : bgX;
        bgY = ycoord > bgY ? ycoord : bgY;

    }

}
