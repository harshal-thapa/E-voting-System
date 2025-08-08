package com.votingsystem.dao;

import com.votingsystem.model.Candidate;
import com.votingsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO {

    public void addCandidate(String name, String party) throws SQLException {
        String sql = "INSERT INTO candidates (name, party) VALUES (?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, party);
            ps.executeUpdate();
        }
    }

    public List<Candidate> listAll() throws SQLException {
        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT candidate_id, name, party, votes FROM candidates ORDER BY candidate_id";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Candidate cand = new Candidate();
                cand.setCandidateId(rs.getInt("candidate_id"));
                cand.setName(rs.getString("name"));
                cand.setParty(rs.getString("party"));
                cand.setVotes(rs.getInt("votes"));
                list.add(cand);
            }
        }
        return list;
    }
}
