package projet.model.enemies;

/* IMPORTS */

import java.util.Collections;
import java.util.List;
import java.util.Random;

import projet.model.armors.Armor;
import projet.model.Champion;
import projet.model.weapons.Weapon;
import projet.model.potions.Potion;

/** Abstract class representing an enemy. */
public abstract class Enemy extends Champion {
    private int xpReward;

    public Enemy(String name, int health, Weapon weapon, Armor armor) {
        super(name, health, weapon, armor);
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    /** Returns a random weapon from the list of possible weapons. 
     * @param possibleWeapons List of possible weapons to choose from.
     * @return A randomly selected weapon from the list.
    */
    public Weapon RandomWeaponDrop(List<Weapon> possibleWeapons) {
        Random rand = new java.util.Random();
        int randomIndex = rand.nextInt(possibleWeapons.size());
        return possibleWeapons.get(randomIndex);
    }

    /**
     * Returns a random armor from the list of possible armors.
     * @param possibleArmors List of possible armors to choose from.
     * @return A randomly selected armor from the list.
     */
    public Armor RandomArmorDrop(List<Armor> possibleArmors) {
        Random rand = new java.util.Random();
        int randomIndex = rand.nextInt(possibleArmors.size());
        return possibleArmors.get(randomIndex);
    }

    /**
     * Returns the list of special potions dropped by this enemy (OCP friendly).
     * Par défaut, aucun drop spécial.
     * @param randomValue la valeur aléatoire pour le drop
     * @return Liste de potions spéciales à donner au joueur
     */
    public List<Potion> specialPotionsDrop(int randomValue) {
        return Collections.emptyList();
    }

    /**
     * Returns the level range for this enemy type.
     * @return An array of two integers [minLevel, maxLevel] representing the level range.
     */
    public abstract int[] getLevelRange();
}
