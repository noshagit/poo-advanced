package projet;

/* IMPORTS */

import java.util.Random;

/* GAME CLASSES */

import projet.armor.Armor;
import projet.weapon.Weapon;

/**
 * The base class for all champions (players and enemies).
 */
public abstract class Champion {
    private String name;
    private int health;
    private Weapon weapon;
    private Armor armor;
    private final int baseHealth;
    private int extraCrit = 0;

    public Champion(String name, int health, Weapon weapon, Armor armor) {
        this.name = name;
        this.health = health;
        this.baseHealth = health;
        this.weapon = weapon;
        this.armor = armor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void resetHealth() {
        this.health = this.baseHealth;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getExtraCrit() {
        int extra = this.extraCrit;
        this.extraCrit = 0;
        return extra;
    }

    public void setExtraCrit(int extraCrit) {
        this.extraCrit = extraCrit;
    }

    /**
     * Inflicts damage to the champion based on the weapon used.
     *
     * @param weapon The weapon used to inflict damage.
     */
    public void takeDamage(Weapon weapon) {
        Random rand = new Random();
        boolean critChance = rand.nextInt(100) > 85;
        int critDefense = 0;
        if (critChance) {
            System.out.println(this.getName() + " blocks part of the damage!");
            critDefense = weapon.getDamages() / 4;
        }
        int damageAfterArmor = weapon.getDamages() - this.armor.getResistance() - critDefense;

        if (weapon.getName().equals("Gambling Blade")) {
            int chance = rand.nextInt(150) - 25;
            this.health -= (chance - this.armor.getResistance());
        } else {
            if (damageAfterArmor > 2) {
                this.health -= damageAfterArmor;
            } else {
                this.health -= 2;
            }
        }
    }

    /**
     * Gets the champion's movement speed.
     *
     * @return The movement speed, calculated based on weapon attack speed and armor weight.
     */
    public int getMoveSpeed() {
        return this.weapon.getAttackSpeed() / (this.armor.getWeight() / 10);
    }
}