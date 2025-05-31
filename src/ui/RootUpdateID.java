package ui;
import Scanner.FaceCaptureListener;
import Scanner.FaceScanner;
import utils.FontLoaderThin;
import db.dbhandler;
import utils.ImageButton;
import utils.TfNeumorphic;
import utils.NeumorphicGradientPanel;
import javax.swing.*;
import java.awt.*;

public class RootUpdateID extends JFrame {
    private JTextField tfBefore, tfNIM, tfNama;
    private byte[] embeddedFace;
    private JLabel lbValidate;

    public RootUpdateID() {
        setTitle("Update ID");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        //Label dan Input Field Data yang harus dirubah
        JLabel lbbefore = new JLabel("Data Sebelumnya ");
        FontLoaderThin.applyToLabel(lbbefore, 14f, Font.PLAIN);
        lbbefore.setBounds(100, 60, 150, 25);
        TfNeumorphic tfBefore = new TfNeumorphic(20);
        tfBefore.setText("Masukkan NIM / NAMA");
        tfBefore.setBounds(250, 60, 200, 25);

        //Tombol Check
        ImageButton btnCheck = new ImageButton(
                "/assets/btnCheckReg.png",
                "/assets/btnCheckHover.png",
                "/assets/btnCheckClicked.png"
        );
        btnCheck.setBounds(500,60, 120, 45);

        //NAMA
        JLabel lbnama = new JLabel("NAMA : ");
        FontLoaderThin.applyToLabel(lbnama, 14f, Font.PLAIN);
        lbnama.setBounds(100, 150, 150, 25);
        TfNeumorphic tfNama = new TfNeumorphic(20);
        tfNama.setBounds(250, 150, 200, 25);

        //NIM
        JLabel lbnim = new JLabel("NIM : ");
        FontLoaderThin.applyToLabel(lbnim, 14f, Font.PLAIN);
        lbnim.setBounds(100, 200, 150, 25);
        TfNeumorphic tfNIM = new TfNeumorphic(20);
        tfNIM.setBounds(250, 200, 200, 25);

        //SCAN
        ImageButton btnScan = new ImageButton(
                "/assets/btnScanReg.png",
                "/assets/btnScanHover.png",
                "/assets/btnScanClicked.png"
        );
        btnScan.setBounds(460, 120, 218, 216);

        ImageButton btnUpdate = new ImageButton(
                "/assets/btnUpdateReg.png",
                "/assets/btnUpdateHover.png",
                "/assets/btnUpdateClicked.png"
        );
        btnUpdate.setBounds(280, 250, 120, 40);

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
            String tValidate = db.showID(tfBefore.getText(), tfBefore.getText());
            if (lbValidate != null) {
                backgroundPanel.remove(lbValidate);
            }
            lbValidate = new JLabel(tValidate);
            lbValidate.setBounds(250, 100, 200, 25);

            if (tValidate.equals("Data Tidak Ditemukan")) {
                backgroundPanel.remove(lbnama);
                backgroundPanel.remove(tfNama);
                backgroundPanel.remove(lbnim);
                backgroundPanel.remove(tfNIM);
                backgroundPanel.remove(btnScan);
                backgroundPanel.remove(btnUpdate);
                revalidate();
                repaint();
            } else {
                backgroundPanel.add(lbnama);
                backgroundPanel.add(tfNama);
                backgroundPanel.add(lbnim);
                backgroundPanel.add(tfNIM);
                backgroundPanel.add(btnScan);
                backgroundPanel.add(btnUpdate);
            }
            backgroundPanel.add(lbValidate);
            revalidate();
            repaint();
        });

        btnUpdate.addActionListener(e -> {
            if (tfNama.getText() == null || tfNama.getText().trim().isEmpty() ||
                    tfNIM.getText() == null || tfNIM.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi Terlebih semua data!");
                return;
            }
            String nimCondition = tfNIM.getText();
            dbhandler db = new dbhandler();
            db.updateID(tfNama.getText(), tfNIM.getText(), embeddedFace, nimCondition);
            JOptionPane.showMessageDialog(this, "Data Berhasil di-Update!");
        });

        btnScan.addActionListener(e -> {
            FaceScanner scanner = new FaceScanner(new FaceCaptureListener() {
                public void onFaceCaptured(byte[] faceBytes) {
                    RootUpdateID.this.embeddedFace = faceBytes;
                }
            });
            scanner.setVisible(true);
        });

        btnBack.addActionListener(e -> {
            new Root().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}
