package lk.textflow.sales.view;

import javax.swing.*;

public class SalesTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame("TextFlow - Sales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        frame.add(new SalesPanel());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}