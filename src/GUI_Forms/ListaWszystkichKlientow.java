package GUI_Forms;

import Class.*;
import DataBase.KlienciRepository;
import DataBase.SprzetRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

public class ListaWszystkichKlientow extends JFrame{
    private JPanel JPanel1;
    private JTextField telefonInp;
    private JButton searchBtn;
    private JTable userTable;
    private JButton menuBackBtn;
    private JButton editBtn;
    private JLabel messageLabel;
    private JButton addBtn;
    private JButton delBtn;

    private DefaultTableModel tableModel;
    private KlienciRepository klienciRepository = new KlienciRepository();
    private Klient selectedKlient;

    public ListaWszystkichKlientow(){
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


        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numerTelefonu = telefonInp.getText();
                if (!numerTelefonu.trim().isEmpty()) {
                    try {
                        ///
                        selectedKlient = klienciRepository.otrzymacKlienta(numerTelefonu);
                        String formattedDataUrodzenia = "";
                        if (selectedKlient.data_urodzenia != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                            formattedDataUrodzenia = formatter.format(selectedKlient.data_urodzenia);
                        }

                        String[][] rowData = {{selectedKlient.imie, selectedKlient.nazwisko, selectedKlient.telefon, selectedKlient.numer_documentu, formattedDataUrodzenia}};
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
                        selectedKlient = null;
                    }
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wpisz numer telefonu");
                    selectedKlient = null;
                }
            }
        });


        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                NewClient newClient = new NewClient(null);
                newClient.setVisible(true);
            }
        });
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numerTelefonu = telefonInp.getText();
                if (!numerTelefonu.trim().isEmpty()){
                    if (!userTable.getSelectionModel().isSelectionEmpty()){
                        try {
                            klienciRepository.usunacKlienta(numerTelefonu);
                            tableModel = new DefaultTableModel(emptyData, columnNames);
                            userTable.setModel(tableModel);
                            messageLabel.setForeground(Color.BLACK);
                            messageLabel.setText("Klient o numerze telefonu: " + numerTelefonu + " został usunięty");
                            telefonInp.setText("");
                        } catch (Exception ex) {
                            messageLabel.setText("Błąd: " + ex.getMessage());
                        }
                    }
                }
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numerTelefonu = telefonInp.getText();
                if (!numerTelefonu.trim().isEmpty() && selectedKlient != null){
                    if (!userTable.getSelectionModel().isSelectionEmpty()){
                        dispose();
                        telefonInp.setText("");

                        NewClient newClient = new NewClient(selectedKlient);
                        newClient.setVisible(true);
                    }
                }
            }
        });

    }

}
