package projet.enemies;

/* IMPORTS */

import java.util.List;

import projet.armor.*;
import projet.weapon.*;

public class Goblin extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword(),
        new Fist(),
        new Spear()
    );

    List<Armor> possibleArmors = List.of(
        new LeatherArmor(),
        new Naked(),
        new IronArmour()
    );
    
    public Goblin() {
        super("Goblin", 30, new Fist(), new LeatherArmor());
        this.setWeapon(RandomWeaponDrop(possibleWeapons));
        this.setArmor(RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(1);
        this.setXpReward(10);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{2, 9};
    }
}
