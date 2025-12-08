package projet.enemies;

import java.util.List;

import projet.armor.Armor;
import projet.armor.LeatherArmor;
import projet.weapon.Fist;
import projet.weapon.Sword;
import projet.weapon.Weapon;

public class Orc extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword()
    );

    Weapon weapon = RandomWeaponDrop(possibleWeapons);

    List<Armor> possibleArmors = List.of(
        new LeatherArmor()
    );

    Armor armor = RandomArmorDrop(possibleArmors);
    public Orc() {
        super("Orc", 80, new Fist(), new LeatherArmor());
        this.setWeapon(RandomWeaponDrop(possibleWeapons));
        this.setArmor(RandomArmorDrop(possibleArmors));
        this.setXpReward(20);
    }
}
