import java.awt.*;

//part of the command pattern 
public interface Command {
public void modeSet(int m);
public void undo();
public void redo();
public Color getMenuColour();
}