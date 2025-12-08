package projet;

/* TODO : Critical strike chances implementation */

/* IMPORTS */
import java.util.Random;

/* GAME CLASSES */

import projet.weapon.Weapon;

/**
 * The class responsible for handling fights between champions.
 */
public class Fight {
    /**
     * Executes an attack sequence between two champions.
     * @param attacker The champion who is attacking. ( The player )
     * @param defender The champion who is defending. ( The monster )
     */
    public void attack(Champion attacker, Champion defender) {
        Weapon attackerWeapon = attacker.getWeapon();
        Weapon defenderWeapon = defender.getWeapon();

        Random rand = new Random();
        
        if (attacker.getMoveSpeed() < defender.getMoveSpeed()) {
            attacker.takeDamage(defenderWeapon);
            if ((rand.nextInt(100) + defender.getExtraCrit()) > 75) {
                System.out.println(defender.getName() + " lands a critical strike!");
                attacker.takeDamage(defenderWeapon);
            }
            
            defender.takeDamage(attackerWeapon);
            if ((rand.nextInt(100) + attacker.getExtraCrit()) > 75) {
                System.out.println(attacker.getName() + " lands a critical strike!");
                defender.takeDamage(attackerWeapon);
            }

        } else {
            defender.takeDamage(attackerWeapon);
            if ((rand.nextInt(100) + attacker.getExtraCrit()) > 75) {
                System.out.println(attacker.getName() + " lands a critical strike!");
                defender.takeDamage(attackerWeapon);
            }

            attacker.takeDamage(defenderWeapon);
            if ((rand.nextInt(100) + defender.getExtraCrit()) > 75) {
                System.out.println(defender.getName() + " lands a critical strike!");
                attacker.takeDamage(defenderWeapon);
            }
        }
        attackerWeapon.gainXp();
    }
}