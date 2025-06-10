package GUI_Forms;

import Class.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RentItem extends JFrame{
    private JPanel JPanel1;
    private JButton backBtn;
    private JButton saveBtn;
    private JComboBox typeBox;
    private JComboBox levelBox;
    private JSpinner numbOfDay;
    private JLabel messageLabel;
    private JButton nextBtn;
    private JLabel nameLabel;
    private JLabel bootSizeLabel;
    private JLabel heightLabel;
    private JLabel surnameLabel;
    private JLabel wieghtLabel;
    private JLabel passpotLabel;
    private JLabel telefonIDLabel;

    public RentItem(Klient klient){
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


        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeBoxCheck = typeBox.getSelectedItem().toString();
                String levelBoxCheck = levelBox.getSelectedItem().toString();
                Object numbOfDayCheck = numbOfDay.getValue();
                if (!typeBoxCheck.equals("Wybierz") && !levelBoxCheck.equals("Wybierz") && (int)numbOfDayCheck > 0){
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Sprzęt dodany do listy wypożyczeń!");
                    typeBox.setSelectedIndex(0);
                    levelBox.setSelectedIndex(0);
                    numbOfDay.setValue(0);
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
    }
}
