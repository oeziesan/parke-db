package ui;
import utils.FontLoaderThin;
import db.dbhandler;
import utils.ImageButton;
import utils.TfNeumorphic;
import utils.NeumorphicGradientPanel;
import javax.swing.*;
import java.awt.*;

public class RootUpdateVehicle extends JFrame {
    private JTextField tfBefore, tfNIM, tfNama;
    private byte[] embeddedFace;
    private JLabel lbValidate;
    private JComboBox<String> cbtkendaraan;

    public RootUpdateVehicle() {
        setTitle("Update Vehicle");
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
        tfBefore.setText("Nomor Polisi");
        tfBefore.setBounds(250, 60, 200, 25);

        //Tombol Check
        ImageButton btnCheck = new ImageButton(
                "/assets/btnCheckReg.png",
                "/assets/btnCheckHover.png",
                "/assets/btnCheckClicked.png"
        );
        btnCheck.setBounds(500,60, 120, 45);

        //TIPE-KENDARAAN
        JLabel lbtkendaraan = new JLabel("Tipe Kendaraan : ");
        FontLoaderThin.applyToLabel(lbtkendaraan, 14f, Font.PLAIN);
        lbtkendaraan.setBounds(100, 150, 150, 25);
        String[] tkendaraanOptions = {"Roda 2", "Roda 4", "Lainnya"};
        cbtkendaraan = new JComboBox<>(tkendaraanOptions);
        cbtkendaraan.setBounds(250, 150, 300, 25);

        //JENIS-KENDARAAN
        JLabel lbjkendaraan = new JLabel("Jenis Kendaraan : ");
        FontLoaderThin.applyToLabel(lbjkendaraan, 14f, Font.PLAIN);
        lbjkendaraan.setBounds(100, 200, 150, 25);
        TfNeumorphic tfJkendaraan = new TfNeumorphic(20);
        tfJkendaraan.setBounds(250, 200, 200, 25);

        //WARNA-KENDARAAN
        JLabel lbwarna = new JLabel("Warna : ");
        FontLoaderThin.applyToLabel(lbwarna, 14f, Font.PLAIN);
        lbwarna.setBounds(100, 250, 150, 25);
        TfNeumorphic tfwarna = new TfNeumorphic(20);
        tfwarna.setBounds(250, 250, 200, 25);

        //PLAT-NOMOR
        JLabel lbnopol = new JLabel("NOPOL : ");
        FontLoaderThin.applyToLabel(lbnopol, 14f, Font.PLAIN);
        lbnopol.setBounds(100, 300, 150, 25);
        TfNeumorphic tfnopol = new TfNeumorphic(20);
        tfnopol.setBounds(250, 300, 200, 25);

        ImageButton btnUpdate = new ImageButton(
                "/assets/btnUpdateReg.png",
                "/assets/btnUpdateHover.png",
                "/assets/btnUpdateClicked.png"
        );
        btnUpdate.setBounds(280, 350, 120, 40);

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
            String tValidate = db.showVehicle(tfBefore.getText(), tfBefore.getText());
            if (lbValidate != null) {
                backgroundPanel.remove(lbValidate);
            }
            lbValidate = new JLabel(tValidate);
            lbValidate.setBounds(250, 100, 200, 25);

            if (tValidate.equals("Data Tidak Ditemukan")) {
                backgroundPanel.remove(lbtkendaraan);
                backgroundPanel.remove(cbtkendaraan);
                backgroundPanel.remove(lbjkendaraan);
                backgroundPanel.remove(tfJkendaraan);
                backgroundPanel.remove(lbwarna);
                backgroundPanel.remove(tfwarna);
                backgroundPanel.remove(lbnopol);
                backgroundPanel.remove(tfnopol);
                backgroundPanel.remove(btnUpdate);
                revalidate();
                repaint();
            } else {
                backgroundPanel.add(lbtkendaraan);
                backgroundPanel.add(cbtkendaraan);
                backgroundPanel.add(lbjkendaraan);
                backgroundPanel.add(tfJkendaraan);
                backgroundPanel.add(lbwarna);
                backgroundPanel.add(tfwarna);
                backgroundPanel.add(lbnopol);
                backgroundPanel.add(tfnopol);
                backgroundPanel.add(btnUpdate);
            }
            backgroundPanel.add(lbValidate);
            revalidate();
            repaint();
        });

        btnUpdate.addActionListener(e -> {
            if (tfJkendaraan == null || tfJkendaraan.getText().trim().isEmpty() ||
                    tfwarna.getText() == null || tfwarna.getText().trim().isEmpty() ||
                    tfnopol.getText() == null || tfnopol.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi Terlebih semua data!");
                return;
            }
            String nopolCondition = tfBefore.getText();
            dbhandler db = new dbhandler();
            db.updateVehicle(cbtkendaraan.getSelectedItem().toString(), tfJkendaraan.getText(), tfwarna.getText(),
                    tfnopol.getText(), nopolCondition);
            JOptionPane.showMessageDialog(this, "Data Berhasil di-Update!");
        });

        btnBack.addActionListener(e -> {
            new root().setVisible(true);
            dispose();
        });
        setContentPane(backgroundPanel);
    }
}