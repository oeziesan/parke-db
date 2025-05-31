package utils;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class FontLoaderThin {
    private static Font customFont;

    static {
        try {
            InputStream is = FontLoaderThin.class.getResourceAsStream("/assets/fonts/Poppins-Light.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.PLAIN, 14); // fallback
        }
    }

    public static Font getFont(float size, int style) {
        return customFont.deriveFont(style, size);
    }

    public static void applyToLabel(JLabel label, float size, int style) {
        label.setFont(getFont(size, style));
    }
}
