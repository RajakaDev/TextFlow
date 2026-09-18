package lk.textflow.ui;

import lk.textflow.model.User;
import lk.textflow.service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private AuthenticationService authenticationService;

    public LoginFrame() {

        authenticationService =
                new AuthenticationService();

        setTitle("TextFlow - Login");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TextFlow Login",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                15
                        )
                );

        usernameField =
                new JTextField();

        passwordField =
                new JPasswordField();

        formPanel.add(
                new JLabel("Username:")
        );

        formPanel.add(usernameField);

        formPanel.add(
                new JLabel("Password:")
        );

        formPanel.add(passwordField);

        JButton loginButton =
                new JButton("Login");

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(titleLabel);

        mainPanel.add(
                Box.createVerticalStrut(30)
        );

        mainPanel.add(formPanel);

        mainPanel.add(
                Box.createVerticalStrut(20)
        );

        mainPanel.add(loginButton);

        add(mainPanel);


        loginButton.addActionListener(e -> {

            String username =
                    usernameField
                            .getText()
                            .trim();

            String password =
                    new String(
                            passwordField
                                    .getPassword()
                    );

            if (username.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password."
                );

                return;
            }

            User user =
                    authenticationService.login(
                            username,
                            password
                    );

            if (user != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!\nWelcome "
                                + user.getName()
                );

                UserManagementFrame frame =
                        new UserManagementFrame(user);

                frame.setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username, password, or inactive account."
                );

                passwordField.setText("");
            }
        });


        passwordField.addActionListener(
                e -> loginButton.doClick()
        );
    }
}