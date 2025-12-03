package projet;

public class Weapon{
    private String nom;
    private int degats;
    private double attackSpeed;

    public Weapon(String nom, int degats, double attackSpeed){
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

    public double getAttackSpeed(){
        return (double)attackSpeed;
    }

}