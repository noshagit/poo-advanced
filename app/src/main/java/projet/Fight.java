package projet;

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
        
        if (attacker.getMoveSpeed() < defender.getMoveSpeed()) {
            attacker.takeDamage(defenderWeapon);
            defender.takeDamage(attackerWeapon);
        } else {
            defender.takeDamage(attackerWeapon);
            attacker.takeDamage(defenderWeapon);
        }
        attackerWeapon.gainXp();
        System.out.println(attackerWeapon.getXp() + ", " + attackerWeapon.getLevel() + " , " + attackerWeapon.getDamages());
    }
}