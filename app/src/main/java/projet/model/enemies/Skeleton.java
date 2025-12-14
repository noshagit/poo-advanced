package projet.model.enemies;

/* IMPORTS */

import java.util.List;

/* CLASS IMPORTS */

import projet.model.armors.*;
import projet.model.weapons.*;

public class Skeleton extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword(),
        new Spear(),
        new Flail(),
        new Mace()
    );
    
    public Skeleton() {
        super("Skeleton", 65, new Fist(), new BoneArmor());
        this.setWeapon(super.RandomWeaponDrop(possibleWeapons));
        this.getWeapon().setLevel(3);
        this.setXpReward(15);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{5, 15};
    }
}
