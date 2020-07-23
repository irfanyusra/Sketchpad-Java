import java.awt.*;

public class Line extends Shape implements ShapeInterface {
    public Line(int x1, int y1, int x2, int y2, Color c) {
        // inherited from Shape
        // x coordinates
        this.startP.coordx = x1;
        this.endP.coordx = x2;

        // ycoordinates
        this.startP.coordy = y1;
        this.endP.coordy = y2;

        this.t = "typeLine";
        this.clr = c;
    }

    public Line(Line line) {
        // inherited from Shape
        // x coordinates
        this.startP.coordx = line.startP.coordx;
        this.endP.coordx = line.endP.coordx;
        // y coordinates
        this.startP.coordy = line.startP.coordy;
        this.endP.coordy = line.endP.coordy;

        this.t = "typeLine";
        this.clr = line.clr;
    }

    @Override
    public Line selected(int xcoord, int ycoord) {
        return (((endP.coordx >= xcoord && startP.coordx <= xcoord) || (startP.coordx >= xcoord && endP.coordx <= xcoord))
                && ((startP.coordy >= ycoord && endP.coordy <= ycoord) || (endP.coordy >= ycoord && startP.coordy <= ycoord))) ? this : null;
    }

    @Override
    public void move(int xcoord, int y) {
        startP.move(xcoord, y);
        endP.move(xcoord, y);
    }
}
