package ui;

import db.dbhandler;
import utils.FontLoaderThin;
import utils.ImageButton;
import utils.TfNeumorphic;
import javax.swing.*;
import java.awt.*;
import utils.NeumorphicGradientPanel;

public class RootDeleteID extends JFrame {
    private JTextField tfBefore, tfNIM, tfNama;
    private byte[] embeddedFace;
    private JLabel lbValidate;

    public RootDeleteID() {
        setTitle("Delete ID");
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
        tfBefore.setText("Masukkan NIM / NAMA");
        tfBefore.setBounds(250, 60, 200, 25);

        //Tombol Check
        ImageButton btnDel = new ImageButton(
                "/assets/btnDelReg.png",
                "/assets/btnDelHover.png",
                "/assets/btnDelClicked.png"
        );
        btnDel.setBounds(500,60, 120, 45);

        ImageButton btnBack = new ImageButton(
                "/assets/btnBackReg.png",
                "/assets/btnBackHover.png",
                "/assets/btnBackClicked.png"
        );
        btnBack.setBounds(-10, 20, 125, 60);

        // Tambah semua ke frame
        backgroundPanel.add(lbbefore);
        backgroundPanel.add(tfBefore);
        backgroundPanel.add(btnDel);
        backgroundPanel.add(btnBack);

        //CEK_DATA
        btnDel.addActionListener(e -> {
            dbhandler db = new dbhandler();
            String tValidate = db.delID(tfBefore.getText(), tfBefore.getText());
            if (lbValidate != null) {
                backgroundPanel.remove(lbValidate);
            }
            lbValidate = new JLabel(tValidate);
            lbValidate.setBounds(250, 100, 200, 25);
            backgroundPanel.add(lbValidate);
            revalidate();
            repaint();
        });

        btnBack.addActionListener(e -> {
            new root().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}
