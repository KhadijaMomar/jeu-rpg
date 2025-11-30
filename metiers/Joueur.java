package metiers;

import java.util.Random;

public class Joueur extends Personnage {
    private int xp;
    private int argent;
    private int niveau;
    private Arme armeEquipee;
    private int pos_x; 
    private int pos_y; 
    
  
    public Joueur(String nom, int pv, int niveau, int experience, Arme arme, int pos_x, int pos_y) {
        super(nom, pv, 15); 
        this.xp = experience;
        this.argent = 50; 
        this.niveau = niveau;
        this.armeEquipee = arme;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        System.out.println("Bienvenue, " + nom + " ! L’aventure commence !");
    }

  
    @Override
    public int attaquer() {
        Random rand = new Random();
        int degatBase = this.attaque + this.armeEquipee.getDegats();
        int degatInflige = degatBase + rand.nextInt(3); 
        System.out.println(this.getNom() + " attaque avec son " + this.armeEquipee.getNom() + " et inflige " + degatInflige + " dégâts !");
        return degatInflige;
    }
    
    public void utiliserItem(PotionDeVie potion) { 
        this.pointsVie += potion.getSoin();
        if (this.pointsVie > this.pvMax) this.pointsVie = this.pvMax;
        System.out.println(this.nom + " utilise une " + potion.getNom() + " et récupère " + potion.getSoin() + " PV !");
    }
    
    public void gagnerExperience(int montant) { 
        xp += montant;
        System.out.println(this.nom + " gagne " + montant + " XP !");
        if (xp >= niveau * 100) {
            niveau++;
            this.pvMax += 20;
            this.pointsVie = this.pvMax;
            this.attaque += 5;
            System.out.println("*** Niveau " + niveau + " atteint ! ***");
        }
    }
    
    public void gagnerArgent(int montant) {
        argent += montant;
        System.out.println(this.nom + " gagne " + montant + " pièces d'argent !");
    }

    public Arme getArme() { return armeEquipee; }
    public int getExperience() { return xp; }
    public int getArgent() { return argent; } 
    
    public int getNiveau() { return niveau; }
    public int getPos_x() { return pos_x; }
    public int getPos_y() { return pos_y; }
    public void setPos_x(int x) { this.pos_x = x; }
    public void setPos_y(int y) { this.pos_y = y; }

    @Override
    public void afficherStats() {
        System.out.println("=== Stats du joueur ===");
        System.out.println("Nom : " + nom);
        System.out.println("Niveau : " + niveau);
        System.out.println("PV : " + pointsVie + "/" + pvMax);
        System.out.println("Attaque : " + this.attaque + " (+ " + this.armeEquipee.getDegats() + " avec " + this.armeEquipee.getNom() + ")");
    }
}