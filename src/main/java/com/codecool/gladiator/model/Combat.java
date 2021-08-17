package com.codecool.gladiator.model;

import com.codecool.gladiator.model.gladiators.Gladiator;
import com.codecool.gladiator.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Combat class, used for simulating fights between pairs of gladiators
 */
public class Combat {

    private static final double MIN_DAMAGE_MULTIPLIER = 0.1;
    private static final double MAX_DAMAGE_MULTIPLIER = 0.5;

    private boolean turnA;
    private final Gladiator gladiator1;
    private final Gladiator gladiator2;

    private final List<String> combatLog;

    public Combat(Contestants contestants) {
        this.gladiator1 = contestants.gladiator1;
        this.gladiator2 = contestants.gladiator2;
        this.combatLog = new ArrayList<>();
        this.turnA = true;
    }

    /**
     * Simulates the combat and returns the winner.
     * If one of the opponents is null, the winner is the one that is not null
     * If both of the opponents are null, the return value is null
     *
     * @return winner of combat
     */
    public Gladiator simulate() {
        if (gladiator2 == null) return gladiator1;
        if (gladiator1 == null) return gladiator2;
        boolean ongoingCombat = true;
        while (ongoingCombat) ongoingCombat = !simulateTurn();
        return gladiator1.isDead() ? gladiator2 : gladiator1;
    }

    /**
     * Simulates a single turn of combat
     *
     * @return true if the fight is over
     */
    private boolean simulateTurn() {
        var attacker = turnA ? gladiator1 : gladiator2;
        var defender = turnA ? gladiator2 : gladiator1;

        var chanceToHit = calculateHitChance(attacker, defender);
        if (RandomUtils.getRandomPercentage() <= chanceToHit) {
            // Hit
            var damage = calculateDamage(attacker);
            defender.decreaseHpBy(damage);
            combatLog.add(String.format("%s deals %s damage", attacker.getName(), damage));
        } else {
            // Miss
            combatLog.add(String.format("%s missed", attacker.getName()));
        }
        turnA = !turnA;
        // Return boolean value whether someone has died
        return gladiator1.isDead() || gladiator2.isDead();
    }

    /**
     * Calculates a percentage chance of hitting the enemy
     *
     * @param attacker the gladiator trying to hit
     * @param defender the gladiator trying to avoid the hit
     * @return the chance of a successful hit
     */
    private int calculateHitChance(Gladiator attacker, Gladiator defender) {
        int unclampedChance = attacker.getDex() - defender.getDex();
        return Math.max(10, Math.min(unclampedChance, 100)); // 100% is max, so cap at 100
    }

    /**
     * Calculates the damage for an attack
     *
     * @param current the gladiator whose attack damage is calculated
     * @return the value of the damage
     */
    private int calculateDamage(Gladiator current) {
        return (int) (current.getSp() * RandomUtils.getRandomBetween(MIN_DAMAGE_MULTIPLIER, MAX_DAMAGE_MULTIPLIER));
    }

    public Gladiator getGladiator1() {
        return gladiator1;
    }

    public Gladiator getGladiator2() {
        return gladiator2;
    }

    public String getCombatLog(String separator) {
        return String.join(separator, combatLog);
    }

}
