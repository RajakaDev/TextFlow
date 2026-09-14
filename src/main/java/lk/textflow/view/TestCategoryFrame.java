package lk.textflow.view;

import javax.swing.*;

public class TestCategoryFrame extends JFrame {

    public TestCategoryFrame() {

        setTitle(
                "TextFlow - Category Management"
        );

        setSize(
                900,
                600
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        add(
                new CategoryPanel()
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    TestCategoryFrame frame =
                            new TestCategoryFrame();

                    frame.setVisible(true);
                }
        );
    }
}