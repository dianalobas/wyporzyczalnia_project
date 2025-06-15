package GUI_Forms;
import Class.*;
import DataBase.SprzetRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentItemsList extends JFrame{
    private JPanel JPanel1;
    private JButton backBtn;
    private JButton backItemBtn;
    private JTable userTable;
    private JLabel telefonIDLabel;
    private JLabel messageLabel;
    private JLabel totalPriceLabel;
    private SprzetRepository sprzetRepository = new SprzetRepository();
    private ArrayList<Sprzet> listaWyporzeczenia = new ArrayList<>();


    RentItemsList(Klient klient){ /// ArrayList<WypoSprzet> wszystkieSprzety
        super("Sprzety do wydożyczania");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 700, height = 400;
        this.setSize(width,height);


        telefonIDLabel.setText("Dane urzytkownika: #" + klient.telefon);

        String[] columnNames = {"Nazwa", "Firma", "Model", "Cena za dzień"};



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
            ArrayList<Sprzet> sprzetKlienta = sprzetRepository.otrzymacWyporzyczonySprzetKlienta(klient.id);
            /*listaWyporzeczenia.clear();
            tableModel.setRowCount(0);*/
            for (Sprzet item: sprzetKlienta){
                listaWyporzeczenia.add(item);
                String[] rowData = {item.nazwa, item.firma, item.model, String.valueOf(item.cena_dzienna)};
                tableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu();
                menu.setVisible(true);
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
                    sprzetRepository.wynajemZwrotSprzetu(klient, listaWyporzeczenia.get(rowIdex), true);
                    listaWyporzeczenia.remove(rowIdex);
                    DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                    model.removeRow(rowIdex);
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Pole zostało uzunięte!");
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

}
