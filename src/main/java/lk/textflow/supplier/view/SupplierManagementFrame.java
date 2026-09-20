package lk.textflow.supplier.view;

import lk.textflow.supplier.dao.SupplierDAO;
import lk.textflow.supplier.model.Supplier;

import javax.swing.*;
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

    public SupplierManagementFrame() {

        supplierDAO =
                new SupplierDAO();

        setTitle(
                "TextFlow - Supplier Management"
        );

        setSize(
                950,
                650
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadSuppliers();
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Supplier Management"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );

        nameField =
                new JTextField();

        contactField =
                new JTextField();

        addressField =
                new JTextField();

        emailField =
                new JTextField();

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );

        formPanel.add(
                new JLabel(
                        "Supplier Name:"
                )
        );

        formPanel.add(
                nameField
        );

        formPanel.add(
                new JLabel(
                        "Contact Number:"
                )
        );

        formPanel.add(
                contactField
        );

        formPanel.add(
                new JLabel(
                        "Address:"
                )
        );

        formPanel.add(
                addressField
        );

        formPanel.add(
                new JLabel(
                        "Email:"
                )
        );

        formPanel.add(
                emailField
        );

        formPanel.add(
                new JLabel(
                        "Status:"
                )
        );

        formPanel.add(
                statusComboBox
        );

        JButton addButton =
                new JButton(
                        "Add Supplier"
                );

        JButton updateButton =
                new JButton(
                        "Update"
                );

        JButton clearButton =
                new JButton(
                        "Clear"
                );

        JButton purchaseButton =
                new JButton(
                        "Purchases"
                );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                addButton
        );

        buttonPanel.add(
                updateButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                purchaseButton
        );

        buttonPanel.add(
                closeButton
        );

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        topPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Supplier",
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
                new JTable(
                        tableModel
                );

        supplierTable.setRowHeight(
                25
        );

        supplierTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        mainPanel.add(
                new JScrollPane(
                        supplierTable
                ),
                BorderLayout.CENTER
        );

        addButton.addActionListener(
                e -> addSupplier()
        );

        updateButton.addActionListener(
                e -> updateSupplier()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        purchaseButton.addActionListener(
                e -> new PurchaseManagementFrame()
                        .setVisible(true)
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        supplierTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (
                                    !e.getValueIsAdjusting()
                            ) {

                                loadSelectedSupplier();
                            }
                        }
                );

        add(
                mainPanel
        );
    }

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

            message(
                    "Supplier name is required."
            );

            return;
        }

        if (
                !contact.isEmpty()
                        && !contact.matches(
                        "\\d{10}"
                )
        ) {

            message(
                    "Contact number must contain 10 digits."
            );

            return;
        }

        if (
                !email.isEmpty()
                        && !email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )
        ) {

            message(
                    "Please enter a valid email."
            );

            return;
        }

        Supplier supplier =
                new Supplier();

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
                status
        );

        if (
                supplierDAO.addSupplier(
                        supplier
                )
        ) {

            message(
                    "Supplier added successfully."
            );

            clearForm();
            loadSuppliers();

        } else {

            message(
                    "Supplier could not be added."
            );
        }
    }

    private void updateSupplier() {

        int row =
                supplierTable
                        .getSelectedRow();

        if (row == -1) {

            message(
                    "Please select a supplier."
            );

            return;
        }

        String name =
                nameField
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            message(
                    "Supplier name is required."
            );

            return;
        }

        String contact =
                contactField
                        .getText()
                        .trim();

        if (
                !contact.isEmpty()
                        && !contact.matches(
                        "\\d{10}"
                )
        ) {

            message(
                    "Contact number must contain 10 digits."
            );

            return;
        }

        String email =
                emailField
                        .getText()
                        .trim();

        if (
                !email.isEmpty()
                        && !email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )
        ) {

            message(
                    "Please enter a valid email."
            );

            return;
        }

        int supplierId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
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
                addressField
                        .getText()
                        .trim()
        );

        supplier.setEmail(
                email
        );

        supplier.setStatus(
                statusComboBox
                        .getSelectedItem()
                        .toString()
        );

        if (
                supplierDAO.updateSupplier(
                        supplier
                )
        ) {

            message(
                    "Supplier updated successfully."
            );

            clearForm();
            loadSuppliers();

        } else {

            message(
                    "Supplier could not be updated."
            );
        }
    }

    private void loadSelectedSupplier() {

        int row =
                supplierTable
                        .getSelectedRow();

        if (row == -1) {
            return;
        }

        nameField.setText(
                value(row, 1)
        );

        contactField.setText(
                value(row, 2)
        );

        addressField.setText(
                value(row, 3)
        );

        emailField.setText(
                value(row, 4)
        );

        statusComboBox.setSelectedItem(
                value(row, 5)
        );
    }

    private void loadSuppliers() {

        tableModel.setRowCount(
                0
        );

        List<Supplier> suppliers =
                supplierDAO
                        .getAllSuppliers();

        for (
                Supplier supplier
                : suppliers
        ) {

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

    private void clearForm() {

        nameField.setText("");
        contactField.setText("");
        addressField.setText("");
        emailField.setText("");

        statusComboBox.setSelectedItem(
                "ACTIVE"
        );

        supplierTable.clearSelection();
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

    private void message(
            String text
    ) {

        JOptionPane.showMessageDialog(
                this,
                text
        );
    }

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new SupplierManagementFrame()
                        .setVisible(true)
        );
    }
}