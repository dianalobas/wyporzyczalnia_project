package GUI_Forms;
import Class.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentItemsList extends JFrame{
    private JPanel JPanel1;
    private JButton backBtn;
    private JButton backItemBtn;
    private JTable userTable;
    private JLabel telefonIDLabel;
    private JLabel totalPriceLabel;

    RentItemsList(Klient klient){ /// ArrayList<WypoSprzet> wszystkieSprzety
        super("Sprzety do wydożyczania");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);


        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        String[] columnNames = {"Rodzaj", "Poziom", "Ilość dni", "Cena za dzień"};
        String[][] rowData = {{klient.imie, klient.nazwisko, klient.telefon, klient.numer_documentu}}; /// dane sprzetu


        /// zakaz na edytowanie pół
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.setModel(tableModel);


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ///
            }
        });
    }
    /* RentItemsList(Klient klient, ArrayList<WypoSprzet> wszystkieSprzety){
        super("Sprzety do wydożyczania");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);

        float totalPrice = 0;

        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        String[] columnNames = {"Rodzaj", "Poziom", "Ilość dni", "Cena za dzień"};

        Object[][] data = new Object[wszystkieSprzety.size()][4];
        for (int i = 0; i < wszystkieSprzety.size(); i++) {
            WypoSprzet sprzet = wszystkieSprzety.get(i);
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


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ///RentItem rentItem = new RentItem(klient, wszystkieSprzety);
                ///rentItem.setVisible(true);
            }
        });
    }*/
}
