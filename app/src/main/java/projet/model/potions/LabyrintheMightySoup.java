package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class LabyrintheMightySoup extends Potion {

    public LabyrintheMightySoup() {
        super("Labyrinthe Mighty Soup");
    }

    @Override
    public void use(Player player) {
        player.setChanceToStun(true);
        player.setExtraDamage(20);
    }
    
}
