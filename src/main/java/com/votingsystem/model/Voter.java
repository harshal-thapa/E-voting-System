package com.votingsystem.model;

public class Voter {
    private int voterId;
    private String name;
    private String username;
    private String passwordHash;
    private boolean hasVoted;

    public Voter() {}
    public Voter(String name, String username, String passwordHash) {
        this.name = name; this.username = username; this.passwordHash = passwordHash;
    }

    public int getVoterId() { return voterId; }
    public void setVoterId(int voterId) { this.voterId = voterId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public boolean isHasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
}
