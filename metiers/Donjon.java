
package metiers;

import java.util.Random;
import java.util.Scanner;

public class Donjon {

    private char[][] matrice; 
    private final int TAILLE = 8;
    private final char VIDE = '.';
    private final char MONSTRE = 'M';
    private final char OBSTACLE = 'O'; 
    private final char SORTIE = 'S';
    private final char JOUEUR_SYMBOLE = 'P'; 

    public Donjon(int startX, int startY) { 
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
    
        matrice[TAILLE - 2][TAILLE - 2] = SORTIE; 
        matrice[1][1] = VIDE; 
    }
    
    public void afficherDonjon(int joueurX, int joueurY) {
        System.out.println("\n--- CARTE DU DONJON ---");
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (i == joueurY && j == joueurX) {
                    System.out.print(JOUEUR_SYMBOLE + " ");
                } else {
                    System.out.print(matrice[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Légende: P=Joueur, M=Monstre, S=Sortie, .=Vide");
    }
    
    
    public boolean deplacerJoueur(Joueur joueur, String direction) {
        int newX = joueur.getPos_x();
        int newY = joueur.getPos_y();
        
        switch (direction) {
            case "Z": newY--; break;
            case "S": newY++; break;
            case "Q": newX--; break;
            case "D": newX++; break;
            default: return false;
        }

        if (newX < 0 || newX >= TAILLE || newY < 0 || newY >= TAILLE) {
            System.out.println("Vous vous heurtez à un mur invisible.");
            return false;
        }

        char caseCible = matrice[newY][newX];
        
        if (caseCible == VIDE || caseCible == SORTIE) {
            joueur.setPos_x(newX);
            joueur.setPos_y(newY);
            return true;
        } else if (caseCible == OBSTACLE) {
            System.out.println("Un obstacle vous bloque le passage ('O').");
            return false;
        } else if (caseCible == MONSTRE) {
            joueur.setPos_x(newX); 
            joueur.setPos_y(newY);
            return true; 
        }

        return false;
    }
    

    public boolean gestionEvenement(Joueur joueur, Scanner scanner) {
        
        char evenement = this.matrice[joueur.getPos_y()][joueur.getPos_x()];

        switch (evenement) {
            case MONSTRE: 
                System.out.println("\n⚔️ ATTENTION ! Un monstre apparaît ! Préparez-vous au combat !");
                Monstre monstre = genererMonstreAleatoire(joueur.getNiveau());
                boolean victoire = lancerCombat(joueur, monstre, scanner);
                
                if (victoire) {
                    this.matrice[joueur.getPos_y()][joueur.getPos_x()] = VIDE; 
                }
                return victoire; 
                
            case SORTIE: 
                System.out.println("\n🎉 VOUS AVEZ TROUVÉ LA SORTIE DU DONJON ! Victoire !");
                return false; 
                
            case OBSTACLE: 
                System.out.println("Un obstacle vous bloque! Pour le moment, il est détruit automatiquement.");
                this.matrice[joueur.getPos_y()][joueur.getPos_x()] = VIDE;
                return true; 
                
            case VIDE: 
                return true; 
                
            default:
                return true;
        }
    }
    

    private boolean lancerCombat(Joueur joueur, Monstre monstre, Scanner scanner) {
        System.out.println("--- DÉBUT DU COMBAT ---");

        while (joueur.getPv() > 0 && monstre.getPv() > 0) {
            System.out.println("\nQue voulez-vous faire ? (1: Attaquer / 2: Fuir)");
            String choix = scanner.nextLine();

            if (choix.equals("1")) {
               
                monstre.recevoirDegats(joueur.attaquer()); 
                
                if (monstre.getPv() <= 0) {
                    System.out.println("\n--- VICTOIRE ! ---");
                    joueur.gagnerExperience(monstre.getExperience());
                    return true;
                }

             
                joueur.recevoirDegats(monstre.attaquer()); 
                
                if (joueur.getPv() <= 0) {
                    System.out.println("\n--- DÉFAITE ! ---");
                    return false; 
                }

            } else if (choix.equals("2")) {
                Random rand = new Random();
                if (rand.nextDouble() < 0.3) { 
                    System.out.println("🏃 Vous avez réussi à fuir !");
                    return true;
                } else {
                    System.out.println("❌ Échec de la fuite ! Le monstre attaque !");
                    joueur.recevoirDegats(monstre.attaquer());
                    if (joueur.getPv() <= 0) {
                        System.out.println("\n--- DÉFAITE ! ---");
                        return false;
                    }
                }
            } else {
                System.out.println("Choix invalide. Veuillez entrer 1 ou 2.");
            }
        }
        return joueur.getPv() > 0;
    }
    
    private Monstre genererMonstreAleatoire(int niveauJoueur) {
        int basePV = 40 + (niveauJoueur * 5);
        int baseATK = 8 + (niveauJoueur * 2);
        int baseXP = 30 + (niveauJoueur * 5);
        
    
        return new Monstre("Gobelin du Donjon", basePV, baseATK, baseXP);
    }
}