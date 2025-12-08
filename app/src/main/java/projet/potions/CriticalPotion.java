package projet.potions;

import projet.Player;

public class CriticalPotion extends Potion {
    public CriticalPotion() {
        super("Critical Strike");
    }

    @Override
    public void use(Player player) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAh");
    }
}
