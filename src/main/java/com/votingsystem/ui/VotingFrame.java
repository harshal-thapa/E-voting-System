package com.votingsystem.ui;

import com.votingsystem.dao.CandidateDAO;
import com.votingsystem.dao.VotingDAO;
import com.votingsystem.model.Candidate;
import com.votingsystem.model.Voter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VotingFrame extends JFrame {
    private Voter voter;
    private JList<String> list;
    private List<Candidate> candidates;

    public VotingFrame(Voter v) {
        this.voter = v;
        setTitle("Cast your vote - " + v.getName());
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        try {
            CandidateDAO cdao = new CandidateDAO();
            candidates = cdao.listAll();
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Candidate c : candidates) model.addElement(c.getName() + " (" + c.getParty() + ")");
            list = new JList<>(model);
            add(new JScrollPane(list), BorderLayout.CENTER);

            JButton voteBtn = new JButton("Vote");
            voteBtn.addActionListener(e -> onVote());
            add(voteBtn, BorderLayout.SOUTH);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading candidates: " + e.getMessage());
        }
    }

    private void onVote() {
        int idx = list.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a candidate first");
            return;
        }
        try {
            VotingDAO vdao = new VotingDAO();
            int candidateId = candidates.get(idx).getCandidateId();
            boolean ok = vdao.castVote(voter.getVoterId(), candidateId);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Vote recorded. Thank you!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Could not record vote. Maybe you already voted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
