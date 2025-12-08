package projet.enemies;

/* IMPORTS */

import java.util.List;

import projet.armor.Armor;
import projet.armor.LeatherArmor;
import projet.weapon.Fist;
import projet.weapon.Sword;
import projet.weapon.Weapon;

public class Goblin extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword()
    );

    List<Armor> possibleArmors = List.of(
        new LeatherArmor()
    );
    
    public Goblin() {
        super("Goblin", 30, new Fist(), new LeatherArmor());
        this.setWeapon(RandomWeaponDrop(possibleWeapons));
        this.setArmor(RandomArmorDrop(possibleArmors));
        this.setXpReward(10);
    }
}
