package com.votingsystem.dao;

import com.votingsystem.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class AdminDAO {
    public void registerAdmin(String username, String rawPassword) throws SQLException {
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashed);
            ps.executeUpdate();
        }
    }

    public boolean authenticate(String username, String rawPassword) throws SQLException {
        String q = "SELECT password FROM admin WHERE username = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String stored = rs.getString("password");
                    return BCrypt.checkpw(rawPassword, stored);
                }
            }
        }
        return false;
    }
}
