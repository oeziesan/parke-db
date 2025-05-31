package ui;
import utils.FontLoaderBold;
import utils.ImageButton;

import javax.swing.*;
import java.awt.*;
import utils.NeumorphicGradientPanel;

public class SuccessExit extends JFrame {
    public SuccessExit() {
        setTitle("Sukses Keluar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        // Tambahkan GIF
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/assets/check.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(150, -50, 400, 300); // Ukuran dan posisi GIF disesuaikan
        backgroundPanel.add(gifLabel);

        // Label "PENDAFTARAN BERHASIL" (menimpa GIF)
        JLabel successLabel = new JLabel("SELAMAT TINGGAL");
        FontLoaderBold.applyToLabel(successLabel, 24f, Font.PLAIN);
        successLabel.setBounds(200, 200, 400, 50);
        backgroundPanel.add(successLabel);

        // Tombol MENU
        ImageButton menuButton = new ImageButton(
                "/assets/btnMenuReg.png",
                "/assets/btnMenuHover.png",
                "/assets/btnMenuClicked.png"
        );
        menuButton.setBounds(180, 270, 125, 45);
        backgroundPanel.add(menuButton);

        // Tombol MASUK
        ImageButton keluarButton = new ImageButton(
                "/assets/btnExitReg.png",
                "/assets/btnExitHover.png",
                "/assets/btnExitClicked.png"
        );
        keluarButton.setBounds(408, 270, 125, 45);
        backgroundPanel.add(keluarButton);

        menuButton.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
    }
}