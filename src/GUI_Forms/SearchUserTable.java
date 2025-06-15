package GUI_Forms;

import DataBase.KlienciRepository;
import Class.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

public class SearchUserTable extends JFrame{
    private JPanel JPanel1;
    private JButton menuBackBtn;
    private JButton nextBtn;
    private JTable userTable;
    private JTextField telefonInp;
    private JButton searchBtn;
    private JLabel messageLabel;
    private JButton backItemBtn;

    private DefaultTableModel tableModel;

    public SearchUserTable(){
        super("Wypożyczenie 1");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 700, height = 400;
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
                    RentItem rentItem = null; //////!
                    try {
                        rentItem = new RentItem(klient);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
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
                        String formattedDataUrodzenia = "";
                        if (klient.data_urodzenia != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                            formattedDataUrodzenia = formatter.format(klient.data_urodzenia);
                        }

                        String[][] rowData = {{klient.imie, klient.nazwisko, klient.telefon, klient.numer_documentu, formattedDataUrodzenia}};
                        tableModel = new DefaultTableModel(rowData, columnNames);
                        userTable.setModel(tableModel);
                        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                        /// zakaz na edytowanie pół
                        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        userTable.setModel(tableModel);

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


        backItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userTable.getSelectionModel().isSelectionEmpty()){
                    dispose();
                    telefonInp.setText("");

                    ///
                    Klient klient = klientRef.get();
                    RentItemsList rentItemsList = new RentItemsList(klient);
                    rentItemsList.setVisible(true);
                }
            }
        });
    }
}
