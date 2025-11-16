package metiers;

public class Joueur extends Personnage {
    private int xp;
    private int argent;
    private int niveau;

    public Joueur(String nom) {
        super(nom, 100, 15);
        this.xp = 0;
        this.argent = 50;
        this.niveau = 1;
        System.out.println("Bienvenue, " + nom + " ! L’aventure commence !");
    }

    public void gagnerXP(int montant) {
        xp += montant;
        System.out.println(nom + " gagne " + montant + " XP !");
        if (xp >= niveau * 100) {
            niveau++;
            pvMax += 20;
            pointsVie = pvMax;
            attaque += 5;
            System.out.println("*** Niveau " + niveau + " atteint ! ***");
            afficherStats();
        }
    }

    public void gagnerArgent(int montant) {
        argent += montant;
        System.out.println(nom + " gagne " + montant + " pièces d’or. (Total: " + argent + ")");
    }

    // --- Ajoutés : méthodes d'accès à l'argent et équipement d'armes ---
    public int getArgent() { return argent; }

    public boolean retirerArgent(int montant) {
        if (montant <= argent) {
            argent -= montant;
            return true;
        }
        return false;
    }

    /**
     * Équipe une arme : applique son bonus de dégâts à l'attaque du joueur.
     * Ceci est simple : on ajoute les dégâts de l'arme à l'attaque actuelle.
     */
    public void equiperArme(Armes a) {
        if (a == null) return;
        attaque += a.getDegats();
        System.out.println(nom + " équipe " + a.getNom() + " (ATK = " + attaque + ")");
    }

    @Override
    public void afficherStats() {
        System.out.println("=== Stats du joueur ===");
        System.out.println("Nom : " + nom);
        System.out.println("Niveau : " + niveau);
        System.out.println("PV : " + pointsVie + "/" + pvMax);
        System.out.println("Attaque : " + attaque);
        System.out.println("XP : " + xp);
        System.out.println("Argent : " + argent);
        System.out.println("========================");
    }
}
