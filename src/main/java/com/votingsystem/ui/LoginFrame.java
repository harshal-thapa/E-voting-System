package com.votingsystem.ui;

import com.votingsystem.dao.AdminDAO;
import com.votingsystem.dao.VoterDAO;
import com.votingsystem.model.Voter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public LoginFrame() {
        setTitle("E-Voting Login");
        setSize(360,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new GridLayout(4,2,6,6));
        p.add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[] {"Voter", "Admin"});
        p.add(roleBox);

        p.add(new JLabel("Username:"));
        usernameField = new JTextField();
        p.add(usernameField);

        p.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        p.add(passwordField);

        JButton login = new JButton("Login");
        login.addActionListener(this::onLogin);
        p.add(login);

        JButton reg = new JButton("Register (Voter)");
        reg.addActionListener(e -> {
            new RegistrationFrame().setVisible(true);
        });
        p.add(reg);

        add(p, BorderLayout.CENTER);
    }

    private void onLogin(ActionEvent ev) {
        String role = (String) roleBox.getSelectedItem();
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());
        try {
            if ("Admin".equals(role)) {
                AdminDAO dao = new AdminDAO();
                if (dao.authenticate(user, pass)) {
                    JOptionPane.showMessageDialog(this, "Admin login OK");
                    new AdminPanel().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Bad admin credentials");
                }
            } else {
                VoterDAO dao = new VoterDAO();
                Voter v = dao.authenticate(user, pass);
                if (v != null) {
                    if (v.isHasVoted()) {
                        JOptionPane.showMessageDialog(this, "You already voted.");
                    } else {
                        new VotingFrame(v).setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bad voter credentials");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
