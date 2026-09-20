package lk.textflow.supplier.view;

import lk.textflow.supplier.dao.SupplierDAO;
import lk.textflow.supplier.model.Supplier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupplierManagementFrame extends JFrame {

    private JTextField nameField;
    private JTextField contactField;
    private JTextField addressField;
    private JTextField emailField;

    private JComboBox<String> statusComboBox;

    private JTable supplierTable;
    private DefaultTableModel tableModel;

    private final SupplierDAO supplierDAO;
    private final int loggedInUserId;

    // ==============================
    // COLORS
    // ==============================

    private final Color DARK_BLUE = new Color(31, 60, 136);
    private final Color LIGHT_BACKGROUND = new Color(245, 247, 250);
    private final Color CARD_BORDER = new Color(220, 225, 230);

    // ==============================
    // CONSTRUCTOR
    // ==============================

    public SupplierManagementFrame(int loggedInUserId) {

        this.loggedInUserId = loggedInUserId;
        supplierDAO = new SupplierDAO();

        setTitle("TextFlow - Supplier Management");
        setSize(1050, 700);
        setMinimumSize(new Dimension(900, 600));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadSuppliers();
    }

    // ==============================
    // UI
    // ==============================

    private void createUI() {

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(LIGHT_BACKGROUND);

        // ==============================
        // HEADER
        // ==============================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(new EmptyBorder(17, 25, 17, 25));

        JLabel titleLabel =
                new JLabel("TEXTFLOW  |  Supplier Management");

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 23)
        );

        JLabel userLabel =
                new JLabel("User ID: " + loggedInUserId);

        userLabel.setForeground(
                new Color(220, 230, 245)
        );

        userLabel.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                userLabel,
                BorderLayout.EAST
        );

        rootPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==============================
        // MAIN CONTENT
        // ==============================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        contentPanel.setBackground(
                LIGHT_BACKGROUND
        );

        contentPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        // ==============================
        // FORM CARD
        // ==============================

        JPanel formCard =
                new JPanel(
                        new BorderLayout(10, 15)
                );

        formCard.setBackground(Color.WHITE);

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                18, 20, 18, 20
                        )
                )
        );

        JLabel formTitle =
                new JLabel("Supplier Details");

        formTitle.setForeground(DARK_BLUE);

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );

        // ==============================
        // FIELDS
        // ==============================

        nameField = new JTextField();
        contactField = new JTextField();
        addressField = new JTextField();
        emailField = new JTextField();

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );

        JPanel fieldsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7, 8, 7, 8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                0,
                "Supplier Name:",
                nameField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                2,
                "Contact Number:",
                contactField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                0,
                "Address:",
                addressField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                2,
                "Email:",
                emailField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                2,
                0,
                "Status:",
                statusComboBox
        );

        formCard.add(
                fieldsPanel,
                BorderLayout.CENTER
        );

        // ==============================
        // BUTTONS
        // ==============================

        JButton addButton =
                createButton(
                        "Add Supplier",
                        new Color(220, 245, 228),
                        new Color(34, 100, 58)
                );

        JButton updateButton =
                createButton(
                        "Update Supplier",
                        new Color(219, 234, 254),
                        new Color(30, 64, 175)
                );

        JButton purchaseButton =
                createButton(
                        "Purchase Management",
                        new Color(237, 233, 254),
                        new Color(91, 33, 182)
                );

        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(204, 251, 241),
                        new Color(17, 94, 89)
                );

        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(243, 244, 246),
                        new Color(55, 65, 81)
                );

        JButton closeButton =
                createButton(
                        "Close",
                        new Color(254, 226, 226),
                        new Color(153, 27, 27)
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                3
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        contentPanel.add(
                formCard,
                BorderLayout.NORTH
        );

        // ==============================
        // TABLE CARD
        // ==============================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        tableCard.setBackground(Color.WHITE);

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        JLabel tableTitle =
                new JLabel("Supplier Records");

        tableTitle.setForeground(DARK_BLUE);

        tableTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        tableCard.add(
                tableTitle,
                BorderLayout.NORTH
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Supplier Name",
                                "Contact",
                                "Address",
                                "Email",
                                "Status"
                        },
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

        supplierTable =
                new JTable(tableModel);

        supplierTable.setRowHeight(28);

        supplierTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        supplierTable.setGridColor(
                new Color(230, 233, 238)
        );

        supplierTable
                .getTableHeader()
                .setBackground(DARK_BLUE);

        supplierTable
                .getTableHeader()
                .setForeground(Color.WHITE);

        supplierTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        supplierTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 32)
                );

        supplierTable
                .getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(
                        supplierTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        CARD_BORDER
                )
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        contentPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        rootPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        setContentPane(rootPanel);

        // ==============================
        // ACTIONS
        // ==============================

        addButton.addActionListener(
                e -> addSupplier()
        );

        updateButton.addActionListener(
                e -> updateSupplier()
        );

        purchaseButton.addActionListener(
                e -> new PurchaseManagementFrame(
                        loggedInUserId
                ).setVisible(true)
        );

        refreshButton.addActionListener(
                e -> loadSuppliers()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        supplierTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                loadSelectedSupplier();
                            }
                        }
                );
    }

    // ==============================
    // FORM ROW
    // ==============================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int startColumn,
            String labelText,
            Component component
    ) {

        gbc.gridx = startColumn;
        gbc.gridy = row;

        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        label.setPreferredSize(
                new Dimension(
                        125,
                        30
                )
        );

        panel.add(label, gbc);

        gbc.gridx =
                startColumn + 1;

        gbc.weightx = 1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        if (component instanceof JComponent) {

            ((JComponent) component)
                    .setPreferredSize(
                            new Dimension(
                                    280,
                                    34
                            )
                    );
        }

        panel.add(
                component,
                gbc
        );
    }

    // ==============================
    // ADD
    // ==============================

    private void addSupplier() {

        String name =
                nameField
                        .getText()
                        .trim();

        String contact =
                contactField
                        .getText()
                        .trim();

        String address =
                addressField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String status =
                statusComboBox
                        .getSelectedItem()
                        .toString();

        if (name.isEmpty()) {

            warning(
                    "Supplier name is required."
            );

            return;
        }

        if (
                !contact.isEmpty()
                        &&
                        !contact.matches("\\d{10}")
        ) {

            warning(
                    "Contact number must contain 10 digits."
            );

            return;
        }

        if (
                !email.isEmpty()
                        &&
                        !email.matches(
                                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                        )
        ) {

            warning(
                    "Please enter a valid email address."
            );

            return;
        }

        Supplier supplier =
                new Supplier();

        supplier.setSupplierName(name);
        supplier.setContactNumber(contact);
        supplier.setAddress(address);
        supplier.setEmail(email);
        supplier.setStatus(status);

        boolean success =
                supplierDAO.addSupplier(
                        supplier
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier could not be added.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==============================
    // UPDATE
    // ==============================

    private void updateSupplier() {

        int selectedRow =
                supplierTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            warning(
                    "Please select a supplier first."
            );

            return;
        }

        String name =
                nameField
                        .getText()
                        .trim();

        String contact =
                contactField
                        .getText()
                        .trim();

        String address =
                addressField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            warning(
                    "Supplier name is required."
            );

            return;
        }

        if (
                !contact.isEmpty()
                        &&
                        !contact.matches("\\d{10}")
        ) {

            warning(
                    "Contact number must contain 10 digits."
            );

            return;
        }

        if (
                !email.isEmpty()
                        &&
                        !email.matches(
                                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                        )
        ) {

            warning(
                    "Please enter a valid email address."
            );

            return;
        }

        int supplierId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );

        Supplier supplier =
                new Supplier();

        supplier.setSupplierId(
                supplierId
        );

        supplier.setSupplierName(
                name
        );

        supplier.setContactNumber(
                contact
        );

        supplier.setAddress(
                address
        );

        supplier.setEmail(
                email
        );

        supplier.setStatus(
                statusComboBox
                        .getSelectedItem()
                        .toString()
        );

        boolean success =
                supplierDAO.updateSupplier(
                        supplier
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier could not be updated.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==============================
    // LOAD SELECTED
    // ==============================

    private void loadSelectedSupplier() {

        int selectedRow =
                supplierTable
                        .getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        nameField.setText(
                tableValue(
                        selectedRow,
                        1
                )
        );

        contactField.setText(
                tableValue(
                        selectedRow,
                        2
                )
        );

        addressField.setText(
                tableValue(
                        selectedRow,
                        3
                )
        );

        emailField.setText(
                tableValue(
                        selectedRow,
                        4
                )
        );

        statusComboBox.setSelectedItem(
                tableValue(
                        selectedRow,
                        5
                )
        );
    }

    // ==============================
    // LOAD
    // ==============================

    private void loadSuppliers() {

        tableModel.setRowCount(0);

        List<Supplier> suppliers =
                supplierDAO
                        .getAllSuppliers();

        for (Supplier supplier : suppliers) {

            tableModel.addRow(
                    new Object[]{
                            supplier.getSupplierId(),
                            supplier.getSupplierName(),
                            supplier.getContactNumber(),
                            supplier.getAddress(),
                            supplier.getEmail(),
                            supplier.getStatus()
                    }
            );
        }
    }

    // ==============================
    // CLEAR
    // ==============================

    private void clearForm() {

        nameField.setText("");
        contactField.setText("");
        addressField.setText("");
        emailField.setText("");

        statusComboBox.setSelectedItem(
                "ACTIVE"
        );

        supplierTable.clearSelection();

        nameField.requestFocus();
    }

    private String tableValue(
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

    // ==============================
    // BUTTON
    // ==============================

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(background);
        button.setForeground(foreground);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                background.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 14, 8, 14
                        )
                )
        );

        return button;
    }

    private void warning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
        );
    }
}