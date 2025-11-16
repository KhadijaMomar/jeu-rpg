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

    // Créer une armurerie avec quelques armes disponibles
    Armurerie armurerie = new Armurerie(
        new Marteau("Marteau basique", 5),
        new Arc("Arc en bois", 3),
        new Hache("Hache rouillée", 4)
    );

    // Prix (gérés dans main pour éviter de modifier les classes d'armes existantes)
    int[] prixArmurerie = new int[] { 20, 15, 18 }; // marteau, arc, hache

        boolean continuer = true;
        while (continuer) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Afficher les statistiques");
            System.out.println("2. Combattre un monstre");
            System.out.println("3. Visiter l'armurerie");
            System.out.println("4. Quitter");
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
                    // Menu de l'armurerie
                    System.out.println("\n--- ARMURERIE ---");
                    System.out.println("1. Voir les armes");
                    System.out.println("2. Acheter une arme");
                    System.out.println("3. Retour");
                    System.out.print("Choix : ");
                    int choixA = sc.nextInt();

                    Armes[] produits = new Armes[] { armurerie.getMarteau(), armurerie.getArc(), armurerie.getHache() };

                    if (choixA == 1) {
                        System.out.println("Armes disponibles :");
                        for (int i = 0; i < produits.length; i++) {
                            System.out.println((i+1) + ". " + produits[i].getNom() + " (Degats: " + produits[i].getDegats() + ") - Prix: " + prixArmurerie[i] + " pièces");
                        }
                    } else if (choixA == 2) {
                        System.out.println("Quelle arme voulez-vous acheter ? (1-3)");
                        for (int i = 0; i < produits.length; i++) {
                            System.out.println((i+1) + ". " + produits[i].getNom() + " - Prix: " + prixArmurerie[i]);
                        }
                        System.out.print("Votre choix : ");
                        int choixProduit = sc.nextInt();
                        if (choixProduit < 1 || choixProduit > produits.length) {
                            System.out.println("Choix invalide.");
                        } else {
                            int idx = choixProduit - 1;
                            int prix = prixArmurerie[idx];
                            Armes a = produits[idx];
                            if (joueur.getArgent() < prix) {
                                System.out.println("Vous n'avez pas assez d'argent (vous avez " + joueur.getArgent() + ").");
                            } else {
                                // Retirer l'argent et appliquer l'arme
                                boolean payé = joueur.retirerArgent(prix);
                                if (payé) {
                                    joueur.equiperArme(a);
                                    System.out.println("Achat réussi : " + a.getNom() + " (coût : " + prix + ")");
                                } else {
                                    System.out.println("Erreur lors du paiement.");
                                }
                            }
                        }
                    }
                }

                case 4 -> {
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
