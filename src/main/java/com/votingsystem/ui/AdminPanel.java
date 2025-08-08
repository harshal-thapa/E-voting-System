package com.votingsystem.ui;

import com.votingsystem.dao.CandidateDAO;
import com.votingsystem.model.Candidate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JFrame {
    private CandidateDAO cdao = new CandidateDAO();

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel top = new JPanel(new FlowLayout());
        JTextField name = new JTextField(10);
        JTextField party = new JTextField(10);
        JButton add = new JButton("Add Candidate");
        add.addActionListener(e -> {
            try {
                cdao.addCandidate(name.getText().trim(), party.getText().trim());
                JOptionPane.showMessageDialog(this, "Added");
                refreshResults();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        top.add(new JLabel("Name:")); top.add(name); top.add(new JLabel("Party:")); top.add(party); top.add(add);

        add(top, BorderLayout.NORTH);
        refreshResults();
    }

    private void refreshResults() {
        try {
            List<Candidate> list = cdao.listAll();
            String[] cols = {"ID","Name","Party","Votes"};
            DefaultTableModel model = new DefaultTableModel(cols,0);
            for (Candidate c : list) {
                model.addRow(new Object[]{c.getCandidateId(), c.getName(), c.getParty(), c.getVotes()});
            }
            JTable table = new JTable(model);
            add(new JScrollPane(table), BorderLayout.CENTER);
            revalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
