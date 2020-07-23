
import java.util.*;

public class Memento {
    public ArrayList<Shape> listOfShapesState = new ArrayList<Shape>();
    public ArrayList<Shape> listOfGroupState = new ArrayList<Shape>();

    public Memento(ArrayList<Shape> shapes, ArrayList<Shape> groups) {// 
        this.listOfShapesState = shapes;
        this.listOfGroupState = groups;
    }
}