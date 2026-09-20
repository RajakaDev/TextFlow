package lk.textflow.sales.view;

import javax.swing.*;

public class SalesReturnTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame("TextFlow - Sales Return / Refund");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.add(new SalesReturnPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}