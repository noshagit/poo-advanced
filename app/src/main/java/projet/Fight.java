package projet;

import projet.weapon.Weapon;

public class Fight {
    public void attack(Champion attacker, Champion defender) {
        Weapon attackerWeapon = attacker.getWeapon();
        Weapon defenderWeapon = defender.getWeapon();
        int diffRatio = (defender.getMoveSpeed() + attacker.getMoveSpeed()) / 2;
        
        if (attacker.getMoveSpeed() < defender.getMoveSpeed()) {
            for (int i = 0; i < diffRatio; i++) {
                attacker.takeDamage(defenderWeapon);
            }
            defender.takeDamage(attackerWeapon);
        } else {
            for (int i = 0; i < diffRatio; i++) {
                defender.takeDamage(attackerWeapon);
            }
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