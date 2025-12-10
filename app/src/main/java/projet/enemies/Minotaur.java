package projet.enemies;

import java.util.List;

import projet.armors.*;
import projet.weapons.*;

public class Minotaur extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword(),
        new Hammer(),
        new Axe()
    );

    List<Armor> possibleArmors = List.of(
        new MythrilArmor(),
        new KevlarArmor()
    );
    
    public Minotaur() {
        super("Minotaur", 200, new Hammer(), new MythrilArmor());
        this.setWeapon(super.RandomWeaponDrop(possibleWeapons));
        this.setArmor(super.RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(30);
        this.setXpReward(40);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{30, 9999};
    }
}
