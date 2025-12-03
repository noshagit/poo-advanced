package projet.enemies;

import projet.Champion;
import projet.armor.Armor;
import projet.weapon.Weapon;

public abstract class Enemy extends Champion {
    private int xpReward;

    public Enemy(String name, int health, Weapon weapon, Armor armor) {
        super(name, health, weapon, armor);
    }
    
    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }
}
