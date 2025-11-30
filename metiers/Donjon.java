package metiers;

import java.util.Random;

public class Donjon {

    private char[][] matrice; 
    private final int TAILLE = 8;
    private final char VIDE = '.';
    private final char MONSTRE = 'M';
    private final char OBSTACLE = 'O'; 
    private final char SORTIE = 'S';
    private final char JOUEUR_SYMBOLE = 'P'; 
    
    private int cibleX; 
    private int cibleY; 

    public Donjon() { 
        initialiserMatrice();
    }

    private void initialiserMatrice() {
        matrice = new char[TAILLE][TAILLE];
        Random rand = new Random();

        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                int r = rand.nextInt(100);
                if (r < 15) { 
                    matrice[i][j] = MONSTRE;
                } else if (r < 25) { 
                    matrice[i][j] = OBSTACLE; 
                } else { 
                    matrice[i][j] = VIDE;
                }
            }
        }
    
        matrice[TAILLE - 1][TAILLE - 1] = SORTIE;
        matrice[1][1] = VIDE; 
    }

    public void afficherDonjon() {
    }
    
    // Affichage pour la GUI
    public String afficherDonjonGUI(int joueurX, int joueurY) { 
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (i == joueurY && j == joueurX) {
                    sb.append(JOUEUR_SYMBOLE).append(" ");
                } else {
                    sb.append(matrice[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Vérifie si le déplacement est valide. 
     * Déplace le joueur UNIQUEMENT si la case est VIDE.
     * Pour Monstre ou Obstacle, elle retourne la case cible.
     */
    public char verifierEtDeplacerJoueur(Joueur joueur, char direction) {
        int newX = joueur.getPos_x();
        int newY = joueur.getPos_y();

        switch (direction) {
            case 'H':
                newY--;
                break;
            case 'B':
                newY++;
                break;
            case 'G':
                newX--;
                break;
            case 'D':
                newX++;
                break;
        }

        // 1. Vérification des limites de la carte
        if (newX < 0 || newX >= TAILLE || newY < 0 || newY >= TAILLE) {
            return 'X'; 
        }
        
        // Sauvegarde de la cible avant déplacement (pour la GUI)
        this.cibleX = newX;
        this.cibleY = newY;
        
        char caseCible = matrice[newY][newX];

        // 2. Déplacement immédiat si vide
        if (caseCible == VIDE) {
            joueur.setPos_x(newX);
            joueur.setPos_y(newY);
        }
        
        // 3. Retourne le type de case. La GUI gère l'événement (Combat, Destruction).
        return caseCible;
    }
    
    /**
     * Finalise le mouvement du joueur sur la case qui a été libérée (après combat/destruction).
     */
    public void finaliserMouvementSurCible(Joueur joueur) {
        joueur.setPos_x(cibleX);
        joueur.setPos_y(cibleY);
    }
    
    public void retirerElement(int x, int y) {
        if (y >= 0 && y < TAILLE && x >= 0 && x < TAILLE) {
            matrice[y][x] = VIDE;
        }
    }
  
    public Monstre genererMonstreAleatoire(int niveauJoueur) {
        Random rand = new Random();
        int basePV = 40 + (niveauJoueur * 5);
        int baseATK = 8 + (niveauJoueur * 2);
        int baseXP = 30 + (niveauJoueur * 5);
        int baseOR = 10 + rand.nextInt(niveauJoueur * 5); 

        return new Monstre("Gobelin du Donjon", basePV, baseATK, baseXP, baseOR); 
    }
    
    // =================================================================
    // GETTERS UTILITAIRES POUR LE GUI
    // =================================================================
    
    public int getTAILLE() { return TAILLE; }
    public int getCibleX() { return cibleX; }
    public int getCibleY() { return cibleY; }
    public char getMONSTRE() { return MONSTRE; }
    public Monstre getMonstre() {
        return new Monstre("Gobelin", 50, 10, 30, 10);
    }
}