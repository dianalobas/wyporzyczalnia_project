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
    private JComboBox dostepnySprzetBox;
    private JButton delButton;
    private SprzetRepository sprzetRepository;
    private ArrayList<Sprzet> listaWyporzeczenia;

    public RentItem(Klient klient) throws Exception {
        super("Wypożyczenie 2");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 700, height = 400;
        this.setSize(width,height);
        listaWyporzeczenia = new ArrayList<>();

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

        String[] columnNames = {"Rodzaj", "Model", "Firma", "Rozmiar", "Cena za dzień"};

        Object[][] data = new Object[listaWyporzeczenia.size()][5];

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
                Object obj = dostepnySprzetBox.getSelectedItem();
                if (!(obj instanceof Sprzet)) {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Uzupełnij wszystkie pola!");
                    return;
                }

                Sprzet sprzet = (Sprzet)obj;
                listaWyporzeczenia.add(sprzet);
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Sprzęt dodany do listy wypożyczeń!");
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                model.addRow(new Object[]{
                    sprzet.nazwa,
                    sprzet.firma,
                    sprzet.model,
                    sprzet.rozmiar,
                    sprzet.cena_dzienna
                });

                updateTotalCena();
                typeBox.setSelectedIndex(0);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTable.getRowCount() != 0){
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
                } else {
                    dispose();
                    SearchUserTable searchUserTable = new SearchUserTable();
                    searchUserTable.setVisible(true);
                }


            }
        });
        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = typeBox.getSelectedItem();
                if (!(obj instanceof SprzetType)) {
                    dostepnySprzetBox.removeAllItems();
                    return;
                }
                SprzetType sprzetType = (SprzetType)obj;
                try {
                    ArrayList<Sprzet> sprzetLista = sprzetRepository.getAvailableSprzetByTypeId(sprzetType.id);
                    dostepnySprzetBox.removeAllItems();
                    dostepnySprzetBox.addItem("Wybierz");
                    for (Sprzet item: sprzetLista){
                        int i = 0;
                        while (i < listaWyporzeczenia.size() && item.id != listaWyporzeczenia.get(i).id)
                            i++;
                        if(i >= listaWyporzeczenia.size()){
                            dostepnySprzetBox.addItem(item);
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTable.getSelectionModel().isSelectionEmpty()) {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wybierz pole!");
                    return;
                }
                int rowIdex = userTable.getSelectedRow();
                listaWyporzeczenia.remove(rowIdex);
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                model.removeRow(rowIdex);
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Pole zostało uzunięte!");
                updateTotalCena();
                typeBox.setSelectedIndex(0);
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                for (int i =  listaWyporzeczenia.size() - 1; i >= 0; i--) {
                    Sprzet elem = listaWyporzeczenia.get(i);
                    try {
                        sprzetRepository.wynajemZwrotSprzetu(klient, elem, false);
                        listaWyporzeczenia.remove(listaWyporzeczenia.size() - 1);
                        model.removeRow(model.getRowCount() - 1);
                    } catch (Exception ex) {
                        JOptionPane.showConfirmDialog(
                                null,
                                (elem.nazwa + " nie został zapisany do wypożyczenia!\n"+ex.getMessage()),
                                "Uwaga",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
                updateTotalCena();

            }
        });
    }
    private void updateTotalCena(){
        // sum update
        float currentTotal = 0;
        for (Sprzet s : listaWyporzeczenia) {
            currentTotal += s.cena_dzienna;
        }
        totalPriceLabel.setText("Cena: " + currentTotal + " zł");
    }

}
