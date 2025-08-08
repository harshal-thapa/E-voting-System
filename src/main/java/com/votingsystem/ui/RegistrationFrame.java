package com.votingsystem.ui;

import com.votingsystem.dao.VoterDAO;
import com.votingsystem.model.Voter;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {
    public RegistrationFrame() {
        setTitle("Voter Registration");
        setSize(360,220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel p = new JPanel(new GridLayout(4,2,6,6));
        JTextField name = new JTextField();
        JTextField username = new JTextField();
        JPasswordField pw = new JPasswordField();

        p.add(new JLabel("Name:"));
        p.add(name);
        p.add(new JLabel("Username:"));
        p.add(username);
        p.add(new JLabel("Password:"));
        p.add(pw);

        JButton register = new JButton("Register");
        register.addActionListener(e -> {
            try {
                Voter v = new Voter();
                v.setName(name.getText().trim());
                v.setUsername(username.getText().trim());
                String raw = new String(pw.getPassword());
                VoterDAO dao = new VoterDAO();
                dao.register(v, raw);
                JOptionPane.showMessageDialog(this, "Registered. You can login now.");
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        p.add(register);
        add(p, BorderLayout.CENTER);
    }
}
