package projet.potions;

/* IMPORTS */

import java.util.Random;

/* GAME CLASSES */

import projet.Player;
import projet.weapon.Weapon;

/** Class representing a gambling potion. */
public class GamblingPotion extends Potion {

    public GamblingPotion() {
        super("Gambling Potion");
    }
    
    @Override
    public void use(Player player) {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance < 25) {
            player.setHealth(0);
            System.out.println("Oh no! The gambling potion backfired and caused instant death!");
        } else if (chance > 25 && chance < 40) {
            player.setWeapon(new Weapon("Gambling Blade", 50, 1));
            System.out.println("Lucky you! The gambling potion transformed your weapon into the Gambling Blade!");
        } else {
            player.setHealth(Math.max(player.getHealth() + 15, player.getMaxHealth()));
            System.out.println("You gained 15 health from the gambling potion!");
        }
        player.getInventory().removePotion(this);
    }
}
