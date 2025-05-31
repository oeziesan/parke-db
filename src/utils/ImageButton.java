package utils;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ImageButton extends JButton {
    private final Icon regularIcon;
    private final Icon hoverIcon;
    private final Icon clickedIcon;

    public ImageButton(String regularPath, String hoverPath, String clickedPath) {
        this.regularIcon = new ImageIcon(getClass().getResource(regularPath));
        this.hoverIcon = new ImageIcon(getClass().getResource(hoverPath));
        this.clickedIcon = new ImageIcon(getClass().getResource(clickedPath));

        setIcon(regularIcon);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createLineBorder(Color.BLUE));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setIcon(hoverIcon);
            }
            public void mouseExited(MouseEvent e) {
                setIcon(regularIcon);
            }
            public void mousePressed(MouseEvent e) {
                setIcon(clickedIcon);
            }
            public void mouseReleased(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setIcon(hoverIcon);
                } else {
                    setIcon(regularIcon);
                }
            }
        });
    }
}