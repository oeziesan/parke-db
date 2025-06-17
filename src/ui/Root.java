package ui;
import utils.ImageButton;
import javax.swing.*;
import java.awt.*;
import utils.NeumorphicGradientPanel;

public class Root extends JFrame {
    public Root() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        //OPSI AWAL
        JLabel label = new JLabel("PILIH OPSI :");
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        ImageButton btnUpdate = new ImageButton(
                "/assets/btnUpdateReg.png",
                "/assets/btnUpdateHover.png",
                "/assets/btnUpdateClicked.png"
        );
        ImageButton btnDelete = new ImageButton(
                "/assets/btnDelReg.png",
                "/assets/btnDelHover.png",
                "/assets/btnDelClicked.png"
        );

        ImageButton btnBack = new ImageButton(
                "/assets/btnBackReg.png",
                "/assets/btnBackHover.png",
                "/assets/btnBackClicked.png"
        );

        ImageButton btnLog = new ImageButton(
                "/assets/btnLogReg.png",
                "/assets/btnLogHover.png",
                "/assets/btnLogClicked.png"
        );

        label.setBounds(290, 50, 150, 20);
        btnUpdate.setBounds(210, 180, 136, 56);
        btnDelete.setBounds(360, 180, 136, 56);
        btnLog.setBounds(285, 120, 136, 56);
        btnBack.setBounds(-10, 20, 125, 60);

        backgroundPanel.add(label);
        backgroundPanel.add(btnUpdate);
        backgroundPanel.add(btnDelete);
        backgroundPanel.add(btnLog);
        backgroundPanel.add(btnBack);

        //IF-UPDATE-(DATA)
        JLabel question = new JLabel("Akan Update Apa?", SwingConstants.CENTER);
        question.setFont(new Font("Poppins", Font.PLAIN, 14));
        JButton btnID = new ImageButton(
                "/assets/btnIDReg.png",
                "/assets/btnIDHover.png",
                "/assets/btnIDClicked.png"
        );
        ImageButton btnUpdateVehicle = new ImageButton(
                "/assets/btnVReg.png",
                "/assets/btnVHover.png",
                "/assets/btnVClicked.png"
        );
        question.setBounds(260, 250, 150, 40);
        btnID.setBounds(210, 320, 125, 45);
        btnUpdateVehicle.setBounds(360, 320, 125, 45);

        //IF-HAPUS-(DATA)
        JLabel question2 = new JLabel("Akan Hapus Apa?", SwingConstants.CENTER);
        question2.setFont(new Font("Poppins", Font.PLAIN, 14));
        ImageButton btnID2 = new ImageButton(
                "/assets/btnIDReg.png",
                "/assets/btnIDHover.png",
                "/assets/btnIDClicked.png"
        );
        ImageButton btnUpdateVehicle2 = new ImageButton(
                "/assets/btnVReg.png",
                "/assets/btnVHover.png",
                "/assets/btnVClicked.png"
        );

        question2.setBounds(260, 250, 150, 40);
        btnID2.setBounds(210, 320, 125, 45);
        btnUpdateVehicle2.setBounds(360, 320, 125, 45);

        //IF-UPDATE-(ACTION)
        btnUpdate.addActionListener(e -> {
            backgroundPanel.remove(question2);
            backgroundPanel.remove(btnID2);
            backgroundPanel.remove(btnUpdateVehicle2);
            backgroundPanel.add(question);
            backgroundPanel.add(btnID);
            backgroundPanel.add(btnUpdateVehicle);
            revalidate();
            repaint();

            btnID.addActionListener(a -> {
                new RootUpdateID().setVisible(true);
                dispose();
            });

            btnUpdateVehicle.addActionListener(b -> {
                new RootUpdateVehicle().setVisible(true);
                dispose();
            });
        });

       btnDelete.addActionListener(e -> {
           backgroundPanel.remove(question);
           backgroundPanel.remove(btnID);
           backgroundPanel.remove(btnUpdateVehicle);
           backgroundPanel.add(question2);
           backgroundPanel.add(btnID2);
           backgroundPanel.add(btnUpdateVehicle2);
           revalidate();
           repaint();

           btnID2.addActionListener(a -> {
               new RootDeleteID().setVisible(true);
               dispose();
           });

           btnUpdateVehicle2.addActionListener(b -> {
               new RootDeleteID().setVisible(true);
               dispose();
           });
       });

       btnLog.addActionListener(e -> {
           new Log().setVisible(true);
       });

       btnBack.addActionListener(e -> {
           new MainWindow().setVisible(true);
           dispose();
       });
       setContentPane(backgroundPanel);
    }
}
