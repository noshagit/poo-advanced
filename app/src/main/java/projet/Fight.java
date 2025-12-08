package projet;

/* IMPORTS */
import java.util.Random;

import projet.enemies.Enemy;
import projet.weapon.Weapon;

/**
 * The class responsible for handling fights between champions.
 */
public class Fight {
    /**
     * Executes an attack sequence between two champions.
     * 
     * @param player The champion who is attacking. ( The player )
     * @param enemy The champion who is defending. ( The monster )
     */
    public void attack(Player player, Enemy enemy) {
        Weapon playerWeapon = player.getWeapon();
        Weapon enemyWeapon = enemy.getWeapon();

        playerWeapon.addDamages(player.getExtraDamage());
        playerWeapon.addDamages(player.extraDamageMightySoup());
        enemyWeapon.subDamages(player.getExtraArmorGolem());

        Random rand = new Random();
        int isStunned = rand.nextInt(100);
        if (isStunned >= 25) {
            if (player.getMoveSpeed() < enemy.getMoveSpeed()) {
                player.takeDamage(enemyWeapon);
                if ((rand.nextInt(100) + enemy.getExtraCrit()) > 75) {
                    System.out.println(enemy.getName() + " lands a critical strike!");
                    player.takeDamage(enemyWeapon);
                }

                enemy.takeDamage(playerWeapon);
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
                if ((rand.nextInt(100) + player.getExtraCrit()) > 75) {
                    System.out.println(player.getName() + " lands a critical strike!");
                    enemy.takeDamage(playerWeapon);
                    if (player.hasLifeSteal()) {
                        player.addHealth(playerWeapon.getDamages() / 4);
                    }
                }

            } else {
                enemy.takeDamage(playerWeapon);
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
                if ((rand.nextInt(100) + player.getExtraCrit()) > 75) {
                    System.out.println(player.getName() + " lands a critical strike!");
                    enemy.takeDamage(playerWeapon);
                    if (player.hasLifeSteal()) {
                        player.addHealth(playerWeapon.getDamages() / 4);
                    }
                }

                player.takeDamage(enemyWeapon);
                if ((rand.nextInt(100) + enemy.getExtraCrit()) > 75) {
                    System.out.println(enemy.getName() + " lands a critical strike!");
                    player.takeDamage(enemyWeapon);
                }
            }
        } else {
            enemy.takeDamage(playerWeapon);
            if (player.hasLifeSteal()) {
                player.addHealth(playerWeapon.getDamages() / 4);
            }
            if ((rand.nextInt(100) + player.getExtraCrit()) > 75) {
                System.out.println(player.getName() + " lands a critical strike!");
                enemy.takeDamage(playerWeapon);
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
            }
        }

        playerWeapon.gainXp();
    }
}