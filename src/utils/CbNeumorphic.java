package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CbNeumorphic<E> extends JComboBox<E> {

    private static final Color BACKGROUND = new Color(240, 240, 240); // Base light gray
    private static final Color SHADOW_LIGHT = new Color(255, 255, 255, 180); // Top-left highlight
    private static final Color SHADOW_DARK = new Color(200, 200, 200, 180); // Bottom-right shadow

    public CbNeumorphic(E[] items) {
        super(items);
        setOpaque(false);
        setFont(new Font("Poppins", Font.PLAIN, 14));
        setForeground(new Color(60, 60, 60));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(3, 10, 3, 10));
        setRenderer(new NeumorphicRenderer<>());
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();

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

    private static class NeumorphicRenderer<E> extends JLabel implements ListCellRenderer<E> {

        public NeumorphicRenderer() {
            setOpaque(true);
            setFont(new Font("Poppins", Font.PLAIN, 14));
            setBorder(new EmptyBorder(5, 10, 5, 10));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list, E value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(value != null ? value.toString() : "");

            if (isSelected) {
                setBackground(new Color(220, 220, 220));
                setForeground(new Color(30, 30, 30));
            } else {
                setBackground(BACKGROUND);
                setForeground(new Color(60, 60, 60));
            }

            return this;
        }
    }
}
