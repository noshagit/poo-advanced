package projet.model.enemies;

import projet.model.armors.*;
import projet.model.weapons.*;

public class Reaper extends Enemy {

    public Reaper() {
        super("Reaper", 100, new Scythe(), new ReaperArmor());
        this.getWeapon().setLevel(9);
        this.setXpReward(25);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{15, 30};
    }
}
