package ui;

import javax.swing.*;
import Scanner.FaceCaptureListener;
import Scanner.FaceScanner;
import utils.ImageButton;
import utils.NeumorphicGradientPanel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import db.dbhandler;

public class EntryGate extends JFrame {
    public float[] byteArrayToFloatArray(byte[] embeddingBytes) {
        float[] embedding = new float[embeddingBytes.length / 4];
        ByteBuffer bb = ByteBuffer.wrap(embeddingBytes).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < embedding.length; i++) {
            embedding[i] = bb.getFloat();
        }
        return embedding;
    }
    private float[] scannedFaceEmbedding = null;

    public EntryGate() {
        setTitle("Gerbang Masuk");
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


        /* SEMENTARA GA DIGUNAIN
        ImageButton platButton = new ImageButton(
                "/assets/btnPlatReg.png",
                "/assets/btnPlatHover.png",
                "/assets/btnPlatClicked.png"
        );
        platButton.setBounds(235, 250, 250, 50);
        backgroundPanel.add(platButton);
         */

        ImageButton btnEntry = new ImageButton(
                "/assets/btnEntryReg.png",
                "/assets/btnEntryHover.png",
                "/assets/btnEntryClicked.png"
        );
        btnEntry.setBounds(300, 310, 125, 45);
        backgroundPanel.add(btnEntry);

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
            FaceScanner scanner = new FaceScanner(new FaceCaptureListener() {
                public void onFaceCaptured(byte[] faceBytes) {
                    if (faceBytes != null) {
                        scannedFaceEmbedding = byteArrayToFloatArray(faceBytes);
                        JOptionPane.showMessageDialog(EntryGate.this, "Wajah berhasil di-capture!");
                    } else {
                        JOptionPane.showMessageDialog(EntryGate.this, "Gagal capture wajah!");
                    }
                }
            });
            scanner.setVisible(true);
        });

        btnEntry.addActionListener(e -> {
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
            ZoneId jakartaZone = ZoneId.of("Asia/Jakarta"); //SET TO JAKARTA (GMT+7)
            ZonedDateTime jakartaDateTime = ZonedDateTime.now(jakartaZone);
            LocalDateTime entryTime = jakartaDateTime.toLocalDateTime();
            Timestamp entryTimestamp = Timestamp.valueOf(entryTime);
            int id = db.findVehicleIDbyPLAT(platField.getText());
            db.entry_log(entryTimestamp, id);
            System.out.println(id);


            new SuccessEntry().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}
