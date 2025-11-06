package application;

import metiers.*;
import java.util.Scanner;

public class JeuRPG {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== MINI JEU RPG ===");
        System.out.print("Entrez le nom de votre héros : ");
        String nom = sc.nextLine();

        Joueur joueur = new Joueur(nom);

        boolean continuer = true;
        while (continuer) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Afficher les statistiques");
            System.out.println("2. Combattre un monstre");
            System.out.println("3. Quitter");
            System.out.print("Choix : ");
            int choix = sc.nextInt();

            switch (choix) {
                case 1 -> joueur.afficherStats();

                case 2 -> {
                    Monstre monstre = Monstre.genererAleatoire();
                    System.out.println("\nUn " + monstre.getNom() + " apparaît !");
                    combat(joueur, monstre);
                }

                case 3 -> {
                    System.out.println("Merci d’avoir joué !");
                    continuer = false;
                }

                default -> System.out.println("Choix invalide !");
            }

            if (joueur.estMort()) {
                System.out.println("💀 Vous êtes mort... Game Over !");
                continuer = false;
            }
        }

        sc.close();
    }

    public static void combat(Joueur joueur, Monstre monstre) {
        Scanner sc = new Scanner(System.in);
        while (!joueur.estMort() && !monstre.estMort()) {
            System.out.println("\n--- COMBAT ---");
            joueur.afficherStats();
            monstre.afficherStats();

            System.out.println("1. Attaquer");
            System.out.println("2. Fuir");
            System.out.print("Choix : ");
            int choix = sc.nextInt();

            if (choix == 1) {
                joueur.attaquer(monstre);
                if (!monstre.estMort()) {
                    monstre.attaquer(joueur);
                }
            } else if (choix == 2) {
                System.out.println("Vous prenez la fuite !");
                return;
            } else {
                System.out.println("Action invalide !");
            }
        }

        if (monstre.estMort()) {
            System.out.println("✅ Le " + monstre.getNom() + " est vaincu !");
            joueur.gagnerXP(monstre.getXpDonnee());
            joueur.gagnerArgent(monstre.getOrDonne());
        }
    }
}
