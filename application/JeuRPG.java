package application;

import metiers.*;
import gui.DialogCreationPersonnage; 
import gui.FenetrePrincipale; 

import javax.swing.SwingUtilities;

/**
 * Classe principale qui gère la logique du jeu (Contrôleur).
 */
public class JeuRPG {
    
    private Joueur joueur;
    private Donjon donjon;
    
    
    private FenetrePrincipale fenetrePrincipale; 

    public Joueur getJoueur() { return joueur; }
    public Donjon getDonjon() { return donjon; }
    public FenetrePrincipale getFenetrePrincipale() { return fenetrePrincipale; }
    public void setFenetrePrincipale(FenetrePrincipale fp) { this.fenetrePrincipale = fp; }


    public static void main(String[] args) {
        // La méthode standard pour lancer une application Swing
        SwingUtilities.invokeLater(() -> {
            JeuRPG jeu = new JeuRPG();
            
            // Lancer le dialogue de création du personnage avant tout
            new DialogCreationPersonnage(jeu); 
        });
    }

    /**
     * Initialise le joueur et le donjon, appelé par la GUI après le choix du nom.
     */
    public void initialiserJeu(String nomJoueur) {
        // Initialisation de l'arme de base
        Arme poings = new Arc("Arc de base", 5);

    
        this.joueur = new Joueur(nomJoueur, 100, 1, 0, poings, 1, 1); 

       
        this.donjon = new Donjon();
    }
}