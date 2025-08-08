package com.votingsystem;

import com.votingsystem.dao.AdminDAO;
import com.votingsystem.dao.CandidateDAO;

public class Setup {
    public static void runSetup() {
        try {
            AdminDAO a = new AdminDAO();
            a.registerAdmin("admin", "admin123");
            CandidateDAO cdao = new CandidateDAO();
            cdao.addCandidate("Alice", "Party A");
            cdao.addCandidate("Bob", "Party B");
            System.out.println("Inserted admin and two candidates.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
