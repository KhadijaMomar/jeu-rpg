package metiers;
public abstract class Personnage {
    protected String nom;
    protected int pointsvie;        
    protected int mana;
    protected int attaque;
    protected int pvMax;

    public Personnage(String nom, int pointsvie, int mana, int attaque) {
        this.nom = nom;
        this.pointsvie = pointsvie;
        this.mana = mana;
        this.attaque = attaque;
    }


    public void recevoirDegats(int degats) {
        pointsvie -= degats;
        if (pointsvie < 0) pointsvie = 0;
        System.out.println(nom + " reçoit " + degats + " dégâts. PV restants: " + pointsvie + "/" + pvMax);
    }

    public boolean estMort() {
        return pointsvie <= 0;
    }
    
    public void attaquer(Personnage cible) {
        cible.recevoirDegats(this.attaque);
        System.out.println(nom + " attaque " + cible.nom + " et inflige " + this.attaque + " dégâts !");
    }

 
    public String getNom() { return nom; }
    public int getPv() { return pointsvie; }
    public int getPvMax() { return pvMax; }
    public int getAttaque() { return attaque; }
    public int getMana() { return mana; }
   
}