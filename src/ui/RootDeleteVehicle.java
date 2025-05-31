package ui;

import db.dbhandler;
import utils.FontLoaderThin;
import utils.ImageButton;
import utils.TfNeumorphic;
import utils.NeumorphicGradientPanel;

import javax.swing.*;
import java.awt.*;

public class RootDeleteVehicle extends JFrame {
    private JTextField tfBefore, tfNIM, tfNama;
    private byte[] embeddedFace;
    private JLabel lbValidate;

    public RootDeleteVehicle() {
        setTitle("Delete Vehicle");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        //Label dan Input Field Data yang harus dirubah
        JLabel lbbefore = new JLabel("Data yang dihapus");
        FontLoaderThin.applyToLabel(lbbefore, 14f, Font.PLAIN);
        lbbefore.setBounds(100, 60, 150, 25);
        TfNeumorphic tfBefore = new TfNeumorphic(20);
        tfBefore.setText("Masukkan NOPOL");
        tfBefore.setBounds(250, 60, 200, 25);

        //Tombol Check
        JButton btnCheck = new JButton("CEK DATA");
        btnCheck.setBounds(500,60, 120, 25);

        ImageButton btnBack = new ImageButton(
                "/assets/btnBackReg.png",
                "/assets/btnBackHover.png",
                "/assets/btnBackClicked.png"
        );
        btnBack.setBounds(-10, 20, 125, 60);

        // Tambah semua ke frame
        backgroundPanel.add(lbbefore);
        backgroundPanel.add(tfBefore);
        backgroundPanel.add(btnCheck);
        backgroundPanel.add(btnBack);

        //CEK_DATA
        btnCheck.addActionListener(e -> {
            dbhandler db = new dbhandler();
            String tValidate = db.delVehicle(tfBefore.getText(), tfBefore.getText());
            if (lbValidate != null) {
                backgroundPanel.add(lbValidate);
            }
            lbValidate = new JLabel(tValidate);
            lbValidate.setBounds(250, 100, 200, 25);
            backgroundPanel.add(lbValidate);
            revalidate();
            repaint();
        });

        btnBack.addActionListener(e -> {
            new Root().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}