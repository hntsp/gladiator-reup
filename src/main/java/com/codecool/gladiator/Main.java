package com.codecool.gladiator;

import com.codecool.gladiator.controller.Colosseum;
import com.codecool.gladiator.dao.DaoImplementation;
import com.codecool.gladiator.dao.DaoImplementationSupplier;
import com.codecool.gladiator.dao.DaoType;
import com.codecool.gladiator.model.gladiators.GladiatorFactory;
import com.codecool.gladiator.view.ConsoleView;

public class Main {

    public static void main(String[] args) {
        Colosseum colosseum = new Colosseum(
                new ConsoleView(),
                DaoImplementationSupplier.getDaoImplementation().getGladiatorHistoryDao(),
                new GladiatorFactory("Names.txt")
        );
        colosseum.welcomeAndAskForStages();
        colosseum.runSimulation();
    }
}
