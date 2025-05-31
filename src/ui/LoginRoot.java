package ui;

import db.dbhandler;
import utils.FontLoaderThin;
import utils.ImageButton;
import utils.NeumorphicGradientPanel;
import utils.TfNeumorphic;
import utils.TfNeumorphicPassword;

import javax.swing.*;
import java.awt.*;

public class LoginRoot extends JFrame {
    public LoginRoot() {
        setTitle("LOGIN ROOT, ADMIN ONLY!");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        JLabel usn = new JLabel("USERNAME: ");
        FontLoaderThin.applyToLabel(usn, 14f, Font.PLAIN);
        usn.setBounds(100, 150, 200, 25);
        backgroundPanel.add(usn);

        JLabel pw = new JLabel("PASSWORD: ");
        FontLoaderThin.applyToLabel(pw, 14f, Font.PLAIN);
        pw.setBounds(100, 200, 200, 25);
        backgroundPanel.add(pw);

        TfNeumorphic tfUsn = new TfNeumorphic(20);
        tfUsn.setBounds(250, 150, 300, 25);
        backgroundPanel.add(tfUsn);

        TfNeumorphicPassword tfPW = new TfNeumorphicPassword(20);
        tfPW.setBounds(250, 200, 300, 25);
        backgroundPanel.add(tfPW);

        ImageButton btnLogin = new ImageButton(
                "/assets/btnLoginReg.png",
                "/assets/btnLoginHover.png",
                "/assets/btnLoginClicked.png"
        );
        btnLogin.setBounds(300, 250, 125, 50);
        backgroundPanel.add(btnLogin);

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

        btnLogin.addActionListener(e -> {
            String username = tfUsn.getText();
            String password = new String(tfPW.getText());
            dbhandler db = new dbhandler();

            boolean isValid = db.AdminAccess(username, password);

            if (isValid) {
                JOptionPane.showMessageDialog(this, "Login berhasil sebagai root.");
                new root().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username/password salah atau tidak terdaftar.");
            }
        });

        setContentPane(backgroundPanel);
    }
}