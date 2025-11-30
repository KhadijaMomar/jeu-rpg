package gui;

import application.JeuRPG;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class FenetrePrincipale extends JFrame {

    private PanelJeu panelJeu; 

    public FenetrePrincipale(JeuRPG jeu) {
        setTitle("Mini RPG - Le Donjon des Ombres");
        setSize(new Dimension(800, 600)); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        setLayout(new BorderLayout()); 
        
        // AJOUT : Donne la référence à la classe principale (Contrôleur)
        jeu.setFenetrePrincipale(this); 
        
       
        this.panelJeu = new PanelJeu(jeu);
        add(this.panelJeu, BorderLayout.CENTER); 
        
        setVisible(true);
        
        this.panelJeu.updateIHM(); 
        this.panelJeu.requestFocusInWindow(); 
    }
    
    public PanelJeu getPanelJeu() {
        return panelJeu;
    }
}