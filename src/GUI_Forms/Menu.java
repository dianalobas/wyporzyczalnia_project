package GUI_Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel JPanel1;
    private JButton createBtn;
    private JButton rentItemBtn;
    private JButton storeManageBtn;
    private JButton outProgramBtn;

    public Menu(){
        super("Menu");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 300;
        this.setSize(width,height);

        createBtn.setPreferredSize(new Dimension(180, 20));
        rentItemBtn.setPreferredSize(new Dimension(180, 20));
        storeManageBtn.setPreferredSize(new Dimension(180, 20));
        outProgramBtn.setPreferredSize(new Dimension(180, 20));
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                NewClient newClient = new NewClient();
                newClient.setVisible(true);
            }
        });
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
    }
}
