package com.codecool.gladiator.dao;

import com.codecool.gladiator.model.GladiatorHistory;
import com.codecool.gladiator.model.exception.GladiatorHistoryNotFoundException;
import com.codecool.gladiator.model.gladiators.Gladiator;

import java.util.List;

public interface GladiatorHistoryDao {
    void saveWin(Gladiator gladiator);
    void saveLoss(Gladiator gladiator);
    void saveTournamentWin(Gladiator gladiator);
    GladiatorHistory findBy(Gladiator gladiator) throws GladiatorHistoryNotFoundException;
    void remove(Gladiator gladiator);
    List<GladiatorHistory> getAll();
}
