package ui;

import Scanner.FaceCaptureListener;
import Scanner.FaceScanner;
import db.dbhandler;
import utils.FontLoaderBold;
import utils.FontLoaderThin;
import utils.ImageButton;
import utils.TfNeumorphic;
import utils.NeumorphicGradientPanel;

import javax.swing.*;
import java.awt.*;

public class RegistGate extends JFrame {
    private JComboBox<String> cbtkendaraan;
    private JTextField tfNama, tfNIM, tfJenisKendaraan, tfWarna, tfPlat;
    private byte[] embeddedFace;

    public RegistGate() {
        setTitle("Pendaftaran Kendaraan");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        int lbX = 20;

        JLabel label = new JLabel("Silakan Masukkan Data", SwingConstants.CENTER);
        FontLoaderBold.applyToLabel(label, 24f, Font.PLAIN);
        label.setBounds(160, 20, 400, 40);
        backgroundPanel.add(label);

        int fieldX = 150;
        int fieldWidth = 300;

        JLabel lbnama = new JLabel("NAMA :");
        FontLoaderThin.applyToLabel(lbnama, 14f, Font.PLAIN);
        lbnama.setBounds(lbX, 100, 150, 25);
        TfNeumorphic tfNama = new TfNeumorphic(50);
        tfNama.setBounds(fieldX, 100, fieldWidth, 25);

        JLabel lbnim = new JLabel("NIM :");
        FontLoaderThin.applyToLabel(lbnim, 14f, Font.PLAIN);
        lbnim.setBounds(lbX, 140, 150, 25);
        TfNeumorphic tfNIM = new TfNeumorphic(50);
        tfNIM.setBounds(fieldX, 140, fieldWidth, 25);

        JLabel lbtkendaraan = new JLabel("TIPE KENDARAAN :");
        FontLoaderThin.applyToLabel(lbtkendaraan, 14f, Font.PLAIN);
        lbtkendaraan.setBounds(lbX, 180, 150, 25);

        String[] tkendaraanOptions = {"Roda 2", "Roda 4", "Lainnya"};
        cbtkendaraan = new JComboBox<>(tkendaraanOptions);
        cbtkendaraan.setBounds(fieldX, 180, fieldWidth, 25);

        JLabel lbjkendaraan = new JLabel("JENIS KENDARAAN :");
        FontLoaderThin.applyToLabel(lbjkendaraan, 14f, Font.PLAIN);
        lbjkendaraan.setBounds(lbX, 220, 150, 25);
        TfNeumorphic tfJenisKendaraan = new TfNeumorphic(50);
        tfJenisKendaraan.setBounds(fieldX, 220, fieldWidth, 25);

        JLabel lbwarna = new JLabel("WARNA :");
        FontLoaderThin.applyToLabel(lbwarna, 14f, Font.PLAIN);
        lbwarna.setBounds(lbX, 260, 150, 25);
        TfNeumorphic tfWarna = new TfNeumorphic(50);
        tfWarna.setBounds(fieldX, 260, fieldWidth, 25);

        JLabel lbplat = new JLabel("NOMOR POLISI :");
        FontLoaderThin.applyToLabel(lbplat, 14f, Font.PLAIN);
        lbplat.setBounds(lbX, 300, 150, 25);
        TfNeumorphic tfPlat = new TfNeumorphic(50);
        tfPlat.setBounds(fieldX, 300, fieldWidth, 25);

        ImageButton btnBack = new ImageButton(
                "/assets/btnBackReg.png",
                "/assets/btnBackHover.png",
                "/assets/btnBackClicked.png"
        );
        btnBack.setBounds(-10, 20, 125, 60);
        backgroundPanel.add(btnBack);
        btnBack.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        ImageButton btnDaftar = new ImageButton(
                "/assets/btnRegReg.png",
                "/assets/btnRegHover.png",
                "/assets/btnRegClicked.png"
        );
        btnDaftar.setBounds(fieldX + 90, 370, 125, 45);


        btnDaftar.addActionListener(e -> {
            String nama = tfNama.getText();
            String nim = tfNIM.getText();
            String tipe = cbtkendaraan.getSelectedItem().toString();
            String jenis = tfJenisKendaraan.getText();
            String warna = tfWarna.getText();
            String plat = tfPlat.getText();

            if (nama == null || nama.trim().isEmpty() ||
                    nim == null || nim.trim().isEmpty() ||
                    tipe == null || tipe.trim().isEmpty() ||
                    jenis == null || jenis.trim().isEmpty() ||
                    warna == null || warna.trim().isEmpty() ||
                    plat == null || plat.trim().isEmpty() ||
                    embeddedFace == null) {

                JOptionPane.showMessageDialog(this, "Isi terlebih dahulu semua data.");
                return;
            }

            dbhandler db = new dbhandler();
            // Masukkan ke tabel identity dan dapatkan id-nya
            int identityId = db.insertIdentity(nama, nim, embeddedFace);

            if (identityId != -1) {
                db.insertVehicle(identityId, tipe, jenis, warna, plat);
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                new Registered().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data ke identity.");
            }
        });

        ImageButton btnScan = new ImageButton(
                "/assets/btnScanReg.png",
                "/assets/btnScanHover.png",
                "/assets/btnScanClicked.png"
        );
        btnScan.setBounds(470, 100, 210, 210);

        btnScan.addActionListener(e -> {
            FaceScanner scanner = new FaceScanner(new FaceCaptureListener() {
                @Override
                public void onFaceCaptured(byte[] faceBytes) {
                    RegistGate.this.embeddedFace = faceBytes;
                    JOptionPane.showMessageDialog(RegistGate.this, "Wajah berhasil di-capture!");
                }
            });
            scanner.setVisible(true);
        });

        backgroundPanel.add(lbnama); backgroundPanel.add(tfNama);
        backgroundPanel.add(lbnim); backgroundPanel.add(tfNIM);
        backgroundPanel.add(lbtkendaraan); backgroundPanel.add(cbtkendaraan);
        backgroundPanel.add(lbjkendaraan); backgroundPanel.add(tfJenisKendaraan);
        backgroundPanel.add(lbwarna); backgroundPanel.add(tfWarna);
        backgroundPanel.add(lbplat); backgroundPanel.add(tfPlat);
        backgroundPanel.add(btnDaftar);
        backgroundPanel.add(btnScan);
        setContentPane(backgroundPanel);
    }
}