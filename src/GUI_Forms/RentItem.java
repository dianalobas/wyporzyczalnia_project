package GUI_Forms;

import Class.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import DataBase.*;

public class RentItem extends JFrame{
    private JPanel JPanel1;
    private JButton backBtn;
    private JButton addBtn;
    private JComboBox typeBox;
    private JLabel messageLabel;
    private JButton saveBtn;
    private JLabel nameLabel;
    private JLabel bootSizeLabel;
    private JLabel heightLabel;
    private JLabel surnameLabel;
    private JLabel wieghtLabel;
    private JLabel passpotLabel;
    private JLabel telefonIDLabel;
    private JTable userTable;
    private JLabel totalPriceLabel;
    private JComboBox choosedItem;
    private SprzetRepository sprzetRepository;

    public RentItem(Klient klient) throws Exception {
        super("Wypożyczenie 2");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);

        /// Dane klienta
        nameLabel.setText("Imię: " + klient.imie);
        surnameLabel.setText("Nazwisko: " + klient.nazwisko);
        heightLabel.setText("Wzrost: " + klient.wzrost);
        wieghtLabel.setText("Waga: " + klient.waga);
        bootSizeLabel.setText("Rozmiar buta: " + klient.rozmiar_buta);
        passpotLabel.setText("Nr dokumentu: " + klient.numer_documentu);
        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        sprzetRepository = new SprzetRepository();
        ArrayList<SprzetType> allRodzaje = sprzetRepository.getAllRodzaje();
        typeBox.addItem("Wybierz");
        for (SprzetType item: allRodzaje)
            typeBox.addItem(item);

        //sprzetRepository.getAvailableSprzetByType(typeBox.getSelectedItem());

        ArrayList<WypoSprzet> listaWyporzeczenia = new ArrayList<>();

        String[] columnNames = {"Rodzaj", "Model", "Firma", "Rozmiar", "Cena za dzień"};

        Object[][] data = new Object[listaWyporzeczenia.size()][5];
        /*for (int i = 0; i < listaWyporzeczenia.size(); i++) {
            WypoSprzet sprzet = listaWyporzeczenia.get(i);
            data[i][0] = sprzet.rodzaj;
            data[i][1] = sprzet.poziom;
            data[i][2] = sprzet.ilosc_dni;
            data[i][3] = sprzet.cena;  /// potrzebna metoda getCena
            totalPrice += sprzet.cena;
        }
        totalPriceLabel.setText("Cena: " + totalPrice);*/

        /// zakaz na edytowanie pół
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.setModel(tableModel);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeBoxCheck = typeBox.getSelectedItem().toString();
                String levelBoxCheck = choosedItem.getSelectedItem().toString();
                if (!typeBoxCheck.equals("Wybierz") && !levelBoxCheck.equals("Wybierz")){
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Sprzęt dodany do listy wypożyczeń!");

                    WypoSprzet sprzet = new WypoSprzet();
                    sprzet.rodzaj = typeBoxCheck;
                    /*sprzet.firma =
                    sprzet.model =   dane z klasy a w klasie z bazy
                    sprzet.rozmiar =
                    sprzet.cena =*/
                    listaWyporzeczenia.add(sprzet);

                    DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                    model.addRow(new Object[]{
                            sprzet.rodzaj, /// sprzet.nazwa
                            sprzet.firma,
                            sprzet.model,
                            sprzet.rozmiar,
                            sprzet.cena
                    });

                    // sum update
                    float currentTotal = 0;
                    for (WypoSprzet s : listaWyporzeczenia) {
                        currentTotal += s.cena;
                    }
                    totalPriceLabel.setText("Cena: " + currentTotal + " zł");

                    typeBox.setSelectedIndex(0);
                    choosedItem.setSelectedIndex(0);
                }
                else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Uzupełnij wszystkie pola!");
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Wybrane sprzęty nie będą przypisane do urzytkownika.\nCzy na pewno chcesz wyjść?",
                        "Potwierdzenie",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.OK_OPTION) {
                    dispose();
                    SearchUserTable searchUserTable = new SearchUserTable();
                    searchUserTable.setVisible(true);
                }
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ////RentItemsList rentItemsList = new RentItemsList(klient, listaWyporzeczenia);
                RentItemsList rentItemsList = new RentItemsList(klient);
                rentItemsList.setVisible(true);
            }
        });
        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = typeBox.getSelectedItem();
                if (!(obj instanceof SprzetType)) {
                    choosedItem.removeAllItems();
                    return;
                }
                SprzetType sprzetType = (SprzetType)obj;
                try {
                    ArrayList<WypoSprzet> sprzetLista = sprzetRepository.getAvailableSprzetByTypeId(sprzetType.id);
                    choosedItem.removeAllItems();
                    choosedItem.addItem("Wybierz");
                    for (WypoSprzet item: sprzetLista)
                        choosedItem.addItem(item);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    /*public RentItem(Klient klient, ArrayList<WypoSprzet> listaWyporzeczenia){
        super("Wypożyczenie 2");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);

        /// Dane klienta
        nameLabel.setText("Imię: " + klient.imie);
        surnameLabel.setText("Nazwisko: " + klient.nazwisko);
        heightLabel.setText("Wzrost: " + klient.wzrost);
        wieghtLabel.setText("Waga: " + klient.waga);
        passpotLabel.setText("Nr dokumentu: " + klient.numer_documentu);
        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        float totalPrice = 0;

        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        String[] columnNames = {"Rodzaj", "Poziom", "Ilość dni", "Cena za dzień"};

        Object[][] data = new Object[listaWyporzeczenia.size()][4];
        for (int i = 0; i < listaWyporzeczenia.size(); i++) {
            WypoSprzet sprzet = listaWyporzeczenia.get(i);
            data[i][0] = sprzet.rodzaj;
            data[i][1] = sprzet.poziom;
            data[i][2] = sprzet.ilosc_dni;
            data[i][3] = sprzet.cena;  /// potrzebna metoda getCena
            totalPrice += sprzet.cena;
        }
        totalPriceLabel.setText("Cena: " + totalPrice);
        /// zakaz na edytowanie pół
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.setModel(tableModel);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeBoxCheck = typeBox.getSelectedItem().toString();
                String levelBoxCheck = choosedItem.getSelectedItem().toString();
                if (!typeBoxCheck.equals("Wybierz") && !levelBoxCheck.equals("Wybierz")){
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Sprzęt dodany do listy wypożyczeń!");
                    typeBox.setSelectedIndex(0);
                    choosedItem.setSelectedIndex(0);
                    WypoSprzet sprzet = new WypoSprzet();
                    sprzet.rodzaj = typeBoxCheck;
                    sprzet.poziom = levelBoxCheck;
                    listaWyporzeczenia.add(sprzet);
                }
                else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Uzupełnij wszystkie pola!");
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SearchUserTable searchUserTable = new SearchUserTable();
                searchUserTable.setVisible(true);
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RentItemsList rentItemsList = new RentItemsList(klient, listaWyporzeczenia);
                rentItemsList.setVisible(true);
            }
        });
    }*/
}
