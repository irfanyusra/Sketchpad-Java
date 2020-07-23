
import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Mode mode;

    public Color clr;

    public Menu() {
        clr = Color.white;
        // setBorder(BorderFactory.createLineBorder(Color.red));

        setBackground(Color.black);
        JButton scribbleBtn = new JButton("Scribble");
        scribbleBtn.addActionListener(e -> mode.modeSet(0));
        add(scribbleBtn);

        JButton lineBtn = new JButton("Line");
        lineBtn.addActionListener(e -> mode.modeSet(1));
        add(lineBtn);

        JButton circleBtn = new JButton("Circle");
        circleBtn.addActionListener(e -> mode.modeSet(5));
        add(circleBtn);

        JButton ellipseBtn = new JButton("Ellipse");
        ellipseBtn.addActionListener(e -> mode.modeSet(3));
        add(ellipseBtn);

        JButton squareBtn = new JButton("Square");
        squareBtn.addActionListener(e -> mode.modeSet(4));
        add(squareBtn);

        JButton rectangleBtn = new JButton("Rectangle");
        rectangleBtn.addActionListener(e -> mode.modeSet(2));
        add(rectangleBtn);

        JButton closedPolygonBtn = new JButton("Closed Polygon");
        closedPolygonBtn.addActionListener(e -> mode.modeSet(7));
        add(closedPolygonBtn);

        JButton openPolygonBtn = new JButton("Open Polygon");
        openPolygonBtn.addActionListener(e -> mode.modeSet(6));
        add(openPolygonBtn);

        JButton finishPolygonBtn = new JButton("Finish Polygon");
        finishPolygonBtn.addActionListener(e -> {
            mode.finishPolygon();
            mode.modeSet(14);
        });
        add(finishPolygonBtn);

        JButton groupBtn = new JButton("Select or Group");
        groupBtn.addActionListener(e -> mode.modeSet(11));
        add(groupBtn);

        JButton ungroupBtn = new JButton("Ungroup");
        ungroupBtn.addActionListener(e -> mode.modeSet(13));
        ungroupBtn.addActionListener(e -> mode.ungroup());
        add(ungroupBtn);

        JButton moveBtn = new JButton("Move");
        moveBtn.addActionListener(e -> mode.modeSet(10));
        add(moveBtn);

        JButton cutBtn = new JButton("Cut");
        cutBtn.addActionListener(e -> mode.cut());
        cutBtn.addActionListener(e -> mode.modeSet(8));
        add(cutBtn);

        JButton copyBtn = new JButton("Copy");
        copyBtn.addActionListener(e -> mode.copy());
        copyBtn.addActionListener(e -> mode.modeSet(12));
        add(copyBtn);

        JButton pasteBtn = new JButton("Paste");
        pasteBtn.addActionListener(e -> mode.modeSet(9));
        add(pasteBtn);

        JButton chooseColorBtn = new JButton("Choose Colour");
        chooseColorBtn.addActionListener(e -> {
            clr = JColorChooser.showDialog(null, "Choose a colour", clr);
            if (clr == null) {
                clr = Color.white;
            }
        });
        add(chooseColorBtn);

        JButton undoBtn = new JButton("Undo");
        undoBtn.addActionListener(e -> mode.undo());
        undoBtn.addActionListener(e -> mode.modeSet(15));
        add(undoBtn);

        JButton redoBtn = new JButton("Redo");
        redoBtn.addActionListener(e -> mode.redo());
        redoBtn.addActionListener(e -> mode.modeSet(16));
        add(redoBtn);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> mode.save());
        add(saveBtn);

    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 100);
    }
}