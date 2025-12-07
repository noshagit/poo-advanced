package projet.enemies;

import projet.weapon.StoneFist;
import projet.armor.StoneArmor;

public class StoneGolem extends Enemy {
    public StoneGolem() {
        super("StoneGolem", 150, new StoneFist(), new StoneArmor());
        this.setXpReward(45);
    }
}
