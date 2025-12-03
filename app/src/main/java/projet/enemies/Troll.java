package projet.enemies;

import projet.weapon.Log;
import projet.armor.Naked;

public class Troll extends Enemy {
    public Troll() {
        super("Troll", 120, new Log(), new Naked());
        this.setXpReward(30);
    }
}
