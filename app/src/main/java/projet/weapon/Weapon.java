package projet.weapon;

public class Weapon{
    private String nom;
    private int degats;
    private int attackSpeed;

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

}