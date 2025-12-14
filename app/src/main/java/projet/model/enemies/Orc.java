package projet.model.enemies;

import java.util.List;

import projet.model.armors.*;
import projet.model.weapons.*;

public class Orc extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword(),
        new Axe(), 
        new Mace(), 
        new Spear()
    );

    List<Armor> possibleArmors = List.of(
        new LeatherArmor(), 
        new IronArmor(), 
        new ChainmailArmor(), 
        new WoodenArmor()
    );

    public Orc() {
        super("Orc", 80, new Fist(), new LeatherArmor());
        this.setWeapon(super.RandomWeaponDrop(possibleWeapons));
        this.setArmor(super.RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(6);
        this.setXpReward(20);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{9, 20};
    }
}
