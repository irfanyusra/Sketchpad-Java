public class Point {
    int coordx;
    int coordy;

    Point() {
        this.coordx = 0;
        this.coordy = 0;
    }

    Point(int coordx, int coordy) {
        this.coordx = coordx;
        this.coordy = coordy;
    }

    Point(Point p) {
        this.coordx = p.coordx;
        this.coordy = p.coordy;
    }

    public void move(int xcoord, int ycoord) {
        this.coordx += xcoord;
        this.coordy += ycoord;
    }

}
