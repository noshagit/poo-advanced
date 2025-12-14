package projet.model.potions;

import projet.model.Player;

/** Class representing a BoneSkinPotion potion. */
public class BerserkPotion extends Potion {

    public BerserkPotion() {
        super("Berserk");
    }

    @Override
    public void use(Player player) {
        player.setExtraDamage(10);
        player.setHealth(player.getHealth() - 30);
    }
}
