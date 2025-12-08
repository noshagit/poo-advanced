package projet;

/* IMPORTS */

import java.util.List;
import java.util.ArrayList;

/* GAME CLASSES */

import projet.potions.Potion;
import projet.armor.Armor;
import projet.weapon.Weapon;

public class Inventory {
    private final List<Potion> potions;
    private final List<Weapon> weapons;
    private final List<Armor> armors;

    public Inventory() {
        this.potions = new ArrayList<Potion>();
        this.weapons = new ArrayList<Weapon>();
        this.armors = new ArrayList<Armor>();
    }

    public void addPotion(Potion potion) {
        this.potions.add(potion);
    }

    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    public void addArmor(Armor armor) {
        this.armors.add(armor);
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Armor> getArmors() {
        return armors;
    }

    public void removePotion(Potion potion) {
        this.potions.remove(potion);
    }

    public void removeWeapon(Weapon weapon) {
        this.weapons.remove(weapon);
    }

    public void removeArmor(Armor armor) {
        this.armors.remove(armor);
    }
}