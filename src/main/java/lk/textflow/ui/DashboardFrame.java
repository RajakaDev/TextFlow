package lk.textflow.ui;

import com.textflow.rahman.ui.CustomerPanel;
import com.textflow.ui.ExpenseUI;

import lk.textflow.model.User;
import lk.textflow.sales.view.SalesHistoryPanel;
import lk.textflow.sales.view.SalesPanel;
import lk.textflow.sales.view.SalesReportPanel;
import lk.textflow.supplier.view.PurchaseManagementFrame;
import lk.textflow.supplier.view.SupplierManagementFrame;
import lk.textflow.view.ProductInventoryFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final User loggedInUser;

    // ==================================================
    // COLORS
    // ==================================================

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color LIGHT_BACKGROUND =
            new Color(245, 247, 250);

    private final Color CARD_BORDER =
            new Color(220, 225, 230);

    private final Color TEXT_DARK =
            new Color(55, 65, 81);

    public DashboardFrame(
            User loggedInUser
    ) {

        this.loggedInUser =
                loggedInUser;

        setTitle(
                "TextFlow - Dashboard"
        );

        setSize(
                1180,
                760
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout()
        );

        createHeader();
        createDashboard();
    }

    // ==================================================
    // HEADER
    // ==================================================

    private void createHeader() {

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                DARK_BLUE
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        18,
                        30,
                        18,
                        30
                )
        );

        // LEFT SIDE
        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW"
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
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
                        13
                )
        );

        titlePanel.add(
                titleLabel
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        titlePanel.add(
                subtitleLabel
        );

        // RIGHT SIDE
        JPanel userPanel =
                new JPanel();

        userPanel.setOpaque(false);

        userPanel.setLayout(
                new BoxLayout(
                        userPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel userNameLabel =
                new JLabel(
                        loggedInUser.getName()
                );

        userNameLabel.setForeground(
                Color.WHITE
        );

        userNameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        userNameLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel roleLabel =
                new JLabel(
                        loggedInUser.getRole()
                );

        roleLabel.setForeground(
                new Color(
                        220,
                        230,
                        245
                )
        );

        roleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        roleLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        userPanel.add(
                userNameLabel
        );

        userPanel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        userPanel.add(
                roleLabel
        );

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                userPanel,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );
    }

    // ==================================================
    // MAIN DASHBOARD
    // ==================================================

    private void createDashboard() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBackground(
                LIGHT_BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        // ==================================================
        // WELCOME CARD
        // ==================================================

        JPanel welcomeCard =
                new JPanel(
                        new BorderLayout()
                );

        welcomeCard.setBackground(
                Color.WHITE
        );

        welcomeCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                18,
                                22,
                                18,
                                22
                        )
                )
        );

        JPanel welcomeTextPanel =
                new JPanel();

        welcomeTextPanel.setOpaque(false);

        welcomeTextPanel.setLayout(
                new BoxLayout(
                        welcomeTextPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + loggedInUser.getName()
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        welcomeLabel.setForeground(
                DARK_BLUE
        );

        JLabel descriptionLabel =
                new JLabel(
                        "Select a module below to continue."
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        descriptionLabel.setForeground(
                TEXT_DARK
        );

        welcomeTextPanel.add(
                welcomeLabel
        );

        welcomeTextPanel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        welcomeTextPanel.add(
                descriptionLabel
        );

        welcomeCard.add(
                welcomeTextPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                welcomeCard,
                BorderLayout.NORTH
        );

        // ==================================================
        // MODULE CARDS
        // ==================================================

        JPanel modulePanel =
                new JPanel(
                        new GridLayout(
                                3,
                                3,
                                18,
                                18
                        )
                );

        modulePanel.setOpaque(false);

        JButton userButton =
                createModuleButton(
                        "User Management",
                        "Manage system users and accounts",
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
                createModuleButton(
                        "Attendance",
                        "View and manage attendance",
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

        JButton productButton =
                createModuleButton(
                        "Product & Inventory",
                        "Products, categories and stock",
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

        JButton salesButton =
                createModuleButton(
                        "Sales & Billing",
                        "Create sales and customer bills",
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

        JButton salesHistoryButton =
                createModuleButton(
                        "Sales History",
                        "Search previous sales records",
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

        JButton salesReportButton =
                createModuleButton(
                        "Sales Reports",
                        "View sales summaries and reports",
                        new Color(
                                255,
                                237,
                                213
                        ),
                        new Color(
                                154,
                                52,
                                18
                        )
                );

        JButton customerButton =
                createModuleButton(
                        "Customer Management",
                        "Manage customer information",
                        new Color(
                                224,
                                242,
                                254
                        ),
                        new Color(
                                3,
                                105,
                                161
                        )
                );

        JButton financeButton =
                createModuleButton(
                        "Finance / Expenses",
                        "Manage expenses and categories",
                        new Color(
                                243,
                                232,
                                255
                        ),
                        new Color(
                                107,
                                33,
                                168
                        )
                );

        JButton supplierButton =
                createModuleButton(
                        "Supplier & Purchase",
                        "Suppliers, purchases and stock-in",
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

        modulePanel.add(
                userButton
        );

        modulePanel.add(
                attendanceButton
        );

        modulePanel.add(
                productButton
        );

        modulePanel.add(
                salesButton
        );

        modulePanel.add(
                salesHistoryButton
        );

        modulePanel.add(
                salesReportButton
        );

        modulePanel.add(
                customerButton
        );

        modulePanel.add(
                financeButton
        );

        modulePanel.add(
                supplierButton
        );

        mainPanel.add(
                modulePanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // BOTTOM BAR
        // ==================================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        JLabel statusLabel =
                new JLabel(
                        "Logged in as "
                                + loggedInUser.getUsername()
                );

        statusLabel.setForeground(
                new Color(
                        107,
                        114,
                        128
                )
        );

        statusLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        JButton logoutButton =
                createSmallButton(
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

        bottomPanel.add(
                statusLabel,
                BorderLayout.WEST
        );

        bottomPanel.add(
                logoutButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // ACTIONS
        // ==================================================

        userButton.addActionListener(
                e -> openUserManagement()
        );

        attendanceButton.addActionListener(
                e -> openAttendance()
        );

        productButton.addActionListener(
                e -> openProductInventory()
        );

        salesButton.addActionListener(
                e -> openSales()
        );

        salesHistoryButton.addActionListener(
                e -> openSalesHistory()
        );

        salesReportButton.addActionListener(
                e -> openSalesReport()
        );

        customerButton.addActionListener(
                e -> openCustomerManagement()
        );

        financeButton.addActionListener(
                e -> openFinance()
        );

        supplierButton.addActionListener(
                e -> openSupplierMenu()
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    // ==================================================
    // USER MANAGEMENT
    // ==================================================

    private void openUserManagement() {

        UserManagementFrame frame =
                new UserManagementFrame(
                        loggedInUser
                );

        frame.setVisible(
                true
        );
    }

    // ==================================================
    // ATTENDANCE
    // ==================================================

    private void openAttendance() {

        boolean canManage =
                "OWNER".equalsIgnoreCase(
                        loggedInUser.getRole()
                )
                        ||
                        "MANAGER".equalsIgnoreCase(
                                loggedInUser.getRole()
                        );

        AttendanceManagementFrame frame =
                new AttendanceManagementFrame(
                        loggedInUser.getUserId(),
                        canManage
                );

        frame.setVisible(
                true
        );
    }

    // ==================================================
    // PRODUCT / INVENTORY
    // ==================================================

    private void openProductInventory() {

        ProductInventoryFrame frame =
                new ProductInventoryFrame();

        frame.setVisible(
                true
        );
    }

    // ==================================================
    // SALES
    // ==================================================

    private void openSales() {

        SalesPanel panel =
                new SalesPanel(
                        loggedInUser.getUserId()
                );

        openPanel(
                panel,
                "TextFlow - Sales & Billing"
        );
    }

    // ==================================================
    // SALES HISTORY
    // ==================================================

    private void openSalesHistory() {

        SalesHistoryPanel panel =
                new SalesHistoryPanel();

        openPanel(
                panel,
                "TextFlow - Sales History"
        );
    }

    // ==================================================
    // SALES REPORT
    // ==================================================

    private void openSalesReport() {

        SalesReportPanel panel =
                new SalesReportPanel();

        openPanel(
                panel,
                "TextFlow - Sales Reports"
        );
    }

    // ==================================================
    // CUSTOMER
    // ==================================================

    private void openCustomerManagement() {

        CustomerPanel panel =
                new CustomerPanel();

        openPanel(
                panel,
                "TextFlow - Customer Management"
        );
    }

    // ==================================================
    // FINANCE
    // ==================================================

    private void openFinance() {

        ExpenseUI frame =
                new ExpenseUI(
                        loggedInUser.getUserId()
                );

        frame.setVisible(
                true
        );
    }

    // ==================================================
    // SUPPLIER & PURCHASE
    // ==================================================

    private void openSupplierMenu() {

        String[] options = {
                "Supplier Management",
                "Purchase Management",
                "Cancel"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Select Supplier & Purchase option:",
                        "Supplier & Purchase",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (
                choice == 0
        ) {

            new SupplierManagementFrame(
                    loggedInUser.getUserId()
            ).setVisible(true);

        } else if (
                choice == 1
        ) {

            new PurchaseManagementFrame(
                    loggedInUser.getUserId()
            ).setVisible(true);
        }
    }

    // ==================================================
    // LOGOUT
    // ==================================================

    private void logout() {

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Logout from TextFlow?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        == JOptionPane.YES_OPTION
        ) {

            new LoginFrame()
                    .setVisible(true);

            dispose();
        }
    }

    // ==================================================
    // OPEN JPANEL
    // ==================================================

    private void openPanel(
            JPanel panel,
            String title
    ) {

        JFrame frame =
                new JFrame(
                        title
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(
                1100,
                720
        );

        frame.setMinimumSize(
                new Dimension(
                        900,
                        600
                )
        );

        frame.setLocationRelativeTo(
                this
        );

        frame.setContentPane(
                panel
        );

        frame.setVisible(
                true
        );
    }

    // ==================================================
    // MODULE BUTTON
    // ==================================================

    private JButton createModuleButton(
            String title,
            String description,
            Color backgroundColor,
            Color textColor
    ) {

        String text =
                "<html>"
                        + "<div style='text-align:center;'>"
                        + "<b style='font-size:14px;'>"
                        + title
                        + "</b>"
                        + "<br><br>"
                        + "<span style='font-size:10px;'>"
                        + description
                        + "</span>"
                        + "</div>"
                        + "</html>";

        JButton button =
                new JButton(
                        text
                );

        button.setBackground(
                backgroundColor
        );

        button.setForeground(
                textColor
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
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
                                backgroundColor.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                18,
                                15,
                                18,
                                15
                        )
                )
        );

        return button;
    }

    // ==================================================
    // SMALL BUTTON
    // ==================================================

    private JButton createSmallButton(
            String text,
            Color backgroundColor,
            Color textColor
    ) {

        JButton button =
                new JButton(
                        text
                );

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

        button.setFocusPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                backgroundColor.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                18,
                                8,
                                18
                        )
                )
        );

        return button;
    }
}