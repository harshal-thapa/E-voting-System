package com.votingsystem.dao;

import com.votingsystem.model.Voter;
import com.votingsystem.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class VoterDAO {

    public boolean register(Voter v, String rawPassword) throws SQLException {
        String insert = "INSERT INTO voters (name, username, password) VALUES (?, ?, ?)";
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setString(1, v.getName());
            ps.setString(2, v.getUsername());
            ps.setString(3, hashed);
            ps.executeUpdate();
            return true;
        }
    }

    public Voter authenticate(String username, String rawPassword) throws SQLException {
        String q = "SELECT voter_id, name, password, has_voted FROM voters WHERE username = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (BCrypt.checkpw(rawPassword, storedHash)) {
                        Voter v = new Voter();
                        v.setVoterId(rs.getInt("voter_id"));
                        v.setName(rs.getString("name"));
                        v.setUsername(username);
                        v.setHasVoted(rs.getBoolean("has_voted"));
                        return v;
                    }
                }
                return null;
            }
        }
    }
}
