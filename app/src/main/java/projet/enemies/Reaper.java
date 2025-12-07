package projet.enemies;

import projet.weapon.Scythe;
import projet.armor.MythrilArmor;

public class Reaper extends Enemy {
    public Reaper() {
        super("Reaper", 100, new Scythe(), new MythrilArmor());
        this.setXpReward(40);
    }
}
