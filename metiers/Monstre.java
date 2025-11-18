package metiers;

public class Monstre extends Personnage {
    private int xpDonnee;
    
    public Monstre(String nom, int pointsVie, int attaque, int xpDonnee) {
        super(nom, pointsVie, attaque); 
        this.xpDonnee = xpDonnee;
        System.out.println("Un " + nom + " apparaît ! PV: " + pointsVie);
    }
    

    @Override
    public int attaquer() {
        System.out.println(this.getNom() + " attaque et inflige " + this.attaque + " dégâts !");
        return this.attaque;
    }

    public int getExperience() { return xpDonnee; } 
    
   
    @Override
    public void recevoirDegats(int degats) { 
        super.recevoirDegats(degats);
    }

    @Override
    public void afficherStats() {
        System.out.println("Monstre : " + nom + " | PV : " + pointsVie + " | ATK : " + attaque);
    }
}