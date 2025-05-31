package ui;
import utils.FontLoaderBold;
import utils.ImageButton;
import utils.NeumorphicGradientPanel;

import javax.swing.*;
import java.awt.*;

public class SuccessEntry extends JFrame {
    public SuccessEntry() {
        setTitle("Sukses Masuk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/assets/check.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(150, -50, 400, 300); // Ukuran dan posisi GIF disesuaikan
        backgroundPanel.add(gifLabel);

        JLabel successLabel = new JLabel("SELAMAT DATANG");
        FontLoaderBold.applyToLabel(successLabel, 24f, Font.PLAIN);
        successLabel.setBounds(235, 200, 400, 50);
        backgroundPanel.add(successLabel);

        ImageButton menuButton = new ImageButton(
                "/assets/btnMenuReg.png",
                "/assets/btnMenuHover.png",
                "/assets/btnMenuClicked.png"
        );
        menuButton.setBounds(180, 270, 125, 45);
        backgroundPanel.add(menuButton);

        ImageButton masukButton = new ImageButton(
                "/assets/btnEntryReg.png",
                "/assets/btnEntryHover.png",
                "/assets/btnEntryClicked.png"
        );
        masukButton.setBounds(408, 270, 125, 45);
        backgroundPanel.add(masukButton);

        menuButton.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        masukButton.addActionListener(e -> {
            new EntryGate().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}