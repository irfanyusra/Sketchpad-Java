
import java.awt.*;

//Mode (Command)
public class Mode implements Command {

    private DrawPanel drawPanel;
    private Menu menu;
    public boolean isPolygonConnected = false;
    public int modeValue = 0;
    private DrawPanelCareTaker dpct;
    private ModePanel modePanel;

    Mode(ModePanel mp) {
        this.modePanel = mp;

    }

    @Override
    public void modeSet(int mode) {
        if (drawPanel != null && menu != null) {
            // to automatically finsh the polygon when mode is changed
            finishPolygon();
            modeValue = mode;

            modePanel.modeLabel.setText("Mode: " + getModeString());
        }
    }

    @Override
    public Color getMenuColour() {
        return menu.clr;
    }

    @Override
    public void undo() {
        dpct.undo();
    }

    @Override
    public void redo() {
        dpct.redo();

    }

    public void save() {
        drawPanel.saveImage();
    }

    public void cut() {
        drawPanel.myMouseHandler.cut();
    }

    public void copy() {
        drawPanel.myMouseHandler.copy();
    }

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setModePanel(ModePanel mp) {
        this.modePanel = mp;
    }

    public void setDPCT(DrawPanelCareTaker dpct) {
        this.dpct = dpct;
    }

    public void connectPolygon() {
        isPolygonConnected = true;
    }

    public void finishPolygon() {
        drawPanel.myMouseHandler.isClosedPolygon();
        isPolygonConnected = false;
    }

    public void ungroup() {
        drawPanel.ungroup();
    }

    public String getModeString() {
        String modeString = "";
        switch (modeValue) {
            case 0:
                modeString = "Scribble";
                break;
            case 1:
                modeString = "Line";
                break;
            case 2:
                modeString = "Rectangle";
                break;
            case 3:
                modeString = "Ellipse";
                break;
            case 4:
                modeString = "Square";
                break;
            case 5:
                modeString = "Circle";
                break;
            case 6:
                modeString = "Open Polygon";
                break;
            case 7:
                modeString = "Closed Polygon";

                break;
            case 8: // cut
                modeString = "Cut";

                break;
            case 9: // paste
                modeString = "Paste";
                break;

            case 10: // move
                modeString = "Move";
                break;

            case 11: // copy
                modeString = "Select or Group";
                break;
            case 12: // copy
                modeString = "Copy";
                break;
            case 13: // copy
                modeString = "Ungroup";
                break;

            case 14: // copy
                modeString = "Finish Polygon";
                break;

            case 15: // copy
                modeString = "Undo";
                break;
            case 16: // copy
                modeString = "Redo";
                break;

        }
        return modeString;
    }

}
