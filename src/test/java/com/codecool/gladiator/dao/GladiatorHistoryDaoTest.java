package com.codecool.gladiator.dao;

import com.codecool.gladiator.model.GladiatorHistory;
import com.codecool.gladiator.model.gladiators.Gladiator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GladiatorHistoryDaoTest {
    private static GladiatorHistoryDao gladiatorHistoryDao;
    private static Set<Gladiator> addedGladiators;

    @BeforeAll
    public static void setUp() {
        gladiatorHistoryDao = DaoImplementationSupplier.getDaoImplementation().getGladiatorHistoryDao();
        addedGladiators = new HashSet<>();
    }

    @AfterEach
    public void tearDown() {
        for (Gladiator gladiator : addedGladiators) {
            gladiatorHistoryDao.remove(gladiator);
        }
        addedGladiators.clear();
    }

    @Test
    public void saveOneWin_shouldBeInMemory() {
        gladiatorHistoryDao.saveWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(1, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getWins());
    }

    @Test
    public void saveTwoWins_winsShouldBeIncreased() {
        gladiatorHistoryDao.saveWin(SampleGladiator.BELA);
        gladiatorHistoryDao.saveWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(2, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getWins());
    }

    @Test
    public void saveOneLoss_shouldBeInMemory() {
        gladiatorHistoryDao.saveLoss(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(1, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getLosses());
    }

    @Test
    public void saveTwoLosses_lossesShouldBeIncreased() {
        gladiatorHistoryDao.saveLoss(SampleGladiator.BELA);
        gladiatorHistoryDao.saveLoss(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(2, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getLosses());
    }

    @Test
    public void saveOneWin_lossesNotIncreased() {
        gladiatorHistoryDao.saveWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(0, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getLosses());
    }

    @Test
    public void saveOneLoss_winsNotIncreased() {
        gladiatorHistoryDao.saveLoss(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(0, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getWins());
    }

    @Test
    public void saveOneTournamentWin_shouldBeInMemory() {
        gladiatorHistoryDao.saveTournamentWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(1, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getTournamentWins());
    }

    @Test
    public void saveTwoTournamentWins_tournamentWinsShouldBeIncreased() {
        gladiatorHistoryDao.saveTournamentWin(SampleGladiator.BELA);
        gladiatorHistoryDao.saveTournamentWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        assertEquals(2, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getTournamentWins());
    }

    @Test
    public void getAll_shouldReturnAllSavedGladiators() {
        gladiatorHistoryDao.saveTournamentWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);
        gladiatorHistoryDao.saveTournamentWin(SampleGladiator.JULISKA);
        addedGladiators.add(SampleGladiator.JULISKA);
        gladiatorHistoryDao.saveLoss(SampleGladiator.GEZA);
        addedGladiators.add(SampleGladiator.GEZA);
        gladiatorHistoryDao.saveWin(SampleGladiator.GIZI);
        addedGladiators.add(SampleGladiator.GIZI);

        for (Gladiator gladiator : addedGladiators) {
            assertTrue(doesGladiatorHistoriesContainGladiator(gladiatorHistoryDao.getAll(), gladiator));
        }
    }

    @Test
    public void saveVariousEvents_eachEventIncrementedProperly() {
        gladiatorHistoryDao.saveWin(SampleGladiator.BELA);
        gladiatorHistoryDao.saveLoss(SampleGladiator.BELA);
        gladiatorHistoryDao.saveTournamentWin(SampleGladiator.BELA);
        addedGladiators.add(SampleGladiator.BELA);

        assertEquals(1, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getWins());
        assertEquals(1, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getLosses());
        assertEquals(1, gladiatorHistoryDao.findBy(SampleGladiator.BELA).getTournamentWins());
    }

    private boolean doesGladiatorHistoriesContainGladiator(List<GladiatorHistory> gladiatorHistories, Gladiator gladiator) {
        boolean isInHistories = false;
        for (GladiatorHistory gladiatorHistory : gladiatorHistories) {
            if (gladiatorHistory.getFullName().equals(gladiator.getFullName())) {
                isInHistories = true;
                break;
            }
        }
        return isInHistories;
    }
}