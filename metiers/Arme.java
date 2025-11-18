
package metiers;

public abstract class Arme {
	private String nom;
	private int degats;
	
	public Arme(String nom, int degats) {
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