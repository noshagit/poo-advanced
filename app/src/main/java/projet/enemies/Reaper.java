package projet.enemies;

import projet.armor.*;
import projet.weapon.*;

public class Reaper extends Enemy {

    public Reaper() {
        super("Reaper", 100, new Scythe(), new ReaperArmor());
        this.getWeapon().setLevel(9);
        this.setXpReward(40);
    }
}
