package ui;

import utils.NeumorphicGradientPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static db.dbhandler.loadLOG;

public class Log extends JFrame {
    public Log() {
        setTitle("Tabel Log");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        NeumorphicGradientPanel backgroundPanel = new NeumorphicGradientPanel();
        backgroundPanel.setLayout(null);

        String[] columns = {"VEHICLE_ID","JAM MASUK", "JAM KELUAR"};
        DefaultTableModel Model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(Model);
        JScrollPane scrollPane = new JScrollPane(table);
        backgroundPanel.add(scrollPane);
        scrollPane.setBounds(0, 0, 700, 500);
        loadLOG(Model);
        setContentPane(backgroundPanel);
    }
}
