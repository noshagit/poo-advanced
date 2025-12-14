package projet.model.enemies;

import projet.model.armors.IronArmor;
import projet.model.weapons.Cancer;

public class Kawaleck extends Enemy {
    public Kawaleck() {
        super("Kawaleck", 200, new Cancer(), new IronArmor());
        this.setXpReward(50);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{100, -100}; // min lvl > max lvl so that it never spawns
    }
}
