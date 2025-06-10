package GUI_Forms;

import DataBase.KlienciRepository;
import Class.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

public class SearchUserTable extends JFrame{
    private JPanel JPanel1;
    private JButton menuBackBtn;
    private JButton nextBtn;
    private JTable userTable;
    private JTextField telefonInp;
    private JButton searchBtn;
    private JLabel messageLabel;

    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemRemoveAll;

    public SearchUserTable(){
        super("Wypożyczenie 1");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);

        /// Class Objects
        KlienciRepository klienciRepository = new KlienciRepository();

        ///  Table
        String[] columnNames = new String[] {"Imie", "Nazwisko", "Telefon", "Numer documentu", "Data urodzenia"};
        String[][] emptyData = new String[0][columnNames.length];
        tableModel = new DefaultTableModel(emptyData, columnNames);
        userTable.setModel(tableModel);

        /// Buttons
        menuBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });





        ///
        AtomicReference<Klient> klientRef = new AtomicReference<>(null);

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userTable.getSelectionModel().isSelectionEmpty()){
                    dispose();
                    telefonInp.setText("");

                    ///
                    Klient klient = klientRef.get();
                    RentItem rentItem = new RentItem(klient);
                    rentItem.setVisible(true);
                }
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numerTelefonu = telefonInp.getText();
                if (!numerTelefonu.trim().isEmpty()) {
                    try {
                        ///
                        Klient klient = klienciRepository.otrzymacKlienta(numerTelefonu);
                        klientRef.set(klient);

                        String[][] rowData = {{klient.imie, klient.nazwisko, klient.telefon, klient.numer_documentu}};
                        tableModel = new DefaultTableModel(rowData, columnNames);
                        userTable.setModel(tableModel);
                        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    } catch (Exception ex) {
                        messageLabel.setForeground(Color.RED);
                        messageLabel.setText("Nie ma klienta z takiem numerem telefonu");
                        telefonInp.setText("");
                        tableModel = new DefaultTableModel(emptyData, columnNames);
                        userTable.setModel(tableModel);
                    }
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wpisz numer telefonu");
                }
            }
        });




    }
}
