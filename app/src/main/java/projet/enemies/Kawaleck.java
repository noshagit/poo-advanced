package projet.enemies;

import projet.weapon.Cancer;
import projet.armor.IronArmour;

public class Kawaleck extends Enemy {
    public Kawaleck() {
        super("Kawaleck", 200, new Cancer(), new IronArmour());
        this.setXpReward(50);
    }
}