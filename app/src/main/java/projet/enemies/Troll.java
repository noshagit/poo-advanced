package projet.enemies;

import java.util.List;

import projet.armor.Armor;
import projet.armor.LeatherArmor;
import projet.weapon.Log;

public class Troll extends Enemy {

    List<Armor> possibleArmors = List.of(
        new LeatherArmor()
    );

    public Troll() {
        super("Troll", 120, new Log(), new LeatherArmor());
        this.setArmor(RandomArmorDrop(possibleArmors));
        this.setXpReward(30);
    }
}
