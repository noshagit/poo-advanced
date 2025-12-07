package projet.enemies;

import projet.weapon.Spear;
import projet.armor.BoneArmor;

public class Skeleton extends Enemy {
    public Skeleton() {
        super("Skeleton", 65, new Spear(), new BoneArmor());
        this.setXpReward(15);
    }
}
