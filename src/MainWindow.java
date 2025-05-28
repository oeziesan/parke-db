import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Label Judul
        JLabel label = new JLabel("P'Okeh - Sistem Parkir", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        label.setBounds(180, 20, 350, 40);
        add(label);

        // Tombol Navigasi
        JButton btnMasuk = new JButton("MASUK");
        JButton btnDaftar = new JButton("DAFTAR");
        JButton btnKeluar1 = new JButton("KELUAR");

        btnKeluar1.setBounds(400, 100, 120, 40);
        btnDaftar.setBounds(290, 170, 120, 40);
        btnMasuk.setBounds(180, 100, 120, 40);

        // Tambahkan tombol ke frame
        add(btnMasuk);
        add(btnDaftar);
        add(btnKeluar1);

        btnDaftar.addActionListener(e -> {
            new RegistGate().setVisible(true); // Buka jendela baru
            dispose(); // Tutup MainWindow ini
        });


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
