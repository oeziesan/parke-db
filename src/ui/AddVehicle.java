package ui;

import javax.swing.*;
import java.awt.*;
import db.dbhandler;
import utils.FontLoaderThin;
import utils.ImageButton;
import utils.TfNeumorphic;
import utils.NeumorphicGradientPanel;

public class AddVehicle extends JFrame {
    private JLabel statusLabel;
    private dbhandler db = new dbhandler();

    public AddVehicle() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ganti background ke panel dengan efek gradien neumorfik
        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        TfNeumorphic nimField = new TfNeumorphic(20);
        nimField.setBounds(250, 50, 200, 25);
        backgroundPanel.add(nimField);

        TfNeumorphic tipeField = new TfNeumorphic(20);
        tipeField.setBounds(250, 100, 200, 25);
        backgroundPanel.add(tipeField);

        TfNeumorphic jenisField = new TfNeumorphic(20);
        jenisField.setBounds(250, 150, 200, 25);
        backgroundPanel.add(jenisField);

        TfNeumorphic warnaField = new TfNeumorphic(20);
        warnaField.setBounds(250, 200, 200, 25);
        backgroundPanel.add(warnaField);

        TfNeumorphic platField = new TfNeumorphic(20);
        platField.setBounds(250, 250, 200, 25);
        backgroundPanel.add(platField);

        ImageButton submitButton = new ImageButton(
                "/assets/btnAddReg.png",
                "/assets/btnAddHover.png",
                "/assets/btnAddClicked.png"
        );
        submitButton.setBounds(450, 300, 200, 45);
        backgroundPanel.add(submitButton);

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

        statusLabel = new JLabel("");
        FontLoaderThin.applyToLabel(statusLabel, 14f, Font.PLAIN);
        statusLabel.setBounds(100, 300, 400, 25);
        backgroundPanel.add(statusLabel);

        JLabel lblNim = new JLabel("NIM");
        FontLoaderThin.applyToLabel(lblNim, 14f, Font.PLAIN);
        lblNim.setBounds(100, 50, 200, 25);
        backgroundPanel.add(lblNim);

        JLabel lbTipe = new JLabel("Tipe Kendaraan");
        FontLoaderThin.applyToLabel(lbTipe, 14f, Font.PLAIN);
        lbTipe.setBounds(100, 100, 200, 25);
        backgroundPanel.add(lbTipe);

        JLabel lblJenis = new JLabel("Jenis Kendaraan");
        FontLoaderThin.applyToLabel(lblJenis, 14f, Font.PLAIN);
        lblJenis.setBounds(100, 150, 200, 25);
        backgroundPanel.add(lblJenis);

        JLabel lblWarna = new JLabel("Warna Kendaraan");
        FontLoaderThin.applyToLabel(lblWarna, 14f, Font.PLAIN);
        lblWarna.setBounds(100, 200, 200, 25);
        backgroundPanel.add(lblWarna);

        JLabel lblPlat = new JLabel("Plat Kendaraan");
        FontLoaderThin.applyToLabel(lblPlat, 14f, Font.PLAIN);
        lblPlat.setBounds(100, 250, 200, 25);
        backgroundPanel.add(lblPlat);

        submitButton.addActionListener(e -> {
            String nim = nimField.getText();
            int identityId = db.getIdentityIdByNim(nim);

            if (identityId == -1) {
                statusLabel.setText("NIM tidak ditemukan.");
                return;
            }

            String tipe = tipeField.getText();
            String jenis = jenisField.getText();
            String warna = warnaField.getText();
            String plat = platField.getText();

            if (tipe.isEmpty() || jenis.isEmpty() || warna.isEmpty() || plat.isEmpty()) {
                statusLabel.setText("Semua field kendaraan wajib diisi.");
                return;
            }

            db.insertVehicle(identityId, tipe, jenis, warna, plat);
            statusLabel.setText("Kendaraan berhasil ditambahkan.");
        });

        // Set background panel sebagai konten utama
        setContentPane(backgroundPanel);
    }
}
