package gui;

import application.JeuRPG;
import metiers.Joueur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner; 

/**
 * Contient les différents éléments de l'IHM (stats, carte, log) et gère le KeyListener.
 */
public class PanelJeu extends JPanel implements KeyListener {

    private final JeuRPG jeu;
    private JLabel labelStats;       
    private JTextArea zoneTexteLog;  
    private JTextArea zoneCarte;     
    
    private Scanner scannerSimule = new Scanner(System.in); 

    public PanelJeu(JeuRPG jeu) {
        this.jeu = jeu;
        setLayout(new BorderLayout(5, 5)); 
        
        initialiserStatistiques(); 
        initialiserCarte();        
        initialiserLog();          
        
        this.addKeyListener(this);
        this.setFocusable(true); 
    }

    private void initialiserStatistiques() {
        labelStats = new JLabel("Stats: En attente..."); 
        labelStats.setFont(new Font("Monospaced", Font.BOLD, 14));
        labelStats.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
        this.add(labelStats, BorderLayout.NORTH);
    }
    
    private void initialiserCarte() {
        zoneCarte = new JTextArea();
        zoneCarte.setFont(new Font("Monospaced", Font.BOLD, 16));
        zoneCarte.setEditable(false);
        zoneCarte.setBackground(Color.DARK_GRAY);
        zoneCarte.setForeground(Color.GREEN);
        
        JScrollPane scrollPane = new JScrollPane(zoneCarte);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Carte du Donjon"));
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void initialiserLog() {
        zoneTexteLog = new JTextArea(10, 30); 
        zoneTexteLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        zoneTexteLog.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(zoneTexteLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Journal d'Événements"));
        this.add(scrollPane, BorderLayout.SOUTH);
    }
    
    // --- Méthodes de mise à jour de l'IHM ---
    
    public void logMessage(String message) {
        zoneTexteLog.append(message + "\n");
        zoneTexteLog.setCaretPosition(zoneTexteLog.getDocument().getLength());
    }

    public void updateIHM() {
        Joueur joueur = jeu.getJoueur();
        
        // 1. Mise à jour des statistiques
        String statsHTML = String.format(
            "<html>Nom: <b>%s</b> | Niveau: %d | PV: <font color='%s'>%d/%d</font> | ATK: %d | Argent: %d | XP: %d</html>",
            joueur.getNom(), joueur.getNiveau(), 
            joueur.getPv() < joueur.getPvMax() / 2 ? "red" : "green", 
            joueur.getPv(), joueur.getPvMax(),
            joueur.getAttaque() + (joueur.getArme() != null ? joueur.getArme().getDegats() : 0), 
            joueur.getArgent(), joueur.getExperience() 
        );
        labelStats.setText(statsHTML);
        
        // 2. Mise à jour de la carte 
        zoneCarte.setText(jeu.getDonjon().afficherDonjonGUI(joueur.getPos_x(), joueur.getPos_y()));
        
        this.validate(); 
    }

    // --- Gestion des événements clavier ---

    @Override
    public void keyTyped(KeyEvent e) { /* Non utilisé */ }

    @Override
    public void keyPressed(KeyEvent e) {
        // Empêche le mouvement si le joueur est mort (sécurité)
        if (jeu.getJoueur().estMort()) return; 

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z: 
            case KeyEvent.VK_W:
                gererDeplacement('H');
                break;
            case KeyEvent.VK_S: 
                gererDeplacement('B');
                break;
            case KeyEvent.VK_Q: 
            case KeyEvent.VK_A:
                gererDeplacement('G');
                break;
            case KeyEvent.VK_D: 
                gererDeplacement('D');
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { /* Non utilisé */ }
    
    private void gererDeplacement(char direction) {
        Joueur joueur = jeu.getJoueur();
        logMessage("\nTentative de déplacement vers " + direction + "...");
        
        // CORRECTION: Appel à la bonne méthode (doit prendre le joueur en argument)
        char caseCible = jeu.getDonjon().verifierEtDeplacerJoueur(joueur, direction); 
        
        int cibleColonne = jeu.getDonjon().getCibleX(); 
        int cibleLigne = jeu.getDonjon().getCibleY(); 
        
        switch (caseCible) {
            case '.':
                logMessage("Chemin dégagé.");
                break;
            case 'O': // Obstacle
                logMessage("Un obstacle vous bloque! Combattez pour le détruire.");
                jeu.getJoueur().attaquer(); 
                
                logMessage("L'obstacle a été détruit et la voie est libre.");
                jeu.getDonjon().retirerElement(cibleColonne, cibleLigne); 
                jeu.getDonjon().finaliserMouvementSurCible(joueur); 
                break;
            case 'M': // Monstre
                logMessage("Monstre rencontré ! Lancement du combat...");
                
                new DialogCombat(jeu, cibleColonne, cibleLigne); 
                
                // Le DialogueCombat gère la victoire, le déplacement, les récompenses et l'IHM
                break;
                
            case 'S':
                logMessage("Vous avez atteint la sortie !");
                JOptionPane.showMessageDialog(this, "🎉 Félicitations, vous avez trouvé la sortie !", "VICTOIRE !", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); 
                break;
            default: 
                logMessage("Mouvement annulé (mur ou obstacle indépassable).");
                break;
        }
        
        updateIHM();
    }
}