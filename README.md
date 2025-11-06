# 🎮 Mini Jeu RPG en Java — JeuRPG

## 🧭 Description
Ce projet est un **mini jeu de rôle (RPG)** en Java, jouable dans la console.  
Le joueur incarne un héros qui affronte des monstres pour gagner de l’expérience, de l’or et augmenter de niveau.  
Le but est d’expérimenter les **concepts de la programmation orientée objet (POO)** : héritage, encapsulation, et interaction entre classes.

---

## ⚙️ Fonctionnalités principales
- 👤 **Création du personnage** : le joueur choisit son nom.
- ⚔️ **Système de combat** :
  - Combat au tour par tour entre le joueur et un monstre aléatoire.
  - Gagner de l’XP et de l’argent après une victoire.
- 📊 **Progression du joueur** :
  - Gain de niveaux avec augmentation des statistiques.
- 💰 **Économie simple** :
  - Le joueur gagne des pièces d’or après chaque combat.
- 🧩 **Menu interactif dans la console** :
  - Afficher les statistiques du joueur.
  - Combattre un monstre.
  - Quitter le jeu.

---

## 🏗️ Structure du projet

jeu-rpg/
│
├── application/
│ └── JeuRPG.java # Classe principale contenant le main()
│
└── metiers/
├── Personnage.java # Classe abstraite représentant un personnage
├── Joueur.java # Classe représentant le joueur (hérite de Personnage)
└── Monstre.java # Classe représentant un monstre (hérite de Personnage)