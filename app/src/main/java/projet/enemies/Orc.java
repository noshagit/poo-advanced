package projet.enemies;

import projet.weapon.Axe;
import projet.armor.WoodenArmour;

public class Orc extends Enemy {
    public Orc() {
        super("Orc", 80, new Axe(), new WoodenArmour());
        this.setXpReward(20);
    }
}
