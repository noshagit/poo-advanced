package projet.model.potions;

import projet.model.Player;

/** Class representing a health potion. */
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
