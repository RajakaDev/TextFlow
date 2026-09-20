package com.textflow.rahman.ui;

import com.textflow.rahman.dao.CustomerDAO;
import com.textflow.rahman.model.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {

    // ==================================================
    // DAO
    // ==================================================

    private final CustomerDAO customerDAO;

    // ==================================================
    // FIELDS
    // ==================================================

    private JTextField txtName;
    private JTextField txtContact;
    private JTextField txtAddress;
    private JTextField txtLoyalty;
    private JTextField txtSearch;

    private JTable customerTable;
    private DefaultTableModel tableModel;

    // ==================================================
    // COLORS
    // ==================================================

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color LIGHT_BACKGROUND =
            new Color(245, 247, 250);

    private final Color CARD_BORDER =
            new Color(220, 225, 230);

    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public CustomerPanel() {

        customerDAO =
                new CustomerDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                LIGHT_BACKGROUND
        );

        createUI();

        loadCustomers();
    }

    // ==================================================
    // CREATE UI
    // ==================================================

    private void createUI() {

        // ==================================================
        // HEADER
        // ==================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                DARK_BLUE
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        17,
                        25,
                        17,
                        25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW  |  Customer Management"
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        23
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // CONTENT
        // ==================================================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        contentPanel.setBackground(
                LIGHT_BACKGROUND
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // ==================================================
        // FORM CARD
        // ==================================================

        JPanel formCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                15
                        )
                );

        formCard.setBackground(
                Color.WHITE
        );

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        JLabel formTitle =
                new JLabel(
                        "Customer Details"
                );

        formTitle.setForeground(
                DARK_BLUE
        );

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

        // ==================================================
        // FIELDS
        // ==================================================

        txtName =
                new JTextField();

        txtContact =
                new JTextField();

        txtAddress =
                new JTextField();

        txtLoyalty =
                new JTextField();

        JPanel fieldsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        fieldsPanel.setOpaque(
                false
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        8,
                        7,
                        8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                0,
                "Customer Name:",
                txtName
        );

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                2,
                "Contact Number:",
                txtContact
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                0,
                "Address:",
                txtAddress
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                2,
                "Loyalty Points:",
                txtLoyalty
        );

        formCard.add(
                fieldsPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // BUTTONS
        // ==================================================

        JButton btnAdd =
                createButton(
                        "Add Customer",
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

        JButton btnUpdate =
                createButton(
                        "Update Customer",
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

        JButton btnDeactivate =
                createButton(
                        "Deactivate",
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

        JButton btnDelete =
                createButton(
                        "Delete",
                        new Color(
                                254,
                                226,
                                226
                        ),
                        new Color(
                                153,
                                27,
                                27
                        )
                );

        JButton btnClear =
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

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                3
                        )
                );

        buttonPanel.setOpaque(
                false
        );

        buttonPanel.add(
                btnAdd
        );

        buttonPanel.add(
                btnUpdate
        );

        buttonPanel.add(
                btnDeactivate
        );

        buttonPanel.add(
                btnDelete
        );

        buttonPanel.add(
                btnClear
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        contentPanel.add(
                formCard,
                BorderLayout.NORTH
        );

        // ==================================================
        // CUSTOMER TABLE CARD
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
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        // ==================================================
        // SEARCH
        // ==================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        searchPanel.setOpaque(
                false
        );

        JLabel tableTitle =
                new JLabel(
                        "Customer Records"
                );

        tableTitle.setForeground(
                DARK_BLUE
        );

        tableTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        JPanel searchControls =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        searchControls.setOpaque(
                false
        );

        JLabel searchLabel =
                new JLabel(
                        "Search:"
                );

        searchLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        txtSearch =
                new JTextField(
                        18
                );

        txtSearch.setPreferredSize(
                new Dimension(
                        220,
                        34
                )
        );

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

        searchControls.add(
                searchLabel
        );

        searchControls.add(
                txtSearch
        );

        searchControls.add(
                searchButton
        );

        searchControls.add(
                refreshButton
        );

        searchPanel.add(
                tableTitle,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchControls,
                BorderLayout.EAST
        );

        tableCard.add(
                searchPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // TABLE
        // ==================================================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Customer Name",
                                "Contact Number",
                                "Address",
                                "Loyalty Points",
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

        customerTable =
                new JTable(
                        tableModel
                );

        customerTable.setRowHeight(
                28
        );

        customerTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        customerTable.setGridColor(
                new Color(
                        230,
                        233,
                        238
                )
        );

        customerTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        customerTable
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        customerTable
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        customerTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        customerTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                32
                        )
                );

        customerTable
                .getTableHeader()
                .setReorderingAllowed(
                        false
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        customerTable
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

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // ACTIONS
        // ==================================================

        btnAdd.addActionListener(
                e -> addCustomer()
        );

        btnUpdate.addActionListener(
                e -> updateCustomer()
        );

        btnDeactivate.addActionListener(
                e -> deactivateCustomer()
        );

        btnDelete.addActionListener(
                e -> deleteCustomer()
        );

        btnClear.addActionListener(
                e -> clearForm()
        );

        searchButton.addActionListener(
                e -> searchCustomers()
        );

        refreshButton.addActionListener(
                e -> {
                    txtSearch.setText("");
                    loadCustomers();
                }
        );

        txtSearch.addActionListener(
                e -> searchCustomers()
        );

        txtSearch
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {

                                searchCustomers();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {

                                searchCustomers();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {

                                searchCustomers();
                            }
                        }
                );

        customerTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (
                                    !e.getValueIsAdjusting()
                            ) {

                                loadSelectedCustomer();
                            }
                        }
                );
    }

    // ==================================================
    // FORM ROW
    // ==================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int startColumn,
            String labelText,
            Component component
    ) {

        gbc.gridx =
                startColumn;

        gbc.gridy =
                row;

        gbc.weightx =
                0;

        gbc.fill =
                GridBagConstraints.NONE;

        JLabel label =
                new JLabel(
                        labelText
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        label.setPreferredSize(
                new Dimension(
                        120,
                        30
                )
        );

        panel.add(
                label,
                gbc
        );

        gbc.gridx =
                startColumn + 1;

        gbc.weightx =
                1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        if (
                component instanceof JComponent
        ) {

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

    // ==================================================
    // ADD CUSTOMER
    // ==================================================

    private void addCustomer() {

        Customer customer =
                readCustomerForm();

        if (
                customer == null
        ) {
            return;
        }

        customer.setStatus(
                "ACTIVE"
        );

        boolean success =
                customerDAO.addCustomer(
                        customer
                );

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer added successfully!\n"
                            + "Customer ID: "
                            + customer.getCustomerId(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadCustomers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer could not be added.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // UPDATE CUSTOMER
    // ==================================================

    private void updateCustomer() {

        int row =
                customerTable
                        .getSelectedRow();

        if (
                row == -1
        ) {

            warning(
                    "Please select a customer to update."
            );

            return;
        }

        Customer customer =
                readCustomerForm();

        if (
                customer == null
        ) {
            return;
        }

        customer.setCustomerId(
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                )
        );

        customer.setStatus(
                tableModel
                        .getValueAt(
                                row,
                                5
                        )
                        .toString()
        );

        boolean success =
                customerDAO.updateCustomer(
                        customer
                );

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadCustomers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer could not be updated.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // DEACTIVATE
    // ==================================================

    private void deactivateCustomer() {

        int row =
                customerTable
                        .getSelectedRow();

        if (
                row == -1
        ) {

            warning(
                    "Please select a customer to deactivate."
            );

            return;
        }

        String status =
                tableModel
                        .getValueAt(
                                row,
                                5
                        )
                        .toString();

        if (
                "INACTIVE"
                        .equalsIgnoreCase(
                                status
                        )
        ) {

            warning(
                    "This customer is already inactive."
            );

            return;
        }

        int customerId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );

        String name =
                tableModel
                        .getValueAt(
                                row,
                                1
                        )
                        .toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deactivate customer: "
                                + name
                                + "?",
                        "Confirm Deactivation",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                customerDAO
                        .deactivateCustomer(
                                customerId
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer deactivated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadCustomers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer could not be deactivated.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // DELETE
    // ==================================================

    private void deleteCustomer() {

        int row =
                customerTable
                        .getSelectedRow();

        if (
                row == -1
        ) {

            warning(
                    "Please select a customer to delete."
            );

            return;
        }

        int customerId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );

        String name =
                tableModel
                        .getValueAt(
                                row,
                                1
                        )
                        .toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Permanently delete customer: "
                                + name
                                + "?\n\n"
                                + "If this customer has sales records, "
                                + "the database may prevent deletion.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        boolean success =
                customerDAO.deleteCustomer(
                        customerId
                );

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadCustomers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer could not be deleted.\n\n"
                            + "If this customer has previous sales, "
                            + "use Deactivate instead.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ==================================================
    // SEARCH
    // ==================================================

    private void searchCustomers() {

        String keyword =
                txtSearch
                        .getText()
                        .trim();

        if (
                keyword.isEmpty()
        ) {

            loadCustomers();

            return;
        }

        List<Customer> customers =
                customerDAO
                        .searchCustomers(
                                keyword
                        );

        displayCustomers(
                customers
        );
    }

    // ==================================================
    // LOAD ALL
    // ==================================================

    private void loadCustomers() {

        List<Customer> customers =
                customerDAO
                        .getAllCustomers();

        displayCustomers(
                customers
        );
    }

    // ==================================================
    // DISPLAY
    // ==================================================

    private void displayCustomers(
            List<Customer> customers
    ) {

        tableModel.setRowCount(
                0
        );

        for (
                Customer customer
                : customers
        ) {

            tableModel.addRow(
                    new Object[]{
                            customer.getCustomerId(),
                            customer.getCustomerName(),
                            customer.getContactNumber(),
                            customer.getAddress(),
                            customer.getLoyaltyPoints(),
                            customer.getStatus()
                    }
            );
        }
    }

    // ==================================================
    // LOAD SELECTED CUSTOMER
    // ==================================================

    private void loadSelectedCustomer() {

        int row =
                customerTable
                        .getSelectedRow();

        if (
                row == -1
        ) {
            return;
        }

        txtName.setText(
                valueAt(
                        row,
                        1
                )
        );

        txtContact.setText(
                valueAt(
                        row,
                        2
                )
        );

        txtAddress.setText(
                valueAt(
                        row,
                        3
                )
        );

        txtLoyalty.setText(
                valueAt(
                        row,
                        4
                )
        );
    }

    // ==================================================
    // READ FORM
    // ==================================================

    private Customer readCustomerForm() {

        String name =
                txtName
                        .getText()
                        .trim();

        String contact =
                txtContact
                        .getText()
                        .trim();

        String address =
                txtAddress
                        .getText()
                        .trim();

        String loyaltyText =
                txtLoyalty
                        .getText()
                        .trim();

        if (
                name.isEmpty()
        ) {

            warning(
                    "Customer name is required."
            );

            return null;
        }

        if (
                !contact.isEmpty()
                        &&
                        !contact.matches(
                                "\\d{10}"
                        )
        ) {

            warning(
                    "Contact number must contain 10 digits."
            );

            return null;
        }

        int loyaltyPoints;

        try {

            loyaltyPoints =
                    loyaltyText.isEmpty()
                            ? 0
                            : Integer.parseInt(
                            loyaltyText
                    );

            if (
                    loyaltyPoints < 0
            ) {

                throw new NumberFormatException();
            }

        } catch (
                NumberFormatException e
        ) {

            warning(
                    "Loyalty points must be zero or greater."
            );

            return null;
        }

        Customer customer =
                new Customer();

        customer.setCustomerName(
                name
        );

        customer.setContactNumber(
                contact
        );

        customer.setAddress(
                address
        );

        customer.setLoyaltyPoints(
                loyaltyPoints
        );

        return customer;
    }

    // ==================================================
    // CLEAR
    // ==================================================

    private void clearForm() {

        txtName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtLoyalty.setText("");

        customerTable.clearSelection();

        txtName.requestFocus();
    }

    // ==================================================
    // VALUE
    // ==================================================

    private String valueAt(
            int row,
            int column
    ) {

        Object value =
                tableModel
                        .getValueAt(
                                row,
                                column
                        );

        return value == null
                ? ""
                : value.toString();
    }

    // ==================================================
    // BUTTON
    // ==================================================

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setBackground(
                background
        );

        button.setForeground(
                foreground
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setContentAreaFilled(
                true
        );

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
                                8,
                                14,
                                8,
                                14
                        )
                )
        );

        return button;
    }

    // ==================================================
    // WARNING
    // ==================================================

    private void warning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }
}