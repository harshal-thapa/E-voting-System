package com.votingsystem.dao;

import com.votingsystem.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VotingDAO {

    public boolean castVote(int voterId, int candidateId) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            try {
                c.setAutoCommit(false);

                String check = "SELECT has_voted FROM voters WHERE voter_id = ? FOR UPDATE";
                try (PreparedStatement ps = c.prepareStatement(check)) {
                    ps.setInt(1, voterId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            c.rollback();
                            return false;
                        }
                        if (rs.getBoolean("has_voted")) {
                            c.rollback();
                            return false;
                        }
                    }
                }

                String upVotes = "UPDATE candidates SET votes = votes + 1 WHERE candidate_id = ?";
                try (PreparedStatement ps = c.prepareStatement(upVotes)) {
                    ps.setInt(1, candidateId);
                    int updated = ps.executeUpdate();
                    if (updated == 0) {
                        c.rollback();
                        return false;
                    }
                }

                String mark = "UPDATE voters SET has_voted = true WHERE voter_id = ?";
                try (PreparedStatement ps = c.prepareStatement(mark)) {
                    ps.setInt(1, voterId);
                    ps.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
}
