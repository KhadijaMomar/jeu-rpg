package metiers;
public abstract class Armes {
	/** Retourne le nom de l'arme */
	public abstract String getNom();

	/** Retourne les dégâts apportés par l'arme */
	public abstract int getDegats();

	@Override
	public String toString() {
		return getNom() + " (Degats: " + getDegats() + ")";
	}
}