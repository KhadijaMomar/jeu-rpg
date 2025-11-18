
package application;

import metiers.Arc;
import metiers.Arme;
import metiers.Hache;
import metiers.Joueur;
import metiers.Marteau;
import metiers.Donjon;  
import metiers.PotionDeVie; 
import java.util.Scanner;

public class JeuRPG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("------------------------------------------");
        System.out.println("   Bienvenue dans le Donjon des Ombres !   ");
        System.out.println("------------------------------------------");
        
        // 1. Initialisation des Armes
        Arme marteau = new Marteau("Marteau de Thor", 15);
        
        // 2. Création du Joueur (avec l'Arme et la position de départ)
        int startX = 1;
        int startY = 1;
   
        Joueur joueur = new Joueur("Aventureux", 100, 1, 0, marteau, startX, startY); 

        // 3. Création du Donjon
        Donjon donjon = new Donjon(startX, startY); 
        
        System.out.println("Joueur: " + joueur.getNom() + " | Arme: " + joueur.getArme().getNom());

        boolean enJeu = true;
        
        while (enJeu) {
            // Affichage du statut et de la carte
            System.out.println("\n--- STATUT ---");
            joueur.afficherStats(); 
            donjon.afficherDonjon(joueur.getPos_x(), joueur.getPos_y());
            
            // Demande de déplacement
            System.out.println("Où voulez-vous aller ? (Z:Haut / S:Bas / Q:Gauche / D:Droite / X:Quitter)");
            System.out.print("Choix: ");
            String move = scanner.nextLine().toUpperCase();
            
            if (move.equals("X")) {
                enJeu = false;
                break;
            }
            
            // Déplacement
            boolean deplacementValide = donjon.deplacerJoueur(joueur, move);
            
            if (deplacementValide) {
                // Gestion de l'événement à la nouvelle position
                enJeu = donjon.gestionEvenement(joueur, scanner); 
            }
            
            if (joueur.estMort()) {
                enJeu = false;
            }
        }

        System.out.println("\n--- JEU TERMINÉ ---");
        if (joueur.estMort()) {
             System.out.println("Votre aventure s'arrête ici. Game Over.");
        } else if (!enJeu) {
             System.out.println("Merci d'avoir joué !");
        }
        
        scanner.close();
    }
}