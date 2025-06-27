package GUI_Forms;
import Class.*;
import DataBase.SprzetRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RentItemsList extends JFrame{
    private JPanel JPanel1;
    private JButton backBtn;
    private JButton backItemBtn;
    private JTable userTable;
    private JLabel telefonIDLabel;
    private JLabel messageLabel;
    private JLabel priceLabel;
    private SprzetRepository sprzetRepository = new SprzetRepository();
    private ArrayList<WypoSprzet> listaWyporzeczenia = new ArrayList<>();




    RentItemsList(Klient klient){ /// ArrayList<WypoSprzet> wszystkieSprzety
        super("Wypożyczone sprzęty");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 700, height = 400;
        this.setSize(width,height);

        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        String[] columnNames = {"Nazwa", "Firma", "Data wypożyczenia", "Cena za dzień", "Ilość dni", "Ogólna cena"};



        /// zakaz na edytowanie pół
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.setModel(tableModel);

        /// wypisanie wypoyczonego sprzetu
        try {
            ArrayList<WypoSprzet> sprzetKlienta = sprzetRepository.otrzymacWyporzyczonySprzetKlienta(klient.id);
            /*listaWyporzeczenia.clear();
            tableModel.setRowCount(0);*/
            for (WypoSprzet item: sprzetKlienta){
                listaWyporzeczenia.add(item);
                long ilosc_dni = getDays(item.data_wypozyczenia);
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String formattedDataWypo = formatter.format(item.data_wypozyczenia);

                float totalSprzetPrice = item.sprzet.cena_dzienna * ilosc_dni;
                String[] rowData = {item.sprzet.nazwa, item.sprzet.firma, formattedDataWypo, String.valueOf(item.sprzet.cena_dzienna), String.valueOf(ilosc_dni), String.valueOf(totalSprzetPrice)};
                tableModel.addRow(rowData);
            }
            updateTotalPrice();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SearchUserTable searchUserTable = new SearchUserTable();
                searchUserTable.setVisible(true);
            }
        });
        backItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTable.getSelectionModel().isSelectionEmpty()) {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wybierz pole!");
                    return;
                }
                int rowIdex = userTable.getSelectedRow();
                try {
                    sprzetRepository.wynajemZwrotSprzetu(klient, listaWyporzeczenia.get(rowIdex).sprzet, true);
                    listaWyporzeczenia.remove(rowIdex);
                    DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                    model.removeRow(rowIdex);
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Pole zostało uzunięte!");
                    updateTotalPrice();
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(
                            null,
                            ("Sprzęt nie został zwrócony!\n"+ex.getMessage()),
                            "Uwaga",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        });
    }
    private long getDays(Date data)
    {
        long diffInMillies = new Date().getTime() - data.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
    }

    private void updateTotalPrice(){
        float totalPrice = 0;
        for (WypoSprzet sprzet: listaWyporzeczenia){
            long ilosc_dni = getDays(sprzet.data_wypozyczenia);
            float totalSprzetPrice = sprzet.sprzet.cena_dzienna * ilosc_dni;
            totalPrice += totalSprzetPrice;
            priceLabel.setText("Total price: " + totalPrice + "zł");
        }
    }
}
