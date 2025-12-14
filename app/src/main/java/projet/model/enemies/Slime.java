package projet.model.enemies;

import projet.model.armors.Naked;
import projet.model.weapons.Fist;

public class Slime extends Enemy {

    public Slime() {
        super("Slime", 30, new Fist(), new Naked());
        this.setXpReward(5);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{0, 5};
    }
}
