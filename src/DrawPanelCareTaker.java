
import java.util.*;

public class DrawPanelCareTaker {

    private DrawPanel drawPanel;
    public Stack<Memento> undoShapes = new Stack<Memento>(); // undo shapes stack
    public Stack<Memento> redoShapes = new Stack<Memento>();// redo shapes stack

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    public void redo() { // redo function
        if (redoShapes.size() <= 0)
            return;
        // is redo stack size is 1 and greate
        Memento m = redoShapes.pop(); // takes the last redo
        ArrayList<Shape> shapesTemp = new ArrayList<Shape>();// temp shapes array
        ArrayList<Shape> groupsTemp = new ArrayList<Shape>(); // temp groups array
        copyArrays(shapesTemp, groupsTemp, m.listOfShapesState, m.listOfGroupState); // copying shapes and groups in
                                                                                     // temp arrays
        drawPanel.listOfShapes = shapesTemp; // making temp array the draw panel array
        drawPanel.ShapesInGroup = groupsTemp; // making temp array the draw panel array
        Memento newMemento = new Memento(drawPanel.listOfShapes, drawPanel.ShapesInGroup); // temp memento
        undoShapes.push(newMemento); // pushes redone
        drawPanel.repainting();
    }

    public void copyArrays(ArrayList<Shape> newShapes, ArrayList<Shape> newGroups, ArrayList<Shape> toCopyShapes,
            ArrayList<Shape> toCopyGroups) {

        for (Shape shape : toCopyShapes) { // going through all the
            switch (shape.t) {
                case "typeScribble":
                    Scribble scribble = new Scribble((Scribble) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(scribble);
                    newShapes.add(scribble);
                    break;
                case "typeLine":
                    Line line = (Line) shape;
                    if (toCopyGroups.contains(shape))
                        newGroups.add(line);
                    newShapes.add(line);
                    break;
                case "typeRectangle":
                    Rectangle rectangle = new Rectangle((Rectangle) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(rectangle);
                    newShapes.add(rectangle);
                    break;
                case "typeEllipse":
                    Ellipse ellipse = new Ellipse((Ellipse) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(ellipse);
                    newShapes.add(ellipse);
                    break;
                case "typeCircle":
                    Ellipse circle = new Ellipse((Ellipse) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(circle);
                    newShapes.add(circle);
                    break;
                case "typeSquare":
                    Rectangle square = new Rectangle((Rectangle) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(square);
                    newShapes.add(square);
                    break;
                case "typeOpenPolygon":
                    Polygon openPolygon = new Polygon((Polygon) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(openPolygon);
                    newShapes.add(openPolygon);
                    break;
                case "typeClosedPolygon":
                    Polygon closedPolygon = new Polygon((Polygon) shape);
                    if (toCopyGroups.contains(shape))
                        newGroups.add(closedPolygon);
                    newShapes.add(closedPolygon);
                    break;
            }
            if (shape.t.equals("typeClosedPolygon")) {
                Polygon tempClosedPolygon = new Polygon((Polygon) shape);
                if (toCopyGroups.contains(shape))
                    newGroups.add(tempClosedPolygon);
                newShapes.add(tempClosedPolygon);
            }
        }
    }

    public void undo() {
        if (undoShapes.size() < 1) // cant undo with an empty undo stack
            return;

        // greater and equal to 1
        redoShapes.push(undoShapes.pop());
        switch (undoShapes.size()) {
            case 1:
                drawPanel.listOfShapes = new ArrayList<Shape>();
                break;
            default:
                ArrayList<Shape> shapesTemp = new ArrayList<Shape>(); // temp shapes array
                ArrayList<Shape> groupsTemp = new ArrayList<Shape>(); // temp group shapes array
                Memento m = undoShapes.pop(); // temp memento
                copyArrays(shapesTemp, groupsTemp, m.listOfShapesState, m.listOfGroupState); // copying the states of
                                                                                             // the lists into a temp
                                                                                             // array

                drawPanel.listOfShapes = shapesTemp;
                drawPanel.ShapesInGroup = groupsTemp;
                undoShapes.push(new Memento(shapesTemp, groupsTemp));

                int index = drawPanel.listOfShapes.size() - 1;
                switch (drawPanel.listOfShapes.get(index).t) {
                    case "typeOpenPolygon":
                        drawPanel.myMouseHandler.setOpenPolygon();
                        break;
                    case "typeClosedPolygon":
                        drawPanel.myMouseHandler.setClosedPolygon();
                        break;
                }

                break;
        }
        drawPanel.repainting();
    }

    public void addMemento(Memento memento) {

        undoShapes.push(memento);
        drawPanel.listOfShapes = memento.listOfShapesState;
        drawPanel.ShapesInGroup = memento.listOfGroupState;
        int index = drawPanel.listOfShapes.size() - 1;
        switch (drawPanel.listOfShapes.get(index).t) {
            case "typeOpenPolygon":
                drawPanel.myMouseHandler.setOpenPolygon();
                break;
            case "typeClosedPolygon":
                drawPanel.myMouseHandler.setClosedPolygon();
                break;
        }

        drawPanel.repainting();
    }

    // public void closedPolygon(){

    // }

}