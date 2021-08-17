package com.codecool.gladiator.model.gladiators;

import com.codecool.gladiator.util.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GladiatorFactory {

    private static final int MIN_START_HP = 25;
    private static final int MAX_START_HP = 100;
    private static final int MIN_START_SP = 25;
    private static final int MAX_START_SP = 100;
    private static final int MIN_START_DEX = 25;
    private static final int MAX_START_DEX = 100;
    private static final int MIN_START_LEVEL = 1;
    private static final int MAX_START_LEVEL = 6;

    private List<String> names;

    public GladiatorFactory(String fileOfNames) {
        try {
            File file = new File(getClass().getClassLoader().getResource(fileOfNames).getFile());
            names = Files.readAllLines(file.toPath());
        } catch (IOException|NullPointerException e) {
            System.out.println("Names file not found or corrupted!");
            System.exit(1);
        }
    }

    /**
     * Picks a random name from the file given in the constructor
     *
     * @return gladiator name
     */
    private String getRandomName() {
        return RandomUtils.getRandomItemFrom(names);
    }

    /**
     * Instantiates a new gladiator with random name and type.
     * Creating an Archer, an Assassin, or a Brutal has the same chance,
     * while the chance of creating a Swordsman is the double of the chance of creating an Archer.
     * @return new Gladiator
     */
    public Gladiator generateRandomGladiator() {
        var name = getRandomName();

        // Assign starter statistics
        int baseHp = RandomUtils.getRandomBetween(MIN_START_HP, MAX_START_HP);
        int baseSp = RandomUtils.getRandomBetween(MIN_START_SP, MAX_START_SP);
        int baseDex = RandomUtils.getRandomBetween(MIN_START_DEX, MAX_START_DEX);
        int level = RandomUtils.getRandomBetween(MIN_START_LEVEL, MAX_START_LEVEL);

        var chance = RandomUtils.getRandomBetween(1, 5);
        switch (chance) {
            case 1:
            case 2:
                return new Swordsman(name, baseHp, baseSp, baseDex, level);
            case 3:
                return new Archer(name, baseHp, baseSp, baseDex, level);
            case 4:
                return new Assassin(name, baseHp, baseSp, baseDex, level);
            default:
                return new Brutal(name, baseHp, baseSp, baseDex, level);
        }
    }
}
