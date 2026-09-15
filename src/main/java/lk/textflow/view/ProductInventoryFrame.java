package lk.textflow.view;

import javax.swing.*;
import java.awt.*;

public class ProductInventoryFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public ProductInventoryFrame() {

        // Window settings
        setTitle("TextFlow - Product & Inventory Management");

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        createUI();
    }

    private void createUI() {

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TextFlow - Product & Inventory Management"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Manage categories, products and inventory"
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.add(titleLabel);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitleLabel);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // MAIN TABS
        // =========================

        tabbedPane =
                new JTabbedPane();

        tabbedPane.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        // Category Management
        CategoryPanel categoryPanel =
                new CategoryPanel();

        // Product Management
        ProductPanel productPanel =
                new ProductPanel();

        // Inventory Management
        InventoryPanel inventoryPanel =
                new InventoryPanel();

        tabbedPane.addTab(
                "Categories",
                categoryPanel
        );

        tabbedPane.addTab(
                "Products",
                productPanel
        );

        tabbedPane.addTab(
                "Inventory",
                inventoryPanel
        );

// ======================================
// AUTO REFRESH WHEN CHANGING TABS
// ======================================
        tabbedPane.addChangeListener(e -> {

            int selectedIndex =
                    tabbedPane.getSelectedIndex();

            // Products tab selected
            if (selectedIndex == 1) {

                productPanel.refreshData();
            }

            // Inventory tab selected
            if (selectedIndex == 2) {

                inventoryPanel.refreshData();
            }
        });


        add(
                tabbedPane,
                BorderLayout.CENTER
        );
        // =========================
        // FOOTER
        // =========================

        JPanel footerPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        footerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        JLabel footerLabel =
                new JLabel(
                        "TextFlow POS System"
                );

        footerPanel.add(
                footerLabel
        );

        add(
                footerPanel,
                BorderLayout.SOUTH
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    ProductInventoryFrame frame =
                            new ProductInventoryFrame();

                    frame.setVisible(true);
                }
        );
    }
}