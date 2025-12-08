package projet.enemies;

import projet.armor.Naked;
import projet.weapon.Fist;

public class Slime extends Enemy {

    public Slime() {
        super("Slime", 30, new Fist(), new Naked());
        this.setXpReward(5);
    }
}
