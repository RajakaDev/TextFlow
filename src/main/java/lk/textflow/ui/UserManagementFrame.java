package lk.textflow.ui;

import lk.textflow.dao.UserDAO;
import lk.textflow.model.User;

import javax.swing.*;
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

    private User loggedInUser;

    public UserManagementFrame(User loggedInUser) {

        this.loggedInUser = loggedInUser;

        setTitle("TextFlow - User Management");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        // =====================================
        // ROLE CHECKS
        // =====================================

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


        // =====================================
        // TITLE
        // =====================================

        JLabel titleLabel =
                new JLabel(
                        "User Management",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        JLabel loggedInLabel =
                new JLabel(
                        "Logged in as: "
                                + loggedInUser.getName()
                                + " | Role: "
                                + loggedInUser.getRole()
                                + " | Position: "
                                + loggedInUser.getPosition(),
                        SwingConstants.CENTER
                );


        // =====================================
        // FORM
        // =====================================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                7,
                                2,
                                10,
                                10
                        )
                );


        nameField = new JTextField();

        usernameField = new JTextField();

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
                new JLabel(
                        "Password (Add only):"
                )
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
                new JLabel("Contact Number:")
        );

        formPanel.add(contactField);


        formPanel.add(
                new JLabel("Status:")
        );

        formPanel.add(statusComboBox);


        // =====================================
        // BUTTONS
        // =====================================

        JButton addButton =
                new JButton("Add User");

        JButton updateButton =
                new JButton("Update User");

        JButton deactivateButton =
                new JButton("Deactivate");

        JButton attendanceButton =
                new JButton("Attendance");

        JButton clearButton =
                new JButton("Clear");

        JButton logoutButton =
                new JButton("Logout");


        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(attendanceButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(logoutButton);


        // =====================================
        // SEARCH
        // =====================================

        searchField =
                new JTextField(20);

        JButton searchButton =
                new JButton("Search");

        JButton refreshButton =
                new JButton("Refresh");


        JPanel searchPanel =
                new JPanel();

        searchPanel.add(
                new JLabel(
                        "Search Username:"
                )
        );

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);


        // =====================================
        // TOP PANEL
        // =====================================

        JPanel topPanel =
                new JPanel();

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        10,
                        20
                )
        );

        topPanel.add(titleLabel);

        topPanel.add(
                Box.createVerticalStrut(5)
        );

        topPanel.add(loggedInLabel);

        topPanel.add(
                Box.createVerticalStrut(15)
        );

        topPanel.add(formPanel);

        topPanel.add(
                Box.createVerticalStrut(10)
        );

        topPanel.add(buttonPanel);

        topPanel.add(
                Box.createVerticalStrut(10)
        );

        topPanel.add(searchPanel);


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =====================================
        // TABLE
        // =====================================

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

        userTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        userTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =====================================
        // PERMISSIONS
        // =====================================

        // Only OWNER manages accounts
        addButton.setEnabled(isOwner);

        updateButton.setEnabled(isOwner);

        deactivateButton.setEnabled(isOwner);


        // Owner is the only person who edits account fields
        nameField.setEnabled(isOwner);

        usernameField.setEnabled(isOwner);

        passwordField.setEnabled(isOwner);

        roleComboBox.setEnabled(isOwner);

        positionField.setEnabled(isOwner);

        contactField.setEnabled(isOwner);

        statusComboBox.setEnabled(isOwner);


        // All roles can open attendance.
        // Employee only sees their own user row,
        // so they can only open their own attendance.
        attendanceButton.setEnabled(
                isOwner
                        || isManager
                        || isEmployee
        );


        // =====================================
        // TABLE CLICK
        // =====================================

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

                                    usernameField
                                            .setText(
                                                    value(
                                                            row,
                                                            2
                                                    )
                                            );

                                    roleComboBox
                                            .setSelectedItem(
                                                    value(
                                                            row,
                                                            3
                                                    )
                                            );

                                    positionField
                                            .setText(
                                                    value(
                                                            row,
                                                            4
                                                    )
                                            );

                                    contactField
                                            .setText(
                                                    value(
                                                            row,
                                                            5
                                                    )
                                            );

                                    statusComboBox
                                            .setSelectedItem(
                                                    value(
                                                            row,
                                                            6
                                                    )
                                            );

                                    passwordField
                                            .setText("");
                                }
                            }
                        }
                );


        // =====================================
        // ADD USER - OWNER ONLY
        // =====================================

        addButton.addActionListener(e -> {

            if (!isOwner) {

                showMessage(
                        "Only the Owner can create user accounts."
                );

                return;
            }


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

                showMessage(
                        "Name is required."
                );

                return;
            }


            if (username.isEmpty()) {

                showMessage(
                        "Username is required."
                );

                return;
            }


            if (password.isEmpty()) {

                showMessage(
                        "Password is required."
                );

                return;
            }


            if (password.length() < 6) {

                showMessage(
                        "Password must contain at least 6 characters."
                );

                return;
            }


            if (!contact.isEmpty()
                    && !contact.matches("\\d{10}")) {

                showMessage(
                        "Contact number must contain 10 digits."
                );

                return;
            }


            // The preset owner is the main OWNER.
            // Do not create additional OWNER accounts.
            if ("OWNER".equalsIgnoreCase(role)) {

                showMessage(
                        "A default Owner account already exists.\n"
                                + "Create a MANAGER or EMPLOYEE account."
                );

                return;
            }


            UserDAO userDAO =
                    new UserDAO();


            User existingUser =
                    userDAO
                            .findUserByUsername(
                                    username
                            );


            if (existingUser != null) {

                showMessage(
                        "Username already exists."
                );

                return;
            }


            User user =
                    new User();

            user.setName(name);

            user.setUsername(username);

            // addUser() hashes this
            user.setPasswordHash(password);

            user.setRole(role);

            user.setPosition(position);

            user.setContactNumber(contact);

            user.setStatus(status);


            boolean added =
                    userDAO.addUser(user);


            if (added) {

                showMessage(
                        "User added successfully!"
                );

                clearForm();

                loadUsers();

            } else {

                showMessage(
                        "User could not be added."
                );
            }
        });


        // =====================================
        // UPDATE USER - OWNER ONLY
        // =====================================

        updateButton.addActionListener(e -> {

            if (!isOwner) {

                showMessage(
                        "Only the Owner can update user accounts."
                );

                return;
            }


            int row =
                    userTable
                            .getSelectedRow();


            if (row == -1) {

                showMessage(
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

                showMessage(
                        "Name and Username are required."
                );

                return;
            }


            if (!contact.isEmpty()
                    && !contact.matches("\\d{10}")) {

                showMessage(
                        "Contact number must contain 10 digits."
                );

                return;
            }


            // Protect preset owner role
            if ("OWNER".equalsIgnoreCase(
                    originalRole
            )) {

                role = "OWNER";

                status = "ACTIVE";
            }


            // Do not promote another account to OWNER
            if (!"OWNER".equalsIgnoreCase(
                    originalRole
            )
                    && "OWNER".equalsIgnoreCase(
                    role
            )) {

                showMessage(
                        "You cannot create another Owner account."
                );

                return;
            }


            UserDAO userDAO =
                    new UserDAO();


            User existing =
                    userDAO
                            .findUserByUsername(
                                    username
                            );


            if (existing != null
                    && existing.getUserId()
                    != userId) {

                showMessage(
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


            boolean updated =
                    userDAO
                            .updateUser(user);


            if (updated) {

                showMessage(
                        "User updated successfully!"
                );

                clearForm();

                loadUsers();

            } else {

                showMessage(
                        "User could not be updated."
                );
            }
        });


        // =====================================
        // DEACTIVATE - OWNER ONLY
        // =====================================

        deactivateButton.addActionListener(e -> {

            if (!isOwner) {

                showMessage(
                        "Only the Owner can deactivate accounts."
                );

                return;
            }


            int row =
                    userTable
                            .getSelectedRow();


            if (row == -1) {

                showMessage(
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


            String selectedRole =
                    value(
                            row,
                            3
                    );


            if ("OWNER".equalsIgnoreCase(
                    selectedRole
            )) {

                showMessage(
                        "The Owner account cannot be deactivated."
                );

                return;
            }


            if (userId
                    == loggedInUser.getUserId()) {

                showMessage(
                        "You cannot deactivate your own logged-in account."
                );

                return;
            }


            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to deactivate this user?",
                            "Confirm Deactivation",
                            JOptionPane.YES_NO_OPTION
                    );


            if (confirm
                    != JOptionPane.YES_OPTION) {

                return;
            }


            UserDAO userDAO =
                    new UserDAO();


            boolean deactivated =
                    userDAO.deactivateUser(
                            userId
                    );


            if (deactivated) {

                showMessage(
                        "User deactivated successfully!"
                );

                clearForm();

                loadUsers();

            } else {

                showMessage(
                        "User could not be deactivated."
                );
            }
        });


        // =====================================
        // ATTENDANCE
        // =====================================

        attendanceButton.addActionListener(e -> {

            int row =
                    userTable
                            .getSelectedRow();


            if (row == -1) {

                showMessage(
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


            // Employee may only view own attendance
            if (isEmployee
                    && selectedUserId
                    != loggedInUser.getUserId()) {

                showMessage(
                        "Employees can only view their own attendance."
                );

                return;
            }


            boolean canManageAttendance =
                    isOwner || isManager;


            AttendanceManagementFrame frame =
                    new AttendanceManagementFrame(
                            selectedUserId,
                            canManageAttendance
                    );


            frame.setVisible(true);
        });


        // =====================================
        // SEARCH
        // =====================================

        searchButton.addActionListener(e -> {

            String username =
                    searchField
                            .getText()
                            .trim();


            if (username.isEmpty()) {

                loadUsers();

                return;
            }


            UserDAO userDAO =
                    new UserDAO();


            User user =
                    userDAO
                            .findUserByUsername(
                                    username
                            );


            tableModel.setRowCount(0);


            if (user == null) {

                showMessage(
                        "User not found."
                );

                loadUsers();

                return;
            }


            // Employee can only search themselves
            if (isEmployee
                    && user.getUserId()
                    != loggedInUser.getUserId()) {

                showMessage(
                        "Employees can only view their own account."
                );

                loadUsers();

                return;
            }


            addUserToTable(user);
        });


        // =====================================
        // REFRESH
        // =====================================

        refreshButton.addActionListener(e -> {

            searchField.setText("");

            clearForm();

            loadUsers();
        });


        // =====================================
        // CLEAR
        // =====================================

        clearButton.addActionListener(
                e -> clearForm()
        );


        // =====================================
        // LOGOUT
        // =====================================

        logoutButton.addActionListener(e -> {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );


            if (confirm
                    == JOptionPane.YES_OPTION) {

                LoginFrame loginFrame =
                        new LoginFrame();

                loginFrame.setVisible(true);

                dispose();
            }
        });


        loadUsers();
    }


    // =====================================
    // LOAD USERS BASED ON ROLE
    // =====================================

    private void loadUsers() {

        UserDAO userDAO =
                new UserDAO();

        tableModel.setRowCount(0);


        // EMPLOYEE sees only themselves
        if ("EMPLOYEE".equalsIgnoreCase(
                loggedInUser.getRole()
        )) {

            User ownUser =
                    userDAO.findUserById(
                            loggedInUser.getUserId()
                    );

            if (ownUser != null) {

                addUserToTable(
                        ownUser
                );
            }

            return;
        }


        // OWNER and MANAGER can view all users
        List<User> users =
                userDAO.getAllUsers();


        for (User user : users) {

            addUserToTable(
                    user
            );
        }
    }


    private void addUserToTable(
            User user
    ) {

        Object[] row = {

                user.getUserId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getPosition(),
                user.getContactNumber(),
                user.getStatus()

        };


        tableModel.addRow(row);
    }


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


    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message
        );
    }
}