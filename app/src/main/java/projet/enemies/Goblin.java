package projet.enemies;

import projet.weapon.Sword;
import projet.armor.Naked;

public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 30, new Sword(), new Naked());
        this.setXpReward(10);
    }
}
