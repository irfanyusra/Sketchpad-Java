import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class DrawPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DrawPanelCareTaker dpct; //to make momento pattern work 
    // for FIFO - these have the state of the current draw panel 
    public ArrayList<Shape> listOfShapes = new ArrayList<Shape>();
    public ArrayList<Shape> ShapesInGroup = new ArrayList<Shape>();
    
    public MyMouseHandler myMouseHandler; // To handle all the mouse events

    public DrawPanel(Mode mode, DrawPanelCareTaker dpct) {
        //setting the base 
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        setBackground(Color.black);
        this.dpct = dpct;
        myMouseHandler = new MyMouseHandler();
        myMouseHandler.setMode(mode);
        this.addMouseMotionListener(myMouseHandler);
        this.addMouseListener(myMouseHandler);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 510);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//going through all the shapes in the list to paint them 
        for (Shape shape : listOfShapes) {
            g.setColor(shape.clr);
            Point p1;
            Point p2;
            int size = 0;
            int j = 0; // to iterate through the arrays
            switch (shape.t) {
                case "typeLine":
                    Line line = (Line) shape;
                    g.drawLine(line.startP.coordx, line.startP.coordy, line.endP.coordx, line.endP.coordy);
                    break;

                case "typeScribble":
                    Scribble scribble = (Scribble) shape;
                    for (Point point : scribble.listOfPoints) {
                        g.fillOval(point.coordx, point.coordy, 5, 5);
                    }
                    break;

                case "typeRectangle":
                    Rectangle rectangle = (Rectangle) shape;
                    g.drawRect(rectangle.startP.coordx, rectangle.startP.coordy, rectangle.w, rectangle.h);
                    break;

                case "typeEllipse":
                    Ellipse ellipse = (Ellipse) shape;
                    g.drawOval(ellipse.startP.coordx, ellipse.startP.coordy, ellipse.w, ellipse.h);
                    break;

                case "typeSquare":
                    Rectangle square = (Rectangle) shape;
                    g.drawRect(square.startP.coordx, square.startP.coordy, square.w, square.h);
                    break;

                case "typeCircle":
                    Ellipse circle = (Ellipse) shape;
                    g.drawOval(circle.startP.coordx, circle.startP.coordy, circle.h, circle.w);
                    break;

                case "typeOpenPolygon":
                    Polygon openPolygon = (Polygon) shape;
                    size = openPolygon.listOfPoints.size();
                    p1 = null;
                    p2 = null;
                    if (size <= 0)
                        break;

                    // greater than 0
                    p1 = openPolygon.listOfPoints.get(0);
                    if (size > 1) {
                        j = 0;
                        for (int i = 1; i < size; i++) {
                            p1 = openPolygon.listOfPoints.get(i);
                            p2 = openPolygon.listOfPoints.get(j);
                            g.drawLine(p2.coordx, p2.coordy, p1.coordx, p1.coordy);
                            j++;
                        }
                    }
                    break;

                case "typeClosedPolygon":
                    // System.out.println("dfdfdfdf");
                    Polygon closedPolygon = (Polygon) shape;
                    Point pointFrst = null;
                    Point pointLst = null;
                    p1 = null;
                    p2 = null;
                    size = closedPolygon.listOfPoints.size();
                    if (size <= 0)
                        break;
                    // greater than 0
                    p2 = closedPolygon.listOfPoints.get(0);
                    pointFrst = p2;

                    if (size > 1) {
                        j = 0;
                        for (int i = 1; i < size; i++) {
                            p2 = closedPolygon.listOfPoints.get(i);
                            p1 = closedPolygon.listOfPoints.get(j);
                            pointLst = p2;
                            g.drawLine(p1.coordx, p1.coordy, p2.coordx, p2.coordy);
                            j++;
                        }
                        if (closedPolygon.isClosed && pointFrst != null)
                            g.drawLine(pointFrst.coordx, pointFrst.coordy, pointLst.coordx, pointLst.coordy);
                    }
                    break;
            }
        }
    }

    class MyMouseHandler extends MouseAdapter {
        boolean groupSelected = false;
        boolean anythingSelected = false;
        boolean stateSaved = false;

        int coordx1;
        int coordx2;
        int tempCoordx;
        int oldCoordx;
        int newCoordx;

        int coordy1;
        int coordy2;
        int tempCoordy;
        int oldCoordy;
        int newCoordy;

        Shape shapeTemp;
        Polygon openPolygon;
        Polygon closedPolygon;
        Scribble scribble;
        Mode mode;
        Color clr;
        boolean isCopy = false;

        public void mouseDragged(MouseEvent event) {
            switch (mode.modeValue) {
                case 0: // scribble
                    scribble.addPoints(event.getX(), event.getY());
                    repaint();
                    break;
            }
        }

        public void mousePressed(MouseEvent event) {
            clr = mode.getMenuColour();
            coordx1 = event.getX();
            coordy1 = event.getY();

            switch (mode.modeValue) {
                case 0: // scribble
                    scribble = new Scribble(coordx1, coordy1, clr);
                    listOfShapes.add(scribble);
                    break;

                case 10: // move
                    shapeTemp = isShapeSelected(coordx1, coordy1, listOfShapes);
                    groupSelected = (shapeTemp != null && ShapesInGroup.contains(shapeTemp)) ? true : false;
                    anythingSelected = (shapeTemp != null && !ShapesInGroup.contains(shapeTemp)) ? true : false;
                    break;

                case 11: // group
                    shapeTemp = isShapeSelected(coordx1, coordy1, listOfShapes);
                    if (shapeTemp != null)
                        ShapesInGroup.add(shapeTemp);
                    break;

                case 13: // ungroup
                    break;

                default:
                    break;
            }
        }

        public void mouseReleased(MouseEvent event) {
            coordx2 = event.getX();
            coordy2 = event.getY();

            clr = mode.getMenuColour();
//checking the mode value and adding shapes accrording to that 
            switch (mode.modeValue) {
                case 1:
                    Line line = new Line(coordx1, coordy1, coordx2, coordy2, clr);
                    listOfShapes.add(line);
                    break;
                case 2:
                    Rectangle rectangle = new Rectangle(coordx1, coordy1, coordx2, coordy2, clr, false);
                    listOfShapes.add(rectangle);
                    break;
                case 3:
                    Ellipse ellipse = new Ellipse(coordx1, coordy1, coordx2, coordy2, clr, false);
                    listOfShapes.add(ellipse);
                    break;
                case 4:
                    Rectangle square = new Rectangle(coordx1, coordy1, coordx2, coordy2, clr, true);
                    listOfShapes.add(square);
                    break;
                case 5:
                    Ellipse circle = new Ellipse(coordx1, coordy1, coordx2, coordy2, clr, true);
                    listOfShapes.add(circle);
                    break;
                case 6:
                    if (mode.isPolygonConnected)
                        openPolygon.addPoints(coordx2, coordy2);
                    else {
                        openPolygon = new Polygon(coordx2, coordy2, clr, false);
                        listOfShapes.add(openPolygon);
                        mode.connectPolygon();
                    }
                    break;
                case 7:
                    if (mode.isPolygonConnected)
                        closedPolygon.addPoints(coordx2, coordy2);
                    else {
                        // closedPolygon = new ClosedPolygon(coordx2, coordy2, clr);
                        closedPolygon = new Polygon(coordx2, coordy2, clr, true);
                        listOfShapes.add(closedPolygon);
                        mode.connectPolygon();
                    }
                    break;
                case 9: // paste
                    if (!ShapesInGroup.isEmpty()) {
                        newCoordx = coordx2 - oldCoordx;
                        newCoordy = coordy2 - oldCoordy;

                        for (Shape shape : ShapesInGroup) {
                            pasteShapes(shape, newCoordx, newCoordy, isCopy);
                        }
                        if (!isCopy) {
                            ungroup();
                        }
                    }
                    break;

                case 10: // move
                    newCoordx = coordx2 - coordx1;
                    newCoordy = coordy2 - coordy1;

                    ArrayList<Shape> tempShapeList = new ArrayList<Shape>();
                    ArrayList<Shape> tempShapeList1 = new ArrayList<Shape>();

                    dpct.copyArrays(tempShapeList, tempShapeList1, listOfShapes, ShapesInGroup); //copying the arrays to use for the future  
                    shapeTemp = isShapeSelected(coordx1, coordy1, tempShapeList); 

                    if (groupSelected) {
                        for (Shape shape : tempShapeList1) {
                            moveShapes(shape, newCoordx, newCoordy);
                        }
                    }
                    if (shapeTemp != null && anythingSelected)
                        moveShapes(shapeTemp, newCoordx, newCoordy);
                    saveCurrentState(tempShapeList, tempShapeList1);
                    stateSaved = true;
                    break;
                default:
                    break;
            }

            if (stateSaved)
                stateSaved = false;
            else
                saveCurrentState(listOfShapes, ShapesInGroup);
        }

        public void copy() { //copying shapes 
            shapeTemp = isShapeSelected(coordx2, coordy2, listOfShapes);
            if (shapeTemp != null) {
                oldCoordx = coordx2;
                oldCoordy = coordy2;
                isCopy = true;
            }
        }

        public void cut() { //cutting the shape 
            isCopy = false;
            oldCoordx = coordx2;
            oldCoordy = coordy2;
            for (Shape currentShape : ShapesInGroup) {
                listOfShapes.remove(currentShape);
            }
            repaint();
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public void setClosedPolygon() {
            int index = listOfShapes.size() - 1;
            closedPolygon = (Polygon) listOfShapes.get(index);
        }

        public void setOpenPolygon() {
            int index = listOfShapes.size() - 1;
            openPolygon = (Polygon) listOfShapes.get(index);
        }

        public void isClosedPolygon() {
            if (closedPolygon != null) {
                closedPolygon.closePolygon();
                // dpct.closePolygon();
                // saveCurrentState(listOfShapes, ShapesInGroup);
                repaint();
            }
        }

        public void clearSelectedGroup() {
            groupSelected = false;
        }

    }

    public void ungroup() {
        ShapesInGroup.clear();
        myMouseHandler.clearSelectedGroup();
    }

    public void moveShapes(Shape shape, int moveCoordx, int moveCoordy) {
        switch (shape.t) {
            case "typeScribble":
                Scribble scribble = (Scribble) shape;
                scribble.move(moveCoordx, moveCoordy);
                break;

            case "typeLine":
                Line line = (Line) shape;
                line.move(moveCoordx, moveCoordy);
                break;

            case "typeRectangle":
                Rectangle rectangle = (Rectangle) shape;
                rectangle.move(moveCoordx, moveCoordy);
                break;

            case "typeEllipse":
                Ellipse ellipse = (Ellipse) shape;
                ellipse.move(moveCoordx, moveCoordy);
                break;

            case "typeCircle":
                Ellipse circle = (Ellipse) shape;
                circle.move(moveCoordx, moveCoordy);
                break;

            case "typeSquare":
                Rectangle square = (Rectangle) shape;
                square.move(moveCoordx, moveCoordy);
                break;

            case "typeOpenPolygon":
                Polygon currentOpenPoly = (Polygon) shape;
                currentOpenPoly.move(moveCoordx, moveCoordy);
                break;
            case "typeClosedPolygon":
                Polygon currentClosedPoly = (Polygon) shape;
                currentClosedPoly.move(moveCoordx, moveCoordy);
                break;
        }
    }

    public Shape isShapeSelected(int xcoord, int ycoord, ArrayList<Shape> shapes) { //checking if the shape is selected 
        for (Shape shape : shapes) {
            switch (shape.t) {
                case "typeScribble":
                    Scribble scribble = (Scribble) shape;
                    if (!(scribble.selected(xcoord, ycoord) == null))
                        return scribble;
                    break;

                case "typeLine":
                    Line line = (Line) shape;
                    if (!(line.selected(xcoord, ycoord) == null))
                        return line;
                    break;

                case "typeRectangle":
                    Rectangle rectangle = (Rectangle) shape;
                    if (!(rectangle.selected(xcoord, ycoord) == null))
                        return rectangle;
                    break;

                case "typeEllipse":
                    Ellipse ellipse = (Ellipse) shape;
                    if (!(ellipse.selected(xcoord, ycoord) == null))
                        return ellipse;
                    break;

                case "typeCircle":
                    Ellipse circle = (Ellipse) shape;
                    if (!(circle.selected(xcoord, ycoord) == null))
                        return circle;
                    break;

                case "typeSquare":
                    Rectangle square = (Rectangle) shape;
                    if (!(square.selected(xcoord, ycoord) == null))
                        return square;
                    break;

                case "typeOpenPolygon":
                    Polygon openPolygon = (Polygon) shape;
                    if (!(openPolygon.selected(xcoord, ycoord) == null))
                        return openPolygon;
                    break;

                case "typeClosedPolygon":
                    Polygon closedPolygon = (Polygon) shape;
                    if (!(closedPolygon.selected(xcoord, ycoord) == null))
                        return closedPolygon;
                    break;

            }
        }
        return null;
    }

    public void pasteShapes(Shape shape, int coordx, int coordy, boolean isCopy) { //paste shapes
        switch (shape.t) {
            case "typeScribble":
                Scribble scribble = new Scribble((Scribble) shape);
                scribble.move(coordx, coordy);
                listOfShapes.add(scribble);
                break;

            case "typeLine":
                Line line = new Line((Line) shape);
                line.move(coordx, coordy);
                listOfShapes.add(line);
                break;

            case "typeRectangle":
                Rectangle rectangle = new Rectangle((Rectangle) shape);
                rectangle.move(coordx, coordy);
                listOfShapes.add(rectangle);
                break;

            case "typeEllipse":
                Ellipse ellipse = new Ellipse((Ellipse) shape);
                ellipse.move(coordx, coordy);
                listOfShapes.add(ellipse);
                break;

            case "typeCircle":
                Ellipse circle = new Ellipse((Ellipse) shape);
                circle.move(coordx, coordy);
                listOfShapes.add(circle);
                break;

            case "typeSquare":
                Rectangle square = new Rectangle((Rectangle) shape);
                square.move(coordx, coordy);
                listOfShapes.add(square);
                break;

            case "typeOpenPolygon":
                Polygon op = new Polygon((Polygon) shape);
                op.move(coordx, coordy);
                listOfShapes.add(op);
                break;

            case "typeClosedPolygon":
                Polygon closedPolygon = new Polygon((Polygon) shape);
                closedPolygon.move(coordx, coordy);
                listOfShapes.add(closedPolygon);
                break;
        }

    }

    public void repainting() {
        repaint();
    }

    public void saveCurrentState(ArrayList<Shape> shapes, ArrayList<Shape> groups) {
        ArrayList<Shape> tempShapes = new ArrayList<Shape>(); //adding shapes to the temperory array
        ArrayList<Shape> tempGroups = new ArrayList<Shape>();// adding groups to the temperory array
        dpct.copyArrays(tempShapes, tempGroups, shapes, groups); // copying shapes and groups to the temperory array
        Memento memento = new Memento(tempShapes, tempGroups);//temp momento 
        dpct.addMemento(memento); //adding the temp momento to the undo stack 
    }

    public void saveImage() { //save the draw panel as an image 
        int h = this.getHeight();
        int w = this.getWidth();
        BufferedImage BI = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = BI.createGraphics();
        this.paintAll(g);
        try {
            ImageIO.write(BI, "png", new File("./SketchPad.png"));
            System.out.println("Sketchpad saved");

        } catch (IOException error) {
            System.out.println("Error occured while saving: ");
            error.printStackTrace();
        }
    }

}
