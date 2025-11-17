package metiers;


public abstract class Armes {
	private String nom;
	private int degats;

	
	public Armes(String nom, int degats) {
		this.nom = nom;
		this.degats = degats;
	}

	public String getNom() {
		return nom;
	}

	public int getDegats() {
		return degats;
	}

	@Override
	public String toString() {
		return getNom() + " (Degats: " + getDegats() + ")";
	}
}