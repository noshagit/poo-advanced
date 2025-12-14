package projet.model.enemies;

import projet.model.armors.StoneArmor;
import projet.model.weapons.StoneFist;

public class StoneGolem extends Enemy {
    public StoneGolem() {
        super("StoneGolem", 150, new StoneFist(), new StoneArmor());
        this.getWeapon().setLevel(12);
        this.setXpReward(35);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{30, 999};
    }
}
