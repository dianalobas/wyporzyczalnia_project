package GUI_Forms;

import Class.Klient;
import DataBase.KlienciRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public NewClient(){
        super("Nowy klient");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
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

                    KlienciRepository klienciRepository = new KlienciRepository();
                    try {
                        klienciRepository.dodac(klient);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,"123"); //////

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
                    sexInp.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null,"Uzupełnij wszystkie pola z gwiazdkami!","Błąd",
                            JOptionPane.WARNING_MESSAGE);

                }

            }
        });
    }
}
