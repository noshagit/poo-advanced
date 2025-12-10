package projet.enemies;

import projet.armors.*;
import projet.weapons.*;

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
