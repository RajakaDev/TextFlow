package lk.textflow.ui;

import lk.textflow.model.User;
import lk.textflow.service.AuthenticationService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final AuthenticationService authenticationService;

    // COLORS
    private final Color DARK_BLUE = new Color(31, 60, 136);
    private final Color TEAL = new Color(22, 160, 133);
    private final Color LIGHT_BACKGROUND = new Color(245, 247, 250);
    private final Color ORANGE = new Color(243, 156, 18);
    private final Color WHITE = Color.WHITE;

    public LoginFrame() {

        authenticationService =
                new AuthenticationService();

        setTitle("TextFlow - Login");
        setSize(480, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout());

        // ===============================
        // HEADER
        // ===============================

        JPanel headerPanel = new JPanel();

        headerPanel.setBackground(DARK_BLUE);

        headerPanel.setPreferredSize(
                new Dimension(480, 100)
        );

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel("TEXTFLOW");

        titleLabel.setForeground(WHITE);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Textile Shop Management System"
                );

        subtitleLabel.setForeground(
                new Color(
                        220,
                        230,
                        245
                )
        );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        headerPanel.add(
                Box.createVerticalStrut(20)
        );

        headerPanel.add(titleLabel);

        headerPanel.add(
                Box.createVerticalStrut(5)
        );

        headerPanel.add(subtitleLabel);

        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // ===============================
        // MAIN BACKGROUND
        // ===============================

        JPanel backgroundPanel =
                new JPanel(
                        new GridBagLayout()
                );

        backgroundPanel.setBackground(
                LIGHT_BACKGROUND
        );


        // ===============================
        // LOGIN CARD
        // ===============================

        JPanel loginCard =
                new JPanel();

        loginCard.setBackground(
                WHITE
        );

        loginCard.setBorder(
                BorderFactory
                        .createCompoundBorder(
                                BorderFactory
                                        .createLineBorder(
                                                new Color(
                                                        220,
                                                        225,
                                                        230
                                                )
                                        ),
                                new EmptyBorder(
                                        25,
                                        35,
                                        25,
                                        35
                                )
                        )
        );

        loginCard.setLayout(
                new BoxLayout(
                        loginCard,
                        BoxLayout.Y_AXIS
                )
        );

        loginCard.setPreferredSize(
                new Dimension(
                        360,
                        230
                )
        );


        JLabel loginTitle =
                new JLabel(
                        "Login"
                );

        loginTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        loginTitle.setForeground(
                DARK_BLUE
        );

        loginTitle.setAlignmentX(
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

        formPanel.setOpaque(false);


        JLabel usernameLabel =
                new JLabel(
                        "Username:"
                );

        usernameField =
                new JTextField();


        JLabel passwordLabel =
                new JLabel(
                        "Password:"
                );

        passwordField =
                new JPasswordField();


        formPanel.add(
                usernameLabel
        );

        formPanel.add(
                usernameField
        );

        formPanel.add(
                passwordLabel
        );

        formPanel.add(
                passwordField
        );


        JButton loginButton =
                new JButton(
                        "LOGIN"
                );

        loginButton.setBackground(
                ORANGE
        );

        loginButton.setForeground(
                WHITE
        );

        loginButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        loginButton.setFocusPainted(
                false
        );

        loginButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        loginButton.setMaximumSize(
                new Dimension(
                        150,
                        40
                )
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        loginCard.add(
                loginTitle
        );

        loginCard.add(
                Box.createVerticalStrut(
                        25
                )
        );

        loginCard.add(
                formPanel
        );

        loginCard.add(
                Box.createVerticalStrut(
                        20
                )
        );

        loginCard.add(
                loginButton
        );


        backgroundPanel.add(
                loginCard
        );

        add(
                backgroundPanel,
                BorderLayout.CENTER
        );


        // ===============================
        // LOGIN ACTION
        // ===============================

        loginButton.addActionListener(
                e -> login()
        );

        passwordField
                .addActionListener(
                        e -> login()
                );
    }


    private void login() {

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
                    "Please enter username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
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
                    "Welcome "
                            + user.getName()
                            + "!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );


            UserManagementFrame frame =
                    new UserManagementFrame(
                            user
                    );

            frame.setVisible(
                    true
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username, password, or inactive account.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText(
                    ""
            );
        }
    }
}