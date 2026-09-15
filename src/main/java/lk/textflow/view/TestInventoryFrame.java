package lk.textflow.view;

import javax.swing.*;

public class TestInventoryFrame extends JFrame {

    public TestInventoryFrame() {

        setTitle(
                "TextFlow - Inventory Management"
        );

        setSize(1100, 700);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        add(
                new InventoryPanel()
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    TestInventoryFrame frame =
                            new TestInventoryFrame();

                    frame.setVisible(true);
                }
        );
    }
}