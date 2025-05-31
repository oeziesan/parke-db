package ui;

import javax.swing.*;
import utils.ImageButton;
import utils.NeumorphicGradientPanel;

public class ExitGate extends JFrame {
    public ExitGate() {
        setTitle("Gerbang Keluar");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        ImageButton scanButton = new ImageButton(
                "/assets/btnScanReg.png",
                "/assets/btnScanHover.png",
                "/assets/btnScanClicked.png"
        );
        scanButton.setBounds(250, 30, 220, 220);
        backgroundPanel.add(scanButton);

        ImageButton platButton = new ImageButton(
                "/assets/btnPlatReg.png",
                "/assets/btnPlatHover.png",
                "/assets/btnPlatClicked.png"
        );
        platButton.setBounds(235, 250, 250, 50);
        backgroundPanel.add(platButton);

        ImageButton btnExit = new ImageButton(
                "/assets/btnExitReg.png",
                "/assets/btnExitHover.png",
                "/assets/btnExitClicked.png"
        );
        btnExit.setBounds(300, 310, 125, 45);
        backgroundPanel.add(btnExit);

        ImageButton btnBack = new ImageButton(
                "/assets/btnBackReg.png",
                "/assets/btnBackHover.png",
                "/assets/btnBackClicked.png"
        );
        btnBack.setBounds(-10, 20, 125, 60);
        btnBack.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
        backgroundPanel.add(btnBack);

        setContentPane(backgroundPanel);
    }
}
