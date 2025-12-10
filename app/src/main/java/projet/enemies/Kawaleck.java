package projet.enemies;

import projet.weapon.Cancer;
import projet.armor.IronArmour;

public class Kawaleck extends Enemy {
    public Kawaleck() {
        super("Kawaleck", 200, new Cancer(), new IronArmour());
        this.setXpReward(50);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{100, -100}; // min lvl > max lvl so that it never spawns
    }
}
