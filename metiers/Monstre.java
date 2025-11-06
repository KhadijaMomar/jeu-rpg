package metiers;

import java.util.Random;

public class Monstre extends Personnage {
    private int xpDonnee;
    private int orDonne;

    public Monstre(String nom, int pointsVie, int attaque, int xpDonnee, int orDonne) {
        super(nom, pointsVie, attaque);
        this.xpDonnee = xpDonnee;
        this.orDonne = orDonne;
    }

    public static Monstre genererAleatoire() {
        Random rand = new Random();
        int type = rand.nextInt(3);
        switch (type) {
            case 0: return new Monstre("Gobelin", 40, 8, 30, 10);
            case 1: return new Monstre("Squelette", 60, 12, 50, 20);
            default: return new Monstre("Ogre", 80, 15, 70, 30);
        }
    }

    public int getXpDonnee() { return xpDonnee; }
    public int getOrDonne() { return orDonne; }

    @Override
    public void afficherStats() {
        System.out.println("Monstre : " + nom + " | PV : " + pointsVie + " | ATK : " + attaque);
    }
}
