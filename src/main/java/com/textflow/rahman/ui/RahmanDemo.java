package com.textflow.rahman.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class RahmanDemo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("TextFlow - Customer Management");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(new CustomerPanel());

            frame.setSize(1000, 650);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }
}