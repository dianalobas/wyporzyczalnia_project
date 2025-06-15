package GUI_Forms;

import Class.Klient;
import DataBase.KlienciRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewClient extends JFrame{
    private JPanel JPanel1;
    private JTextField nameInp;
    private JTextField surnameInp;
    private JFormattedTextField emailInp;
    private JTextField telefonInp;
    private JTextField passportInp;
    private JTextField heightInp;
    private JTextField weightInp;
    private JComboBox bootSizeInp;
    private JTextField adresInp;
    private JComboBox sexInp;
    private JButton menuBackBtn;
    private JButton saveBtn;
    private JTextField dataInp;

    public NewClient(){
        super("Nowy klient");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 700, height = 400;
        this.setSize(width,height);

        menuBackBtn.setPreferredSize(new Dimension(180, 20));
        saveBtn.setPreferredSize(new Dimension(180, 20));

        nameInp.setPreferredSize(new Dimension(100, 20));
        surnameInp.setPreferredSize(new Dimension(100, 20));

        heightInp.setPreferredSize(new Dimension(40, 20));
        weightInp.setPreferredSize(new Dimension(40, 20));
        bootSizeInp.setPreferredSize(new Dimension(40, 20));



        menuBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameInp.getText().isEmpty() && !surnameInp.getText().isEmpty() && !emailInp.getText().isEmpty() &&
                        !telefonInp.getText().isEmpty() && !passportInp.getText().isEmpty()){
                    Klient klient = new Klient();
                    klient.imie = nameInp.getText();
                    klient.nazwisko = surnameInp.getText();
                    klient.email = emailInp.getText();
                    klient.telefon = telefonInp.getText();
                    klient.numer_documentu = passportInp.getText();
                    String dateString = dataInp.getText();
                    if (dateString.length() > 0) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        try {
                            klient.data_urodzenia = formatter.parse(dateString);
                        } catch (ParseException ex) {
                            dataInp.setForeground(Color.RED);
                            return;
                        }
                    } else {
                        klient.data_urodzenia = null;//new Date(Long.MIN_VALUE);
                    }

                    KlienciRepository klienciRepository = new KlienciRepository();
                    try {
                        klienciRepository.dodac(klient);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage()); //////

                    }

                    nameInp.setText("");
                    surnameInp.setText("");
                    emailInp.setText("");
                    telefonInp.setText("");
                    passportInp.setText("");
                    heightInp.setText("");
                    weightInp.setText("");
                    bootSizeInp.setSelectedIndex(0);
                    adresInp.setText("");
                    dataInp.setText("");
                    sexInp.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null,"Uzupełnij wszystkie pola z gwiazdkami!","Błąd",
                            JOptionPane.WARNING_MESSAGE);

                }

            }
        });
        dataInp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                dataInp.setForeground(Color.BLACK);
            }
        });
    }
}
