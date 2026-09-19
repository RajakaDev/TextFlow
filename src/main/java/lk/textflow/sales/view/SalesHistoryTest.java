package lk.textflow.sales.view;

import javax.swing.*;

public class SalesHistoryTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame("TextFlow - Sales History");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.add(new SalesHistoryPanel());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}