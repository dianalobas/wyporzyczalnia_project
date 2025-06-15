package GUI_Forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import DataBase.*;
import Class.*;

public class WszystkieWypozyczenia extends JFrame{
    private JPanel JPanel1;
    private JButton menuBackBtn;
    private JTable userTable;
    private JCheckBox nartyCheckBox;
    private JCheckBox snowboardCheckBox;
    private JCheckBox kaskCheckBox;
    private JCheckBox kijkiCheckBox;
    private JCheckBox butyCheckBox;
    private JRadioButton rentOutBtn;
    private JRadioButton forRentBtn;
    private SprzetRepository sprzetRepository = new SprzetRepository();
    private ArrayList<Sprzet> sprzetList = new ArrayList<>();


    public WszystkieWypozyczenia() {
        super("Wszystkie Wypożyczenia");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);
        String[] columnNames = {"Nazwa", "Firma", "Model", "Cena za dzień"};
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
        userTable.setModel(tableModel);
        tableUpdate();

        try {
            sprzetList = sprzetRepository.getAvailableAllSprzety();

        } catch (Exception ex){

        }

        rentOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });

        forRentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });
        menuBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
        nartyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });
        snowboardCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });
        kaskCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });
        kijkiCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });
        butyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdate();
            }
        });
    }
    private void tableUpdate() {
        DefaultTableModel tableModel = (DefaultTableModel)userTable.getModel();
        tableModel.setRowCount(0);
        for (Sprzet sprzet: sprzetList) {
            boolean isDisplay = forRentBtn.isSelected() && sprzet.isAvaibleForRent ||
                                rentOutBtn.isSelected() && !sprzet.isAvaibleForRent;
            if (isDisplay &&  isDisplaySprzet(sprzet)){
                String[] rowData = {sprzet.nazwa, sprzet.firma, sprzet.model, String.valueOf(sprzet.cena_dzienna)};
                tableModel.addRow(rowData);
            }
        }
    }
    private boolean isDisplaySprzet(Sprzet sprzet){
        return sprzet instanceof Narty && nartyCheckBox.isSelected() ||
               sprzet instanceof Snowboard && snowboardCheckBox.isSelected() ||
               sprzet instanceof Kask && kaskCheckBox.isSelected() ||
                sprzet instanceof Kijki && kijkiCheckBox.isSelected() ||
                sprzet instanceof Buty && butyCheckBox.isSelected();
    }
}
