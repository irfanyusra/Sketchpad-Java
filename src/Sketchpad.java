
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Sketchpad {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui();
            }
        });
    }

    private static void gui() {
        JFrame jframe = new JFrame("Sketchpad Demo");
        Menu menu = new Menu();
        DrawPanelCareTaker dpct = new DrawPanelCareTaker();
        ModePanel modePanel = new ModePanel();
        Mode mode = new Mode(modePanel);
        DrawPanel drawPanel = new DrawPanel(mode, dpct);

        dpct.setDrawPanel(drawPanel);
        mode.setDrawPanel(drawPanel);
        mode.setMenu(menu);
        mode.setModePanel(modePanel);
        mode.setDPCT(dpct);
        menu.setMode(mode);

        jframe.add(menu, BorderLayout.PAGE_START); // center and page end makes sure they arent overlapping.
        jframe.add(drawPanel, BorderLayout.CENTER);
        jframe.add(modePanel, BorderLayout.PAGE_END);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setSize(800, 650);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
