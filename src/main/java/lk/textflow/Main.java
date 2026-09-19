package lk.textflow;

import lk.textflow.ui.LoginFrame;
import lk.textflow.util.DefaultOwnerSetup;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        DefaultOwnerSetup.ensureOwnerExists();

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);

        });
    }
}