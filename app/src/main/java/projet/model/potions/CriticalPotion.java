package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class CriticalPotion extends Potion {
    public CriticalPotion() {
        super("Critical Strike");
    }

    @Override
    public void use(Player player) {
        player.setExtraCrit(10);
    }
}
