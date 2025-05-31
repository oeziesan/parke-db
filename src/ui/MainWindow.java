package ui;

import utils.ImageButton;
import javax.swing.*;
import utils.NeumorphicGradientPanel;

public class MainWindow extends JFrame {
    public MainWindow() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ganti background default dengan panel gradien neumorphic
        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null); // untuk mempertahankan layout absolut

        ImageButton btnEntry = new ImageButton(
                "/assets/btnEntryReg.png",
                "/assets/btnEntryHover.png",
                "/assets/btnEntryClicked.png"
        );
        btnEntry.setBounds(180, 230, 125, 45);
        backgroundPanel.add(btnEntry);

        ImageButton btnDaftar = new ImageButton(
                "/assets/btnRegReg.png",
                "/assets/btnRegHover.png",
                "/assets/btnRegClicked.png"
        );
        btnDaftar.setBounds(180, 330, 125, 45);
        backgroundPanel.add(btnDaftar);

        ImageButton btnExit1 = new ImageButton(
                "/assets/btnExitReg.png",
                "/assets/btnExitHover.png",
                "/assets/btnExitClicked.png"
        );
        btnExit1.setBounds(400, 230, 125, 45);
        backgroundPanel.add(btnExit1);

        ImageButton btnAdd = new ImageButton(
                "/assets/btnAddReg.png",
                "/assets/btnAddHover.png",
                "/assets/btnAddClicked.png"
        );
        btnAdd.setBounds(400, 330, 125, 45);
        backgroundPanel.add(btnAdd);

        ImageButton btnRoot = new ImageButton(
                "/assets/btnRootReg.png",
                "/assets/btnRootHover.png",
                "/assets/btnRootClicked.png"
        );
        btnRoot.setBounds(600, 10, 125, 60);
        backgroundPanel.add(btnRoot);

        btnDaftar.addActionListener(e -> {
            new RegistGate().setVisible(true);
            dispose();
        });

        btnRoot.addActionListener(e -> {
            new LoginRoot().setVisible(true);
            dispose();
        });

        btnEntry.addActionListener(e -> {
            new EntryGate().setVisible(true);
            dispose();
        });

        btnExit1.addActionListener(e -> {
            new ExitGate().setVisible(true);
            dispose();
        });

        btnAdd.addActionListener(e -> {
            new AddVehicle().setVisible(true);
            dispose();
        });

        // Set panel gradien sebagai background
        setContentPane(backgroundPanel);
    }
}
