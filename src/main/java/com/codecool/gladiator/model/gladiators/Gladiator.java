package com.codecool.gladiator.model.gladiators;

public abstract class Gladiator {

    private final String name;
    private final int baseHp;
    private final int baseSp;
    private final int baseDex;
    private int level;
    private int currentHp;

    /**
     * Constructor for Gladiators
     *
     * @param name the gladiator's name
     * @param baseHp the gladiator's base Health Points
     * @param baseSp the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level the gladiator's starting Level
     */
    public Gladiator(String name, int baseHp, int baseSp, int baseDex, int level) {
        this.name = name;
        this.baseHp = baseHp;
        this.baseSp = baseSp;
        this.baseDex = baseDex;
        this.level = level;
        healUp();
    }

    /**
     * @return HP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getHpMultiplier();

    /**
     * @return SP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getSpMultiplier();

    /**
     * @return DEX multiplier of the gladiator subclass
     */
    protected abstract Multiplier getDexMultiplier();

    /**
     * Invoked after winning a fight, increments the gladiator's level
     */
    public void levelUp() {
        level++;
    }

    /**
     * Decreases the HP value after being hit
     *
     * @param hpValue the value by which to decrease
     */
    public void decreaseHpBy(int hpValue) {
        currentHp -= hpValue;
    }

    /**
     * Checks if the gladiator is dead or alive
     *
     * @return true if the currentHp is negative
     */
    public boolean isDead() {
        return currentHp < 0;
    }

    /**
     * Restores the current HP to the available HP value
     */
    public void healUp() {
        currentHp = getHp();
    }

    /**
     * @return Gladiator's available Health Points
     */
    public int getHp() {
        return (int) (baseHp * level * getHpMultiplier().getValue());
    }

    /**
     * @return Gladiator's available Strength Points
     */
    public int getSp() {
        return (int) (baseSp * level * getSpMultiplier().getValue());
    }

    /**
     * @return Gladiator's available Dexterity Points
     */
    public int getDex() {
        return (int) (baseDex * level * getDexMultiplier().getValue());
    }

    /**
     * @return Gladiator's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return Gladiator's current HP
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * @return Gladiator's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the full name of the gladiator
     * assembled by the subtype and the name
     * (e.g. "Brutal Brutus" or "Archer Leo")
     *
     * @return the full name
     */
    public String getFullName() {
        return String.format("%s %s", this.getClass().getSimpleName(), name);
    }

    public String toString() {
        return String.format("%s (%s/%s HP, %s SP, %s DEX, %s LVL)",
                getFullName(), getCurrentHp(), getHp(), getSp(), getDex(), getLevel());
    }

    public enum Multiplier {
        Low(0.75),
        Medium(1.0),
        High(1.25);

        private final double value;

        Multiplier(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

}
