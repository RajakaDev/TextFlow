package lk.textflow.ui;

import javax.swing.*;

import lk.textflow.model.User;
import lk.textflow.service.AuthenticationService;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("TextFlow - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");

        AuthenticationService authService = new AuthenticationService();

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);

        add(panel);


        loginButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = authService.login(username, password);

            if (user != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful! Welcome " + user.getName()
                );

                UserManagementFrame userManagementFrame =
                        new UserManagementFrame();

                userManagementFrame.setVisible(true);

                dispose();
            }

            else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username, password, or inactive account."
                );
            }
        });

    }


}