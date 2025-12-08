package projet.weapon;

public class Weapon{
    private final String nom;
    private int degats;
    private int xp = 0;
    private int level = 1;
    private final int attackSpeed;

    public Weapon(String nom, int degats, int attackSpeed){
        this.nom = nom;
        this.degats = degats;
        this.attackSpeed = attackSpeed;
    }

    public String getName(){
        return nom;
    }
    public int getDamages(){
        return degats;
    }

    public int getAttackSpeed(){
        return attackSpeed;
    }

    public int getXp(){
        return xp;
    }

    public int getLevel(){
        return level;
    }

    /**
     * Increases the weapon's experience points and levels up if necessary.
     */
    public void gainXp(){
        this.xp += 10;
        if (this.xp >= 100){
            this.level ++;
            this.degats += 1;
            this.xp -=100;
        }
    }
}