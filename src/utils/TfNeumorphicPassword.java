package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TfNeumorphicPassword extends JTextField {

    private static final Color BACKGROUND = new Color(240, 240, 240); // Light gray base
    private static final Color SHADOW_LIGHT = new Color(255, 255, 255, 180); // Top-left light
    private static final Color SHADOW_DARK = new Color(200, 200, 200, 180); // Bottom-right shadow

    public TfNeumorphicPassword(int columns) {
        super(columns);
        setOpaque(false);
        setFont(new Font("Poppins", Font.PLAIN, 0));
        setForeground(new Color(60, 60, 60));
        setBorder(new EmptyBorder(0, 10, 0, 10));
        setBackground(BACKGROUND);
        setCaretColor(Color.GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw base
        g2.setColor(BACKGROUND);
        g2.fillRoundRect(0, 0, width, height, 20, 20);

        // Draw light shadow (top-left)
        g2.setColor(SHADOW_LIGHT);
        g2.drawRoundRect(1, 1, width - 3, height - 3, 20, 20);

        // Draw dark shadow (bottom-right)
        g2.setColor(SHADOW_DARK);
        g2.drawRoundRect(2, 2, width - 4, height - 4, 20, 20);

        g2.dispose();
        super.paintComponent(g);
    }
}
