package projet.enemies;

import java.util.List;

import projet.armor.BoneArmor;
import projet.weapon.Fist;
import projet.weapon.Sword;
import projet.weapon.Weapon;

public class Skeleton extends Enemy {
    List<Weapon> possibleWeapons = List.of(
        new Sword()
    );
    
    public Skeleton() {
        super("Skeleton", 65, new Fist(), new BoneArmor());
        this.setWeapon(RandomWeaponDrop(possibleWeapons));
        this.setXpReward(15);
    }
}
