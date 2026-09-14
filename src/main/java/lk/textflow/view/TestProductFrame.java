package lk.textflow.view;

import javax.swing.*;

public class TestProductFrame extends JFrame {

    public TestProductFrame() {

        setTitle(
                "TextFlow - Product Management"
        );

        setSize(
                1200,
                800
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        add(
                new ProductPanel()
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    TestProductFrame frame =
                            new TestProductFrame();

                    frame.setVisible(true);
                }
        );
    }
}