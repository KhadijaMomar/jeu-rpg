package metiers;

public class Monstre extends Personnage {
    private int xpDonnee;
    private int argentDonne; 
    
    public Monstre(String nom, int pointsVie, int attaque, int xpDonnee, int argentDonne) { 
        super(nom, pointsVie, attaque); 
        this.xpDonnee = xpDonnee;
        this.argentDonne = argentDonne; 
        System.out.println("Un " + nom + " apparaît ! PV: " + pointsVie);
    }
    

    @Override
    public int attaquer() {
        System.out.println(this.getNom() + " attaque et inflige " + this.attaque + " dégâts !");
        return this.attaque;
    }

    public int getExperience() { return xpDonnee; } 
    public int getArgentDonne() { return argentDonne; }
    
   
    @Override
    public void recevoirDegats(int degats) { 
        super.recevoirDegats(degats);
    }

    @Override
    public void afficherStats() {
        System.out.println("Monstre : " + nom + " | PV : " + pointsVie + " | ATK : " + attaque);
    }
}