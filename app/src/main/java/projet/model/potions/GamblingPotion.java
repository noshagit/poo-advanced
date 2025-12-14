package projet.model.potions;

/* IMPORTS */

import java.util.Random;

import projet.model.weapons.Weapon;
import projet.model.Player;
import projet.view.Views;

/** Class representing a gambling potion. */
public class GamblingPotion extends Potion {

    public GamblingPotion() {
        super("Gambling");
    }
    
    @Override
    public void use(Player player) {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance < 25) {
            player.setHealth(0);
            Views.println("Oh no! The gambling potion backfired and caused instant death!");
        } else if (chance > 25 && chance < 40) {
            player.setWeapon(new Weapon("Gambling Blade", 50, 1));
            Views.println("Lucky you! The gambling potion transformed your weapon into the Gambling Blade!");
        } else {
            player.setHealth(Math.max(player.getHealth() + 15, player.getMaxHealth()));
            Views.println("You gained 15 health from the gambling potion!");
        }
        player.getInventory().removePotion(this);
    }
}
