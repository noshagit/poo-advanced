package projet.model.enemies;

/* IMPORTS */

import java.util.List;

/* CLASS IMPORTS */

import projet.model.armors.*;
import projet.model.weapons.*;

public class Goblin extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword(),
        new Fist(),
        new Spear()
    );

    List<Armor> possibleArmors = List.of(
        new LeatherArmor(),
        new Naked(),
        new IronArmor()
    );
    
    public Goblin() {
        super("Goblin", 30, new Fist(), new LeatherArmor());
        this.setWeapon(super.RandomWeaponDrop(possibleWeapons));
        this.setArmor(super.RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(1);
        this.setXpReward(10);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{2, 9};
    }
}
