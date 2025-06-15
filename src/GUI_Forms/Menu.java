package GUI_Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel JPanel1;
    private JButton rentItemBtn;
    private JButton itemsViewBtn;
    private JButton outProgramBtn;
    private JButton CRUDklienci;

    public Menu(){
        super("Menu");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 700, height = 400;
        this.setSize(width,height);

        rentItemBtn.setPreferredSize(new Dimension(200, 20));
        itemsViewBtn.setPreferredSize(new Dimension(200, 20));
        outProgramBtn.setPreferredSize(new Dimension(200, 20));
        CRUDklienci.setPreferredSize(new Dimension(200, 20));

        outProgramBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        rentItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SearchUserTable searchUserTable = new SearchUserTable();
                searchUserTable.setVisible(true);
            }
        });
        CRUDklienci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ListaWszystkichKlientow listaWszystkichKlientow = new ListaWszystkichKlientow();
                listaWszystkichKlientow.setVisible(true);
            }
        });
        itemsViewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WszystkieWypozyczenia wszystkieWypozyczenia = new WszystkieWypozyczenia();
                wszystkieWypozyczenia.setVisible(true);
            }
        });
    }
}
