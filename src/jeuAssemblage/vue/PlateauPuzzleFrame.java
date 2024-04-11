package jeuAssemblage.vue;

import jeuAssemblage.controleur.MouseInputBoutons;
import jeuAssemblage.modele.*;
import javax.swing.JFrame;
import java.awt.*;

public class PlateauPuzzleFrame extends JFrame implements BoucleJouable {

	private PlateauPuzzleVue vuePlateau;
	private static int LARGEUR_GRILLE = 700;

	public PlateauPuzzleFrame(PlateauPuzzle jeu) {
		super("Jeu Assemblage");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Container cp = this.getContentPane();
		this.vuePlateau = new PlateauPuzzleVue(jeu, LARGEUR_GRILLE);
		gbc.insets = new Insets(30, 30, 30, 30);
		cp.add(vuePlateau, gbc);

		BoutonPerso valide = new BoutonPerso("Terminer la partie", 600, 40);
		gbc.gridy = 1;
		valide.addMouseListener(new MouseInputBoutons(this.vuePlateau));
		cp.add(valide, gbc);

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	@Override
	public boolean estTermine() {
		return this.vuePlateau.finJeu();
	}

	@Override
	public void terminer() {
		this.vuePlateau.setState(PlateauPuzzleVue.VueState.GAME_FINISH);
	}

	@Override
	public void quitter() {
		this.dispose();
	}

	@Override
	public BoucleJouable rejouer() {
		return new PlateauPuzzleFrame(this.vuePlateau.getJeu());
	}

}
