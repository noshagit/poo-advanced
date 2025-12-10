package projet.enemies;

import java.util.List;

import projet.armor.*;
import projet.weapon.*;

public class Orc extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword(),
        new Axe(), 
        new Mace(), 
        new Spear()
    );

    List<Armor> possibleArmors = List.of(
        new LeatherArmor(), 
        new IronArmour(), 
        new ChainmailArmor(), 
        new WoodenArmour()
    );

    public Orc() {
        super("Orc", 80, new Fist(), new LeatherArmor());
        this.setWeapon(RandomWeaponDrop(possibleWeapons));
        this.setArmor(RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(6);
        this.setXpReward(20);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{9, 20};
    }
}
