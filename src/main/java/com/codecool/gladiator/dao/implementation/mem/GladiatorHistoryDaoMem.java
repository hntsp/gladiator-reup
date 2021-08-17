package com.codecool.gladiator.dao.implementation.mem;

import com.codecool.gladiator.dao.GladiatorHistoryDao;
import com.codecool.gladiator.model.GladiatorHistory;
import com.codecool.gladiator.model.exception.GladiatorHistoryNotFoundException;
import com.codecool.gladiator.model.gladiators.Gladiator;

import java.util.LinkedList;
import java.util.List;

public class GladiatorHistoryDaoMem implements GladiatorHistoryDao {
    private static final GladiatorHistoryDaoMem INSTANCE = new GladiatorHistoryDaoMem();
    private final List<GladiatorHistory> gladiatorHistories;

    private GladiatorHistoryDaoMem() {
        gladiatorHistories = new LinkedList<>();
    }

    public static GladiatorHistoryDaoMem getInstance() {
        return INSTANCE;
    }

    @Override
    public void saveWin(Gladiator gladiator) {
        try {
            findBy(gladiator).incrementWins();
        } catch (GladiatorHistoryNotFoundException e) {
            gladiatorHistories.add(new GladiatorHistory(gladiator.getFullName()).incrementWins());
        }
    }

    @Override
    public void saveLoss(Gladiator gladiator) {
        try {
            findBy(gladiator).incrementLosses();
        } catch (GladiatorHistoryNotFoundException e) {
            gladiatorHistories.add(new GladiatorHistory(gladiator.getFullName()).incrementLosses());
        }
    }

    @Override
    public void saveTournamentWin(Gladiator gladiator) {
        try {
            findBy(gladiator).incrementTournamentWins();
        } catch (GladiatorHistoryNotFoundException e) {
            gladiatorHistories.add(new GladiatorHistory(gladiator.getFullName()).incrementTournamentWins());
        }
    }

    @Override
    public GladiatorHistory findBy(Gladiator gladiator) throws GladiatorHistoryNotFoundException {
        return gladiatorHistories.stream().filter(
                gladiatorHistory -> gladiatorHistory.getFullName().equals(gladiator.getFullName())
        ).findFirst().orElseThrow(GladiatorHistoryNotFoundException::new);
    }

    @Override
    public void remove(Gladiator gladiator) {
        gladiatorHistories.remove(findBy(gladiator));
    }

    @Override
    public List<GladiatorHistory> getAll() {
        return gladiatorHistories;
    }
}
