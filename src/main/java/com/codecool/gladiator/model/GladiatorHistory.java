package com.codecool.gladiator.model;

public class GladiatorHistory {
    private int tournamentWins = 0;
    private int wins = 0;
    private int losses = 0;
    private final String fullName;

    public GladiatorHistory(String fullName) {
        this.fullName = fullName;
    }

    public GladiatorHistory(String fullName, int wins, int losses, int tournamentWins) {
        this.fullName = fullName;
        this.wins = wins;
        this.losses = losses;
        this.tournamentWins = tournamentWins;
    }

    public GladiatorHistory incrementWins() {
        wins++;
        return this;
    }

    public GladiatorHistory incrementLosses() {
        losses++;
        return this;
    }

    public GladiatorHistory incrementTournamentWins() {
        tournamentWins++;
        return this;
    }

    public int getWins() {
        return wins;
    }

    public String getFullName() {
        return fullName;
    }

    public int getLosses() {
        return losses;
    }

    public int getTournamentWins() {
        return tournamentWins;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, losses: %d, wins: %d, tournament wins: %d\n",
                fullName, losses, wins, tournamentWins
        );
    }
}
