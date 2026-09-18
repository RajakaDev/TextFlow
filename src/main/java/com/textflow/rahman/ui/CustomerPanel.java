package com.textflow.rahman.ui;

import com.textflow.rahman.model.Customer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPanel extends JPanel {

    // Customer data stored temporarily in memory
    private final List<Customer> customers = new ArrayList<>();

    // ID for the next customer
    private int nextCustomerId = 1;

    // Input fields
    private JTextField txtName;
    private JTextField txtContact;

    private JTextField txtAddress;
    private JTextField txtLoyalty;
    private JTextField txtSearch;

    // Table
    private JTable customerTable;
    private DefaultTableModel tableModel;

    // Buttons
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnDeactivate;

    public CustomerPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createHeader();
        createForm();
        createTable();

        // Add a couple of sample customers
        addSampleCustomers();

        refreshTable();
    }

    // ---------------------------------------------------------
    // HEADER
    // ---------------------------------------------------------

    private void createHeader() {

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel subtitleLabel = new JLabel(
                "Add, view, update, search and manage customers"
        );
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(245, 247, 250));

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);
    }

    // ---------------------------------------------------------
    // CUSTOMER FORM
    // ---------------------------------------------------------

    private void createForm() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 224, 230)),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        formPanel.add(new JLabel("Customer Name"), gbc);

        txtName = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(txtName, gbc);

        // Contact Number
        gbc.gridx = 2;
        gbc.weightx = 0;

        formPanel.add(new JLabel("Contact Number"), gbc);

        txtContact = new JTextField();
        gbc.gridx = 3;
        gbc.weightx = 1;

        formPanel.add(txtContact, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        formPanel.add(new JLabel("Address"), gbc);

        txtAddress = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(txtAddress, gbc);

        // Loyalty Points
        gbc.gridx = 2;
        gbc.weightx = 0;

        formPanel.add(new JLabel("Loyalty Points"), gbc);

        txtLoyalty = new JTextField();
        gbc.gridx = 3;
        gbc.weightx = 1;

        formPanel.add(txtLoyalty, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");
        btnDeactivate = new JButton("DEACTIVATE");
        btnClear = new JButton("CLEAR");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnDeactivate);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1;

        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnDeactivate.addActionListener(e -> deactivateCustomer());
        btnClear.addActionListener(e -> clearForm());
    }

    // ---------------------------------------------------------
    // CUSTOMER TABLE
    // ---------------------------------------------------------

    private void createTable() {

        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(new Color(245, 247, 250));

        // Search section
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(new Color(245, 247, 250));

        JLabel searchLabel = new JLabel("Search:");

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 32));

        JButton btnSearch = new JButton("SEARCH");

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        tablePanel.add(searchPanel, BorderLayout.NORTH);

        // Table columns
        String[] columns = {
                "ID",
                "Customer Name",
                "Contact Number",
                "Address",
                "Loyalty Points",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        customerTable = new JTable(tableModel);

        customerTable.setRowHeight(28);
        customerTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        customerTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        customerTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(customerTable);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Search button
        btnSearch.addActionListener(e -> searchCustomers());

        // Pressing Enter in search field also searches
        txtSearch.addActionListener(e -> searchCustomers());

        // Live search while typing
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchCustomers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchCustomers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchCustomers();
            }
        });

        // When a table row is selected, load its data into the form
        customerTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                loadSelectedCustomer();
            }
        });

        add(tablePanel, BorderLayout.SOUTH);

        // Give the table more space
        tablePanel.setPreferredSize(new Dimension(0, 320));
    }

    // ---------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------

    private void addCustomer() {

        String name = txtName.getText().trim();
        String contact = txtContact.getText().trim();
        String address = txtAddress.getText().trim();
        String loyaltyText = txtLoyalty.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the customer name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int loyaltyPoints;

        try {

            loyaltyPoints = loyaltyText.isEmpty()
                    ? 0
                    : Integer.parseInt(loyaltyText);

            if (loyaltyPoints < 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Loyalty points must be a valid positive number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Customer customer = new Customer(
                nextCustomerId++,
                name,
                contact,
                address,
                loyaltyPoints,
                "ACTIVE"
        );

        customers.add(customer);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(
                this,
                "Customer added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ---------------------------------------------------------
    // READ
    // ---------------------------------------------------------

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Customer customer : customers) {

            tableModel.addRow(new Object[]{
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getContactNumber(),
                    customer.getAddress(),
                    customer.getLoyaltyPoints(),
                    customer.getStatus()
            });
        }
    }

    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------

    private void updateCustomer() {

        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer to update.",
                    "No Customer Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);

        Customer customer = findCustomerById(customerId);

        if (customer == null) {
            return;
        }

        String name = txtName.getText().trim();
        String contact = txtContact.getText().trim();
        String address = txtAddress.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer name cannot be empty.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int loyaltyPoints;

        try {

            loyaltyPoints = txtLoyalty.getText().trim().isEmpty()
                    ? 0
                    : Integer.parseInt(txtLoyalty.getText().trim());

            if (loyaltyPoints < 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Loyalty points must be a valid positive number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        customer.setCustomerName(name);
        customer.setContactNumber(contact);
        customer.setAddress(address);
        customer.setLoyaltyPoints(loyaltyPoints);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(
                this,
                "Customer updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------

    private void deleteCustomer() {

        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer to delete.",
                    "No Customer Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this customer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            Customer customer = findCustomerById(customerId);

            if (customer != null) {
                customers.remove(customer);
            }

            refreshTable();
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ---------------------------------------------------------
    // DEACTIVATE
    // ---------------------------------------------------------

    private void deactivateCustomer() {

        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer to deactivate.",
                    "No Customer Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);

        Customer customer = findCustomerById(customerId);

        if (customer != null) {

            customer.setStatus("INACTIVE");

            refreshTable();
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer deactivated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ---------------------------------------------------------
    // SEARCH
    // ---------------------------------------------------------

    private void searchCustomers() {

        String keyword = txtSearch.getText()
                .trim()
                .toLowerCase();

        tableModel.setRowCount(0);

        for (Customer customer : customers) {

            boolean matches =
                    String.valueOf(customer.getCustomerId()).contains(keyword)
                            || customer.getCustomerName().toLowerCase().contains(keyword)
                            || customer.getContactNumber().toLowerCase().contains(keyword)
                            || customer.getAddress().toLowerCase().contains(keyword)
                            || customer.getStatus().toLowerCase().contains(keyword);

            if (matches) {

                tableModel.addRow(new Object[]{
                        customer.getCustomerId(),
                        customer.getCustomerName(),
                        customer.getContactNumber(),
                        customer.getAddress(),
                        customer.getLoyaltyPoints(),
                        customer.getStatus()
                });
            }
        }
    }

    // ---------------------------------------------------------
    // LOAD SELECTED CUSTOMER
    // ---------------------------------------------------------

    private void loadSelectedCustomer() {

        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        txtName.setText(
                tableModel.getValueAt(selectedRow, 1).toString()
        );

        txtContact.setText(
                tableModel.getValueAt(selectedRow, 2).toString()
        );

        txtAddress.setText(
                tableModel.getValueAt(selectedRow, 3).toString()
        );

        txtLoyalty.setText(
                tableModel.getValueAt(selectedRow, 4).toString()
        );
    }

    // ---------------------------------------------------------
    // FIND CUSTOMER
    // ---------------------------------------------------------

    private Customer findCustomerById(int id) {

        for (Customer customer : customers) {

            if (customer.getCustomerId() == id) {
                return customer;
            }
        }

        return null;
    }

    // ---------------------------------------------------------
    // CLEAR FORM
    // ---------------------------------------------------------

    private void clearForm() {

        txtName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtLoyalty.setText("");

        customerTable.clearSelection();
    }

    // ---------------------------------------------------------
    // SAMPLE DATA
    // ---------------------------------------------------------

    private void addSampleCustomers() {

        customers.add(
                new Customer(
                        nextCustomerId++,
                        "Ahmed Perera",
                        "0771234567",
                        "Colombo",
                        120,
                        "ACTIVE"
                )
        );

        customers.add(
                new Customer(
                        nextCustomerId++,
                        "Sarah Fernando",
                        "0719876543",
                        "Negombo",
                        250,
                        "ACTIVE"
                )
        );
    }
}