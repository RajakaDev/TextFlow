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

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    public DashboardFrame(User loggedInUser) {

        this.loggedInUser = loggedInUser;

        setTitle("TextFlow - Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createHeader();
        createDashboard();
    }

    // ==================================================
    // HEADER
    // ==================================================

    private void createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(DARK_BLUE);

        header.setBorder(
                new EmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        "TEXTFLOW  |  Main Dashboard"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel userLabel =
                new JLabel(
                        loggedInUser.getName()
                                + "  |  "
                                + loggedInUser.getRole()
                );

        userLabel.setForeground(Color.WHITE);

        userLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                userLabel,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );
    }

    // ==================================================
    // DASHBOARD
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
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        30,
                        40
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
                        22
                )
        );

        welcomeLabel.setForeground(
                DARK_BLUE
        );

        mainPanel.add(
                welcomeLabel,
                BorderLayout.NORTH
        );

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
                createButton(
                        "User Management"
                );

        JButton attendanceButton =
                createButton(
                        "My Attendance"
                );

        JButton productButton =
                createButton(
                        "Product & Inventory"
                );

        JButton salesButton =
                createButton(
                        "Sales & Billing"
                );

        JButton salesHistoryButton =
                createButton(
                        "Sales History"
                );

        JButton salesReportButton =
                createButton(
                        "Sales Reports"
                );

        JButton customerButton =
                createButton(
                        "Customer Management"
                );

        JButton financeButton =
                createButton(
                        "Finance / Expenses"
                );

        JButton supplierButton =
                createButton(
                        "Supplier & Purchase"
                );

        modulePanel.add(userButton);
        modulePanel.add(attendanceButton);
        modulePanel.add(productButton);

        modulePanel.add(salesButton);
        modulePanel.add(salesHistoryButton);
        modulePanel.add(salesReportButton);

        modulePanel.add(customerButton);
        modulePanel.add(financeButton);
        modulePanel.add(supplierButton);

        mainPanel.add(
                modulePanel,
                BorderLayout.CENTER
        );

        JButton logoutButton =
                new JButton(
                        "Logout"
                );

        logoutButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        logoutButton.setBackground(
                new Color(
                        254,
                        226,
                        226
                )
        );

        logoutButton.setForeground(
                new Color(
                        127,
                        29,
                        29
                )
        );

        logoutButton.setFocusPainted(false);

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setOpaque(false);

        bottomPanel.add(
                logoutButton
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
        // USER MANAGEMENT
        // ==================================================

        userButton.addActionListener(
                e -> {

                    UserManagementFrame frame =
                            new UserManagementFrame(
                                    loggedInUser
                            );

                    frame.setVisible(true);
                }
        );

        // ==================================================
        // ATTENDANCE
        // ==================================================

        attendanceButton.addActionListener(
                e -> {

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

                    frame.setVisible(true);
                }
        );

        // ==================================================
        // PRODUCT / INVENTORY
        // ==================================================

        productButton.addActionListener(
                e -> {

                    ProductInventoryFrame frame =
                            new ProductInventoryFrame();

                    frame.setVisible(true);
                }
        );

        // ==================================================
        // SALES
        // ==================================================

        salesButton.addActionListener(
                e -> {

                    SalesPanel panel =
                            new SalesPanel();

                    openPanel(
                            panel,
                            "TextFlow - Sales & Billing"
                    );
                }
        );

        // ==================================================
        // SALES HISTORY
        // ==================================================

        salesHistoryButton.addActionListener(
                e -> {

                    SalesHistoryPanel panel =
                            new SalesHistoryPanel();

                    openPanel(
                            panel,
                            "TextFlow - Sales History"
                    );
                }
        );

        // ==================================================
        // SALES REPORT
        // ==================================================

        salesReportButton.addActionListener(
                e -> {

                    SalesReportPanel panel =
                            new SalesReportPanel();

                    openPanel(
                            panel,
                            "TextFlow - Sales Reports"
                    );
                }
        );

        // ==================================================
        // CUSTOMER MANAGEMENT
        // ==================================================

        customerButton.addActionListener(
                e -> {

                    CustomerPanel panel =
                            new CustomerPanel();

                    openPanel(
                            panel,
                            "TextFlow - Customer Management"
                    );
                }
        );

        // ==================================================
        // FINANCE
        // ==================================================

        financeButton.addActionListener(
                e -> {

                    ExpenseUI frame =
                            new ExpenseUI(
                                    loggedInUser.getUserId()
                            );

                    frame.setVisible(true);
                }
        );

        // ==================================================
        // SUPPLIER & PURCHASE
        // ==================================================

        supplierButton.addActionListener(
                e -> {

                    openSupplierMenu();
                }
        );

        // ==================================================
        // LOGOUT
        // ==================================================

        logoutButton.addActionListener(
                e -> {

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
        );
    }

    // ==================================================
    // SUPPLIER MENU
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
                        "Select an option:",
                        "Supplier & Purchase",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (choice == 0) {

            new SupplierManagementFrame()
                    .setVisible(true);

        } else if (choice == 1) {

            new PurchaseManagementFrame(
                    loggedInUser.getUserId()
            ).setVisible(true);
        }
    }

    // ==================================================
    // OPEN JPANEL IN FRAME
    // ==================================================

    private void openPanel(
            JPanel panel,
            String title
    ) {

        JFrame frame =
                new JFrame(title);

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(
                1050,
                700
        );

        frame.setLocationRelativeTo(
                this
        );

        frame.setContentPane(
                panel
        );

        frame.setVisible(true);
    }

    // ==================================================
    // BUTTON STYLE
    // ==================================================

    private JButton createButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        button.setBackground(
                Color.WHITE
        );

        button.setForeground(
                DARK_BLUE
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        215,
                                        220,
                                        230
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                15,
                                20,
                                15
                        )
                )
        );

        return button;
    }
}