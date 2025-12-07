package projet.enemies;

import projet.weapon.Hammer;
import projet.armor.ChainmailArmor;

public class Minotaur extends Enemy {
    public Minotaur() {
        super("Minotaur", 200, new Hammer(), new ChainmailArmor());
        this.setXpReward(50);
    }
}
