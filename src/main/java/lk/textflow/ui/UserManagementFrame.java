package lk.textflow.ui;

import lk.textflow.dao.UserDAO;
import lk.textflow.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JTextField positionField;
    private JTextField contactField;
    private JComboBox<String> statusComboBox;
    private JTextField searchField;

    private final User loggedInUser;

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color LIGHT_BACKGROUND =
            new Color(245, 247, 250);

    public UserManagementFrame(User loggedInUser) {

        this.loggedInUser = loggedInUser;

        boolean isOwner =
                "OWNER".equalsIgnoreCase(
                        loggedInUser.getRole()
                );

        boolean isManager =
                "MANAGER".equalsIgnoreCase(
                        loggedInUser.getRole()
                );

        boolean isEmployee =
                "EMPLOYEE".equalsIgnoreCase(
                        loggedInUser.getRole()
                );

        setTitle("TextFlow - User Management");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==================================================
        // HEADER
        // ==================================================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(DARK_BLUE);

        headerPanel.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW  |  User Management"
                );

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel loggedInLabel =
                new JLabel(
                        loggedInUser.getName()
                                + "  |  "
                                + loggedInUser.getRole()
                );

        loggedInLabel.setForeground(Color.WHITE);

        loggedInLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                loggedInLabel,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // MAIN PANEL
        // ==================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBackground(
                LIGHT_BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        // ==================================================
        // USER DETAILS CARD
        // ==================================================

        JPanel formCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        formCard.setBackground(
                Color.WHITE
        );

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        JLabel formTitle =
                new JLabel(
                        "User Details"
                );

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        formTitle.setForeground(
                DARK_BLUE
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                4,
                                12,
                                12
                        )
                );

        formPanel.setOpaque(false);

        nameField =
                new JTextField();

        usernameField =
                new JTextField();

        passwordField =
                new JPasswordField();

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "EMPLOYEE",
                                "MANAGER",
                                "OWNER"
                        }
                );

        positionField =
                new JTextField();

        contactField =
                new JTextField();

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );

        formPanel.add(
                new JLabel("Name:")
        );

        formPanel.add(nameField);

        formPanel.add(
                new JLabel("Username:")
        );

        formPanel.add(usernameField);

        formPanel.add(
                new JLabel("Password:")
        );

        formPanel.add(passwordField);

        formPanel.add(
                new JLabel("Role:")
        );

        formPanel.add(roleComboBox);

        formPanel.add(
                new JLabel("Position:")
        );

        formPanel.add(positionField);

        formPanel.add(
                new JLabel("Contact:")
        );

        formPanel.add(contactField);

        formPanel.add(
                new JLabel("Status:")
        );

        formPanel.add(statusComboBox);

        formPanel.add(
                new JLabel("")
        );

        formPanel.add(
                new JLabel("")
        );

        // ==================================================
        // POLISHED LIGHT BUTTONS
        // ==================================================

        JButton addButton =
                createButton(
                        "Add User",
                        new Color(
                                220,
                                245,
                                228
                        ),
                        new Color(
                                34,
                                100,
                                58
                        )
                );

        JButton updateButton =
                createButton(
                        "Update",
                        new Color(
                                219,
                                234,
                                254
                        ),
                        new Color(
                                30,
                                64,
                                175
                        )
                );

        JButton attendanceButton =
                createButton(
                        "Attendance",
                        new Color(
                                237,
                                233,
                                254
                        ),
                        new Color(
                                91,
                                33,
                                182
                        )
                );

        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(
                                243,
                                244,
                                246
                        ),
                        new Color(
                                55,
                                65,
                                81
                        )
                );

        JButton logoutButton =
                createButton(
                        "Logout",
                        new Color(
                                254,
                                226,
                                226
                        ),
                        new Color(
                                127,
                                29,
                                29
                        )
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(attendanceButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(logoutButton);

        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );

        formCard.add(
                formPanel,
                BorderLayout.CENTER
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                formCard,
                BorderLayout.NORTH
        );

        // ==================================================
        // USER TABLE CARD
        // ==================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        tableCard.setBackground(
                Color.WHITE
        );

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel recordsLabel =
                new JLabel(
                        "User Records"
                );

        recordsLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        recordsLabel.setForeground(
                DARK_BLUE
        );

        searchField =
                new JTextField(20);

        JButton searchButton =
                createButton(
                        "Search",
                        new Color(
                                254,
                                243,
                                199
                        ),
                        new Color(
                                146,
                                64,
                                14
                        )
                );

        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(
                                204,
                                251,
                                241
                        ),
                        new Color(
                                17,
                                94,
                                89
                        )
                );

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        searchPanel.setOpaque(false);

        searchPanel.add(
                new JLabel(
                        "Search Username:"
                )
        );

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        JPanel tableTop =
                new JPanel(
                        new BorderLayout()
                );

        tableTop.setOpaque(false);

        tableTop.add(
                recordsLabel,
                BorderLayout.WEST
        );

        tableTop.add(
                searchPanel,
                BorderLayout.EAST
        );

        String[] columns = {
                "ID",
                "Name",
                "Username",
                "Role",
                "Position",
                "Contact",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        userTable =
                new JTable(
                        tableModel
                );

        userTable.setRowHeight(28);

        userTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        userTable.setGridColor(
                new Color(
                        225,
                        230,
                        235
                )
        );

        userTable
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        userTable
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        userTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        userTable
                );

        tableCard.add(
                tableTop,
                BorderLayout.NORTH
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // ROLE PERMISSIONS
        // ==================================================

        addButton.setEnabled(isOwner);
        updateButton.setEnabled(isOwner);

        nameField.setEnabled(isOwner);
        usernameField.setEnabled(isOwner);
        passwordField.setEnabled(isOwner);
        roleComboBox.setEnabled(isOwner);
        positionField.setEnabled(isOwner);
        contactField.setEnabled(isOwner);
        statusComboBox.setEnabled(isOwner);

        attendanceButton.setEnabled(
                isOwner
                        || isManager
                        || isEmployee
        );

        // ==================================================
        // TABLE CLICK
        // ==================================================

        userTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                int row =
                                        userTable
                                                .getSelectedRow();

                                if (row != -1) {

                                    nameField.setText(
                                            value(
                                                    row,
                                                    1
                                            )
                                    );

                                    usernameField.setText(
                                            value(
                                                    row,
                                                    2
                                            )
                                    );

                                    roleComboBox.setSelectedItem(
                                            value(
                                                    row,
                                                    3
                                            )
                                    );

                                    positionField.setText(
                                            value(
                                                    row,
                                                    4
                                            )
                                    );

                                    contactField.setText(
                                            value(
                                                    row,
                                                    5
                                            )
                                    );

                                    statusComboBox.setSelectedItem(
                                            value(
                                                    row,
                                                    6
                                            )
                                    );

                                    passwordField.setText("");
                                }
                            }
                        }
                );

        // ==================================================
        // ADD USER
        // ==================================================

        addButton.addActionListener(e -> {

            String name =
                    nameField
                            .getText()
                            .trim();

            String username =
                    usernameField
                            .getText()
                            .trim();

            String password =
                    new String(
                            passwordField
                                    .getPassword()
                    );

            String role =
                    roleComboBox
                            .getSelectedItem()
                            .toString();

            String position =
                    positionField
                            .getText()
                            .trim();

            String contact =
                    contactField
                            .getText()
                            .trim();

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            if (name.isEmpty()) {

                message(
                        "Name is required."
                );

                return;
            }

            if (username.isEmpty()) {

                message(
                        "Username is required."
                );

                return;
            }

            if (password.isEmpty()) {

                message(
                        "Password is required."
                );

                return;
            }

            if (password.length() < 6) {

                message(
                        "Password must have at least 6 characters."
                );

                return;
            }

            if (!contact.isEmpty()
                    && !contact.matches(
                    "\\d{10}"
            )) {

                message(
                        "Contact number must contain 10 digits."
                );

                return;
            }

            if ("OWNER".equalsIgnoreCase(
                    role
            )) {

                message(
                        "The system already has an Owner.\n"
                                + "Please create a Manager or Employee."
                );

                return;
            }

            UserDAO userDAO =
                    new UserDAO();

            if (userDAO
                    .findUserByUsername(
                            username
                    ) != null) {

                message(
                        "Username already exists."
                );

                return;
            }

            User user =
                    new User();

            user.setName(name);
            user.setUsername(username);
            user.setPasswordHash(password);
            user.setRole(role);
            user.setPosition(position);
            user.setContactNumber(contact);
            user.setStatus(status);

            if (userDAO.addUser(user)) {

                message(
                        "User added successfully!"
                );

                clearForm();

                loadUsers();

            } else {

                message(
                        "User could not be added."
                );
            }
        });

        // ==================================================
        // UPDATE USER
        // ==================================================

        updateButton.addActionListener(e -> {

            int row =
                    userTable
                            .getSelectedRow();

            if (row == -1) {

                message(
                        "Please select a user first."
                );

                return;
            }

            int userId =
                    Integer.parseInt(
                            value(
                                    row,
                                    0
                            )
                    );

            String originalRole =
                    value(
                            row,
                            3
                    );

            String name =
                    nameField
                            .getText()
                            .trim();

            String username =
                    usernameField
                            .getText()
                            .trim();

            String role =
                    roleComboBox
                            .getSelectedItem()
                            .toString();

            String position =
                    positionField
                            .getText()
                            .trim();

            String contact =
                    contactField
                            .getText()
                            .trim();

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            if (name.isEmpty()
                    || username.isEmpty()) {

                message(
                        "Name and Username are required."
                );

                return;
            }

            if (!contact.isEmpty()
                    && !contact.matches(
                    "\\d{10}"
            )) {

                message(
                        "Contact number must contain 10 digits."
                );

                return;
            }

            // Protect the system OWNER account
            if ("OWNER".equalsIgnoreCase(
                    originalRole
            )) {

                role = "OWNER";
                status = "ACTIVE";
            }

            // Do not promote another user to OWNER
            if (!"OWNER".equalsIgnoreCase(
                    originalRole
            )
                    && "OWNER".equalsIgnoreCase(
                    role
            )) {

                message(
                        "Another Owner account cannot be created."
                );

                return;
            }

            UserDAO userDAO =
                    new UserDAO();

            User existing =
                    userDAO.findUserByUsername(
                            username
                    );

            if (existing != null
                    && existing.getUserId()
                    != userId) {

                message(
                        "Username already belongs to another user."
                );

                return;
            }

            User user =
                    new User();

            user.setUserId(userId);
            user.setName(name);
            user.setUsername(username);
            user.setRole(role);
            user.setPosition(position);
            user.setContactNumber(contact);
            user.setStatus(status);

            if (userDAO.updateUser(user)) {

                message(
                        "User updated successfully!"
                );

                clearForm();

                loadUsers();

            } else {

                message(
                        "User could not be updated."
                );
            }
        });

        // ==================================================
        // ATTENDANCE
        // ==================================================

        attendanceButton.addActionListener(e -> {

            int row =
                    userTable
                            .getSelectedRow();

            if (row == -1) {

                message(
                        "Please select a user first."
                );

                return;
            }

            int selectedUserId =
                    Integer.parseInt(
                            value(
                                    row,
                                    0
                            )
                    );

            if (isEmployee
                    && selectedUserId
                    != loggedInUser
                    .getUserId()) {

                message(
                        "Employees can only view their own attendance."
                );

                return;
            }

            boolean canManage =
                    isOwner || isManager;

            AttendanceManagementFrame frame =
                    new AttendanceManagementFrame(
                            selectedUserId,
                            canManage
                    );

            frame.setVisible(true);
        });

        // ==================================================
        // SEARCH
        // ==================================================

        searchButton.addActionListener(e -> {

            String username =
                    searchField
                            .getText()
                            .trim();

            if (username.isEmpty()) {

                loadUsers();

                return;
            }

            UserDAO dao =
                    new UserDAO();

            User user =
                    dao.findUserByUsername(
                            username
                    );

            tableModel.setRowCount(0);

            if (user == null) {

                message(
                        "User not found."
                );

                loadUsers();

                return;
            }

            if (isEmployee
                    && user.getUserId()
                    != loggedInUser
                    .getUserId()) {

                message(
                        "Employees can only view their own account."
                );

                loadUsers();

                return;
            }

            addUserToTable(user);
        });

        refreshButton.addActionListener(e -> {

            searchField.setText("");

            clearForm();

            loadUsers();
        });

        clearButton.addActionListener(
                e -> clearForm()
        );

        // ==================================================
        // LOGOUT
        // ==================================================

        logoutButton.addActionListener(e -> {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Logout from TextFlow?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm
                    == JOptionPane.YES_OPTION) {

                new LoginFrame()
                        .setVisible(true);

                dispose();
            }
        });

        loadUsers();
    }

    // ==================================================
    // BUTTON STYLE
    // ==================================================

    private JButton createButton(
            String text,
            Color backgroundColor,
            Color textColor
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(
                backgroundColor
        );

        button.setForeground(
                textColor
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        125,
                        36
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                backgroundColor.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                7,
                                14,
                                7,
                                14
                        )
                )
        );

        return button;
    }

    // ==================================================
    // LOAD USERS
    // ==================================================

    private void loadUsers() {

        UserDAO dao =
                new UserDAO();

        tableModel.setRowCount(0);

        if ("EMPLOYEE".equalsIgnoreCase(
                loggedInUser.getRole()
        )) {

            User user =
                    dao.findUserById(
                            loggedInUser
                                    .getUserId()
                    );

            if (user != null) {

                addUserToTable(user);
            }

            return;
        }

        List<User> users =
                dao.getAllUsers();

        for (User user : users) {

            addUserToTable(user);
        }
    }

    // ==================================================
    // ADD USER TO TABLE
    // ==================================================

    private void addUserToTable(
            User user
    ) {

        tableModel.addRow(
                new Object[]{
                        user.getUserId(),
                        user.getName(),
                        user.getUsername(),
                        user.getRole(),
                        user.getPosition(),
                        user.getContactNumber(),
                        user.getStatus()
                }
        );
    }

    // ==================================================
    // READ TABLE VALUE
    // ==================================================

    private String value(
            int row,
            int column
    ) {

        Object value =
                tableModel.getValueAt(
                        row,
                        column
                );

        return value == null
                ? ""
                : value.toString();
    }

    // ==================================================
    // CLEAR FORM
    // ==================================================

    private void clearForm() {

        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        positionField.setText("");
        contactField.setText("");

        roleComboBox.setSelectedIndex(0);

        statusComboBox.setSelectedIndex(0);

        userTable.clearSelection();
    }

    // ==================================================
    // MESSAGE
    // ==================================================

    private void message(
            String text
    ) {

        JOptionPane.showMessageDialog(
                this,
                text
        );
    }
}