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

    public void attaquer(Personnage cible) {
        System.out.println(nom + " attaque " + cible.nom + " !");
        cible.recevoirDegats(this.attaque);
    }

    public void recevoirDegats(int degats) {
        pointsVie -= degats;
        if (pointsVie < 0) pointsVie = 0;
        System.out.println(nom + " subit " + degats + " dégâts. PV restants : " + pointsVie + "/" + pvMax);
    }

    public boolean estMort() {
        return pointsVie <= 0;
    }

    public String getNom() { return nom; }
    public int getPointsVie() { return pointsVie; }
    public int getAttaque() { return attaque; }

    public abstract void afficherStats();
}
