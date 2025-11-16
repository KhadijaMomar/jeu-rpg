package metiers;

public class Arc extends Armes {
    private String nom;
    private int degats;

    public Arc(String nom, int degats) {
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
