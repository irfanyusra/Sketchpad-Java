
import javax.swing.*;
import java.awt.*;

public class ModePanel extends JPanel {
    public Color clr;
    public JLabel modeLabel;

    public ModePanel() {
        // this.dp=dp;

        clr = Color.white;
        setBorder(BorderFactory.createLineBorder(Color.blue));
        setBackground(Color.black);
        modeLabel = new JLabel("Mode: Scribble");
        modeLabel.setForeground(Color.white);
        add(modeLabel);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 30);
    }
}