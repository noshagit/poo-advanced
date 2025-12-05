package projet;

import projet.weapon.Weapon;

public class Fight {
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
    }

    public boolean aliveVerification(Champion champion) {
        if (champion.getHealth() <= 0) {
            return false;
        }
        return true;
    }
}