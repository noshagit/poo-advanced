package projet.model.enemies;

import java.util.List;

import projet.model.armors.*;
import projet.model.weapons.*;

public class Troll extends Enemy {

    List<Armor> possibleArmors = List.of(
        new LeatherArmor(), 
        new ChainmailArmor(),
        new IronArmor(),
        new WoodenArmor()
    );

    public Troll() {
        super("Troll", 120, new Log(), new LeatherArmor());
        this.setArmor(super.RandomArmorDrop(possibleArmors));
        this.getWeapon().setLevel(10);
        this.setXpReward(30);
    }

    @Override
    public int[] getLevelRange() {
        return new int[]{20, 40};
    }
}
