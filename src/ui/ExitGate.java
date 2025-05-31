package ui;

import javax.swing.*;

import Scanner.FaceCaptureListener;
import Scanner.FaceScanner;
import db.dbhandler;
import utils.ImageButton;
import utils.NeumorphicGradientPanel;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ExitGate extends JFrame {
    private float[] scannedFaceEmbedding = null;
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

        JTextField platField = new JTextField();
        platField.setBounds(235, 250, 250, 50);
        platField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 22));
        backgroundPanel.add(platField);


        /*
        ImageButton platButton = new ImageButton(
                "/assets/btnPlatReg.png",
                "/assets/btnPlatHover.png",
                "/assets/btnPlatClicked.png"
        );
        platButton.setBounds(235, 250, 250, 50);
        backgroundPanel.add(platButton);
         */

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

        scanButton.addActionListener(e -> {
            EntryGate EG = new EntryGate();
            FaceScanner scanner = new FaceScanner(new FaceCaptureListener() {
                public void onFaceCaptured(byte[] faceBytes) {
                    if (faceBytes != null) {
                        scannedFaceEmbedding = EG.byteArrayToFloatArray(faceBytes);
                        JOptionPane.showMessageDialog(ExitGate.this, "Wajah berhasil di-capture!");
                    } else {
                        JOptionPane.showMessageDialog(ExitGate.this, "Gagal capture wajah!");
                    }
                }
            });
            scanner.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            dbhandler db = new db.dbhandler();
            if (scannedFaceEmbedding == null) {
                JOptionPane.showMessageDialog(this, "Silakan scan wajah terlebih dahulu.");
                return;
            }
            String plat = platField.getText().trim();
            if (plat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Plat nomor belum diisi.");
                return;
            }

            //1.FACE-NOT-SIGNED
            int matchedIdentityId = db.findMatchingFaceId(scannedFaceEmbedding, db);
            if (matchedIdentityId == -1) {
                JOptionPane.showMessageDialog(this, "Wajah Tidak Terdaftar!");
                return;
            }
            //2.PLAT-NOT-SIGNED
            int platIdentityId = db.findIdentityIdByPlat(plat, db);
            if (platIdentityId == -1) {
                JOptionPane.showMessageDialog(this, "Plat Tidak Terdaftar!");
                return;
            }
            //3.FACE-PLAT-NOT-MATCH
            if (matchedIdentityId != platIdentityId) {
                JOptionPane.showMessageDialog(this, "Peringatan! Wajah dengan plat tidak cocok.");
                return;
            }
            //4.SUCCESS
            LocalDateTime exitTime = LocalDateTime.now();
            Timestamp exitTimestamp = Timestamp.valueOf(exitTime);
            int id = db.findVehicleIDbyPLAT(platField.getText());
            db.exit_log(exitTimestamp, id);

            String logdata = db.log_exitWindow(id);
            JOptionPane.showMessageDialog(null, logdata);

            new SuccessExit().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}
