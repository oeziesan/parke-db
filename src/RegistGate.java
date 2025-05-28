import javax.swing.*;
import java.awt.*;

public class RegistGate extends JFrame {
    private JComboBox<String> cbtkendaraan;
    private JTextField tfNama, tfNIM, tfJenisKendaraan, tfWarna, tfPlat;

    public RegistGate() {
        setTitle("Pendaftaran Kendaraan");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Judul
        JLabel label = new JLabel("Silakan Masukkan Data", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        label.setBounds(180, 20, 350, 40);
        add(label);

        // Label dan Input Field
        JLabel lbnama = new JLabel("NAMA:");
        lbnama.setBounds(100, 100, 150, 25);
        tfNama = new JTextField();
        tfNama.setBounds(250, 100, 300, 25);

        JLabel lbnim = new JLabel("NIM:");
        lbnim.setBounds(100, 140, 150, 25);
        tfNIM = new JTextField();
        tfNIM.setBounds(250, 140, 300, 25);

        JLabel lbtkendaraan = new JLabel("TIPE KENDARAAN:");
        lbtkendaraan.setBounds(100, 180, 150, 25);
        String[] tkendaraanOptions = {"Roda 2", "Roda 4", "Lainnya"};
        cbtkendaraan = new JComboBox<>(tkendaraanOptions);
        cbtkendaraan.setBounds(250, 180, 300, 25);

        JLabel lbjkendaraan = new JLabel("JENIS KENDARAAN:");
        lbjkendaraan.setBounds(100, 220, 150, 25);
        tfJenisKendaraan = new JTextField();
        tfJenisKendaraan.setBounds(250, 220, 300, 25);

        JLabel lbwarna = new JLabel("WARNA:");
        lbwarna.setBounds(100, 260, 150, 25);
        tfWarna = new JTextField();
        tfWarna.setBounds(250, 260, 300, 25);

        JLabel lbplat = new JLabel("NOMOR POLISI:");
        lbplat.setBounds(100, 300, 150, 25);
        tfPlat = new JTextField();
        tfPlat.setBounds(250, 300, 300, 25);

        // Tombol Daftar
        JButton btnRegist = new JButton("DAFTAR");
        btnRegist.setBounds(250, 350, 120, 40);

        // Tambah semua ke frame
        add(lbnama); add(tfNama);
        add(lbnim); add(tfNIM);
        add(lbtkendaraan); add(cbtkendaraan);
        add(lbjkendaraan); add(tfJenisKendaraan);
        add(lbwarna); add(tfWarna);
        add(lbplat); add(tfPlat);
        add(btnRegist);

        btnRegist.addActionListener(e -> {
            new Registered().setVisible(true); // Buka jendela baru
            dispose(); // Tutup MainWindow ini
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistGate().setVisible(true);
        });
    }
}
