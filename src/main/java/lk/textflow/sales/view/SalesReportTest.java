package lk.textflow.sales.view;

import javax.swing.*;

public class SalesReportTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame("TextFlow - Sales Report");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.add(new SalesReportPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}