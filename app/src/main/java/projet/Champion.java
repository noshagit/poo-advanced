package projet;

import java.util.Random;

import projet.armor.Armor;
import projet.weapon.Weapon;

public abstract class Champion {
    private String name;
    private int health;
    private Weapon weapon;
    private Armor armor;
    private final Weapon oldWeapon;
    private final int baseHealth;

    public Champion(String name, int health, Weapon weapon, Armor armor) {
        this.name = name;
        this.health = health;
        this.baseHealth = health;
        this.weapon = weapon;
        this.oldWeapon = weapon;
        this.armor = armor;
    }

    public Weapon getOldWeapon() {
        return oldWeapon;
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

    public void takeDamage(Weapon weapon) {
        if (weapon.getName().equals("Gambling Blade")) {
            Random rand = new Random();
            int chance = rand.nextInt(150) - 25;
            this.health -= (chance - this.armor.getResistance());
        } else {
            if ((weapon.getDamages() - this.armor.getResistance()) > 2) {
                this.health -= (weapon.getDamages() - this.armor.getResistance());
            } else {
                this.health -= 2;
            }
        }
    }

    public int getMoveSpeed() {
        return this.weapon.getAttackSpeed() / (this.armor.getWeight() / 10);
    }
}