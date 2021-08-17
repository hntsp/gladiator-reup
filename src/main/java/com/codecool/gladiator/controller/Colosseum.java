package com.codecool.gladiator.controller;

import com.codecool.gladiator.dao.GladiatorHistoryDao;
import com.codecool.gladiator.model.Combat;
import com.codecool.gladiator.model.Contestants;
import com.codecool.gladiator.model.gladiators.Gladiator;
import com.codecool.gladiator.model.gladiators.GladiatorFactory;
import com.codecool.gladiator.util.Tournament;
import com.codecool.gladiator.view.Viewable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Colosseum {

    public static final int MIN_TOURNAMENT_STAGES = 1;
    public static final int MAX_TOURNAMENT_STAGES = 10;

    private final Viewable view;
    private final GladiatorHistoryDao gladiatorHistoryDao;
    private final GladiatorFactory gladiatorFactory;
    private int stages = 2;

    public Colosseum(Viewable view, GladiatorHistoryDao gladiatorHistoryDao, GladiatorFactory gladiatorFactory) {
        this.view = view;
        this.gladiatorHistoryDao = gladiatorHistoryDao;
        this.gladiatorFactory = gladiatorFactory;
    }

    /**
     * Runs the Tournament simulation
     */
    public void runSimulation() {
        var numberOfGladiators = (int) Math.pow(2, stages);
        var gladiators = generateGladiators(numberOfGladiators);
        var contestants = splitGladiatorsIntoPairs(gladiators);
        var tournamentTree = new Tournament(contestants);
        var champion = getChampion(tournamentTree);
        gladiatorHistoryDao.saveTournamentWin(champion);
        announceChampion(champion);

        // The following line chains the above lines:
        // announceChampion(getChampion(new BinaryTree<>(generateGladiators((int) Math.pow(2, stages)))));
    }

    private List<Gladiator> generateGladiators(int numberOfGladiators) {
        List<Gladiator> gladiators = new ArrayList<>();
        for (int i = 0; i < numberOfGladiators; i++) {
            gladiators.add(gladiatorFactory.generateRandomGladiator());
        }
        introduceGladiators(gladiators);
        return gladiators;
    }

    private List<Contestants> splitGladiatorsIntoPairs(List<Gladiator> gladiators) {
        List<Contestants> contestants = new LinkedList<>();
        for (int i = 0; i < gladiators.size(); i += 2) {
            Gladiator gladiator1 = gladiators.get(i);
            Gladiator gladiator2 = gladiators.get(i + 1);
            contestants.add(new Contestants(gladiator1, gladiator2));
        }
        return contestants;
    }

    private Gladiator getChampion(Tournament tournament) {
        if (tournament == null || tournament.size() == 0) return null;
        if (tournament.getContestants() == null) {
            Contestants contestants = new Contestants(
                    getChampion(tournament.getLeftBranch()),
                    getChampion(tournament.getRightBranch())
            );
            tournament.setContestants(contestants);
        }
        return simulateCombat(new Combat(tournament.getContestants()));
    }

    private Gladiator simulateCombat(Combat combat) {
        Gladiator gladiator1 = combat.getGladiator1();
        Gladiator gladiator2 = combat.getGladiator2();
        announceCombat(gladiator1, gladiator2);

        Gladiator winner = combat.simulate();
        Gladiator loser = winner == gladiator1 ? gladiator2 : gladiator1;
        displayCombatLog(combat);
        announceWinnerAndLoser(winner, loser);
        gladiatorHistoryDao.saveWin(winner);
        gladiatorHistoryDao.saveLoss(loser);

        winner.levelUp();
        winner.healUp();
        return winner;
    }

    public void welcome() {
        view.display("Ave Caesar, and welcome to the Colosseum!");
    }

    public void welcomeAndAskForStages() {
        welcome();
        view.display("How many stages of the Tournament do you wish to watch? (1-10)");
        stages = view.getNumberBetween(MIN_TOURNAMENT_STAGES, MAX_TOURNAMENT_STAGES);
    }

    private void introduceGladiators(List<Gladiator> gladiators) {
        view.display(String.format("\nWe have selected Rome's %d finest warriors for today's Tournament!", gladiators.size()));
        for (Gladiator gladiator : gladiators) {
            view.display(String.format(" - %s", gladiator));
        }
        view.display("\n\"Ave Imperator, morituri te salutant!\"");
    }

    private void announceCombat(Gladiator gladiator1, Gladiator gladiator2) {
        view.display(String.format("\nDuel %s versus %s:", gladiator1.getName(), gladiator2.getName()));
        view.display(String.format(" - %s", gladiator1));
        view.display(String.format(" - %s", gladiator2));
    }

    private void displayCombatLog(Combat combat) {
        view.display(String.format(" - %s", combat.getCombatLog(", ")));
    }

    private void announceWinnerAndLoser(Gladiator winner, Gladiator loser) {
        view.display(String.format("%s has died, %s wins!", loser.getFullName(), winner.getFullName()));
    }

    private void announceChampion(Gladiator champion) {
        if (champion == null) {
            view.display("\nHave mercy, Caesar, the Tournament will start soon!");
        } else {
            view.display(String.format("\nThe Champion of the Tournament is %s!", champion.getFullName()));
            view.display(gladiatorHistoryDao.getAll().toString());
        }
    }

}
