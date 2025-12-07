package projet.enemies;

import projet.weapon.Fist;
import projet.armor.Naked;

public class Slime extends Enemy {
    public Slime() {
        super("Slime", 30, new Fist(), new Naked());
        this.setXpReward(5);
    }
}
