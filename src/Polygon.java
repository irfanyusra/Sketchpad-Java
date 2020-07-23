
import java.awt.*;
import java.util.ArrayList;

public class Polygon extends Shape implements ShapeInterface {

    public ArrayList<Point> listOfPoints = new ArrayList<Point>();
    private int smX;
    private int bgX;
    private int smY;
    private int bgY;


    private boolean isClosedPolygon = false;
    public boolean isClosed = false;

    Polygon(int coordx, int coordy, Color clr, boolean isClosedPolygon) {
        smX = coordx;
        bgX = coordx;
        smY = coordy;
        bgY = coordy;
        this.isClosedPolygon = isClosedPolygon;

        // inherited from Shape
        this.startP.coordx = coordx;
        this.startP.coordy = coordy;
        if (isClosedPolygon)
            this.t = "typeClosedPolygon";
        else
            this.t = "typeOpenPolygon";
        this.clr = clr;

        listOfPoints.add(this.startP);
    }

    Polygon(Polygon openPolygon) {
        // inherited from Shape
        this.startP.coordx = openPolygon.startP.coordx;
        this.startP.coordy = openPolygon.startP.coordy;
        // this.t = "typeOpenPolygon";
        this.clr = openPolygon.clr;

        this.smX = openPolygon.smX;
        this.smY = openPolygon.smY;
        this.bgX = openPolygon.bgX;
        this.bgY = openPolygon.bgY;
        this.isClosedPolygon = openPolygon.isClosedPolygon;
        if (isClosedPolygon)
            this.t = "typeClosedPolygon";
        else
            this.t = "typeOpenPolygon";
        this.listOfPoints = new ArrayList<Point>();
        for (Point p : openPolygon.listOfPoints) {
            Point newP = new Point(p);
            this.listOfPoints.add(newP);
        }

    }

    @Override
    public Polygon selected(int xcoord, int ycoord) {
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

    public void closePolygon() {
        isClosed = true;
    }

    public void openPolygon() {
        isClosed = false;
    }
}