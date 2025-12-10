package projet.enemies;

import java.util.List;

import projet.armor.*;
import projet.weapon.*;

public class Troll extends Enemy {

    List<Armor> possibleArmors = List.of(
        new LeatherArmor(), 
        new ChainmailArmor(),
        new IronArmour(),
        new WoodenArmour()
    );

    public Troll() {
        super("Troll", 120, new Log(), new LeatherArmor());
        this.setArmor(RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(10);
        this.setXpReward(30);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{20, 40};
    }
}
