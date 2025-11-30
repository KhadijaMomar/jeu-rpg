package metiers;

public abstract class Personnage {
    protected String nom;
    protected int pointsVie;
    protected int attaque;
    protected int pvMax; 


    public Personnage(String nom, int pointsVie, int attaque) {
        this.nom = nom;
        this.pointsVie = pointsVie;
        this.pvMax = pointsVie;
        this.attaque = attaque;
    }
    

    public abstract int attaquer();


    public void recevoirDegats(int degats) {
        pointsVie -= degats;
        if (pointsVie < 0) pointsVie = 0;
        System.out.println(nom + " subit " + degats + " dégâts. PV restants : " + pointsVie);
    }

    public boolean estMort() {
        return pointsVie <= 0;
    }

    
    public String getNom() { return nom; }
    public int getPv() { return pointsVie; } 
    public int getAttaque() { return attaque; }
    public int getPvMax() { return pvMax; }

    public abstract void afficherStats();
}