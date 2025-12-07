package projet;

import java.util.List;

import projet.potions.Potion;

import java.util.ArrayList;

public class Inventory {
    private final List<Potion> potions;
    private final List<Potion> weapons;
    private final List<Potion> armors;

    public Inventory() {
        this.potions = new ArrayList<Potion>();
        this.weapons = new ArrayList<Potion>();
        this.armors = new ArrayList<Potion>();
    }

    public void addPotion(Potion potion) {
        this.potions.add(potion);
    }

    public void addWeapon(Potion weapon) {
        this.weapons.add(weapon);
    }

    public void addArmor(Potion armor) {
        this.armors.add(armor);
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public List<Potion> getWeapons() {
        return weapons;
    }

    public List<Potion> getArmors() {
        return armors;
    }

    public void removePotion(Potion potion) {
        this.potions.remove(potion);
    }

    public void removeWeapon(Potion weapon) {
        this.weapons.remove(weapon);
    }

    public void removeArmor(Potion armor) {
        this.armors.remove(armor);
    }
}