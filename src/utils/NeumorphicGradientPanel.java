package utils;
import javax.swing.*;
import java.awt.*;

public class NeumorphicGradientPanel extends JPanel {

    private final Color colorStart = Color.decode("#FFFFFF");
    private final Color colorEnd = Color.decode("#8183FF");

    public NeumorphicGradientPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(
                0, 0, colorStart,
                0, height, colorEnd
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}
