package com.votingsystem;

import com.votingsystem.ui.LoginFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "setup".equalsIgnoreCase(args[0])) {
            com.votingsystem.Setup.runSetup();
            System.out.println("Setup completed.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame lf = new LoginFrame();
            lf.setVisible(true);
        });
    }
}
