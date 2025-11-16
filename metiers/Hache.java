package metiers;

public class Hache extends Armes {
    private String nom;
    private int degats;

    public Hache(String nom, int degats) {
        this.nom = nom;
        this.degats = degats;
    }

    public String getNom() {
        return nom;
    }

    public int getDegats() {
        return degats;
    }
    
}
