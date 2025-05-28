import javax.swing.*;
import java.awt.*;

public class Registered extends JFrame {
    public Registered() {
        setTitle("Pendaftaran Berhasil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Center screen
        setLayout(null); // Manual positioning

        // Tambahkan GIF
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/assets/check.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(150, 15, 400, 300); // Ukuran dan posisi GIF disesuaikan
        add(gifLabel);

        // Label "PENDAFTARAN BERHASIL" (menimpa GIF)
        JLabel successLabel = new JLabel("PENDAFTARAN BERHASIL");
        successLabel.setFont(new Font("Arial", Font.BOLD, 28));
        successLabel.setForeground(new Color(0, 128, 0));
        successLabel.setBounds(180, 280, 400, 50); // Geser ke atas menimpa GIF
        add(successLabel);

        // Tombol MASUK
        JButton menuButton = new JButton("MENU");
        menuButton.setFont(new Font("Arial", Font.BOLD, 16));
        menuButton.setBackground(new Color(0, 100, 255));
        menuButton.setForeground(Color.WHITE);
        menuButton.setFocusPainted(false);
        menuButton.setBounds(180, 350, 120, 40);
        add(menuButton);

        // Tombol MASUK
        JButton masukButton = new JButton("MASUK");
        masukButton.setFont(new Font("Arial", Font.BOLD, 16));
        masukButton.setBackground(new Color(0, 100, 255));
        masukButton.setForeground(Color.WHITE);
        masukButton.setFocusPainted(false);
        masukButton.setBounds(408, 350, 120, 40);
        add(masukButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Registered().setVisible(true);
        });
    }
}
