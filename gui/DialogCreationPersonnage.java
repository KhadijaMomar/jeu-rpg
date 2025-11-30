package gui;

import application.JeuRPG;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogCreationPersonnage extends JDialog {

    private final JeuRPG jeu;

    public DialogCreationPersonnage(JeuRPG jeu) {
        // Modale: bloque le reste de l'application jusqu'à ce qu'il soit fermé
        super((JFrame)null, "Création de Personnage", true); 
        this.jeu = jeu;

        setLayout(new FlowLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
        setSize(400, 150);
        setLocationRelativeTo(null); 
        
        JLabel mainLabel = new JLabel("Entrez le nom de votre héros :");
        JTextField nomField = new JTextField(20);
        JButton startButton = new JButton("DÉMARRER L'AVENTURE");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText().trim();
                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un nom.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 1. Initialiser la logique du jeu
                    jeu.initialiserJeu(nom);
                    // 2. Fermer la boîte de dialogue
                    setVisible(false);
                    dispose(); 
                    // 3. Lancer la fenêtre principale
                    new FenetrePrincipale(jeu);
                }
            }
        });

        add(mainLabel);
        add(nomField);
        add(startButton);
        
        setVisible(true);
    }
}