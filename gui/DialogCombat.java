package gui;

import application.JeuRPG;
import metiers.Joueur;
import metiers.Monstre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DialogCombat extends JDialog {

    private final JeuRPG jeu;
    private final Joueur joueur;
    private final Monstre monstre;
    private final int monstreX;
    private final int monstreY;

    private JLabel statsJoueurLabel;
    private JLabel statsMonstreLabel;
    private JTextArea logArea;
    private JButton attaquerButton;
    
    private PanelJeu panelJeuReference; 

    /**
     * Crée et affiche le dialogue de combat.
     * @param jeu L'objet JeuRPG pour l'accès aux données.
     * @param monstreX La colonne du monstre (sera supprimée en cas de victoire).
     * @param monstreY La ligne du monstre (sera supprimée en cas de victoire).
     */
    public DialogCombat(JeuRPG jeu, int monstreX, int monstreY) {
        super((Frame) null, "⚔️ Combat contre Monstre", true);
        this.jeu = jeu;
        this.joueur = jeu.getJoueur();
        
        this.monstre = jeu.getDonjon().genererMonstreAleatoire(joueur.getNiveau()); 
        this.monstreX = monstreX;
        this.monstreY = monstreY;
        
        this.panelJeuReference = jeu.getFenetrePrincipale().getPanelJeu();

        initialiserComposants();
        setTitle("⚔️ Combat contre " + monstre.getNom());
        updateStats();
        logMessage("Un " + monstre.getNom() + " apparaît ! Préparez-vous au combat.");

        pack(); 
        setLocationRelativeTo(null);
        setVisible(true); 
    }

    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));

        // --- Nord : Statistiques ---
        JPanel statsPanel = new JPanel(new GridLayout(2, 1));
        statsJoueurLabel = new JLabel();
        statsMonstreLabel = new JLabel();
        statsPanel.add(statsJoueurLabel);
        statsPanel.add(statsMonstreLabel);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(statsPanel, BorderLayout.NORTH);

        // --- Centre : Journal de Combat ---
        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBorder(BorderFactory.createTitledBorder("Journal de Combat"));
        JScrollPane scrollLog = new JScrollPane(logArea);
        add(scrollLog, BorderLayout.CENTER);

        // --- Sud : Actions ---
        attaquerButton = new JButton("ATTAQUER !");
        attaquerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionJoueur();
            }
        });
        JPanel actionPanel = new JPanel();
        actionPanel.add(attaquerButton);
        add(actionPanel, BorderLayout.SOUTH);
    }
    
    private void logMessage(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void updateStats() {
        // Mise à jour des stats du Joueur
        String jStats = String.format("👤 %s - PV: %d/%d | ATK: %d", 
            joueur.getNom(), joueur.getPv(), joueur.getPvMax(), 
            joueur.getAttaque() + (joueur.getArme() != null ? joueur.getArme().getDegats() : 0));
        statsJoueurLabel.setText(jStats);

        // Mise à jour des stats du Monstre
        String mStats = String.format("👹 %s - PV: %d/%d | ATK: %d", 
            monstre.getNom(), monstre.getPv(), monstre.getPvMax(), monstre.getAttaque());
        statsMonstreLabel.setText(mStats);
    }

    private void actionJoueur() {
        attaquerButton.setEnabled(false); 

        // 1. Le Joueur attaque
        int degatsInfliges = joueur.attaquer();
        monstre.recevoirDegats(degatsInfliges);
        logMessage(joueur.getNom() + " inflige " + degatsInfliges + " dégâts à " + monstre.getNom() + ".");
        updateStats();

        // 2. Vérification de la victoire
        if (monstre.estMort()) {
            victoire();
            return;
        }

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionMonstre();
                ((Timer)e.getSource()).stop(); 
                attaquerButton.setEnabled(true); 
            }
        });
        timer.setRepeats(false); 
        timer.start();
    }
    
    private void actionMonstre() {
        // 1. Le Monstre attaque
        int degatsRecus = monstre.attaquer();
        joueur.recevoirDegats(degatsRecus);
        logMessage(monstre.getNom() + " inflige " + degatsRecus + " dégâts à " + joueur.getNom() + ".");
        updateStats();

        // 2. Vérification de la défaite
        if (joueur.estMort()) {
            defaite();
        }
    }

    private void victoire() {
        attaquerButton.setEnabled(false);
        int xpGagne = monstre.getExperience();
        int argentGagne = monstre.getArgentDonne(); 

        // 1. Attribution des récompenses
        joueur.gagnerExperience(xpGagne);
        joueur.gagnerArgent(argentGagne);
        
        // 2. Libérer la case du donjon et y déplacer le joueur
        jeu.getDonjon().retirerElement(monstreX, monstreY);
        jeu.getDonjon().finaliserMouvementSurCible(joueur); 

        logMessage("\n*** VICTOIRE ! ***");
        logMessage("Récompenses: " + xpGagne + " XP et " + argentGagne + " Pièces.");

        JOptionPane.showMessageDialog(this, "Vous avez vaincu " + monstre.getNom() + " !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
        
        fermerDialogue();
    }

    private void defaite() {
        attaquerButton.setEnabled(false);
        logMessage("\n*** DÉFAITE ! ***");
        logMessage("Votre aventure prend fin. Game Over.");

        JOptionPane.showMessageDialog(this, "Vous avez été vaincu. Game Over.", "Défaite", JOptionPane.ERROR_MESSAGE);
        
        // Fermer l'application
        System.exit(0);
    }

    private void fermerDialogue() {
        if (panelJeuReference != null) {
            panelJeuReference.logMessage("\nCombat terminé. Vous avez repris votre exploration.");
            panelJeuReference.updateIHM();
            panelJeuReference.requestFocusInWindow();
        }
        dispose();
    }
}