package lk.textflow.ui;

import javax.swing.*;

public class UserManagementFrame extends JFrame {

    public UserManagementFrame() {

        setTitle("TextFlow - User Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("User Management");

        add(titleLabel);
    }
}