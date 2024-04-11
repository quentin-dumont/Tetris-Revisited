package jeuAssemblage.vue;

import pieces.*;
import jeuAssemblage.modele.*;
import jeuAssemblage.controleur.*;

import javax.swing.JPanel;
import java.awt.*;

public class PlateauPuzzleVue extends JPanel implements Ecouteur {

	public static enum VueState {
		IN_GAME, GAME_FINISH
	};

	private PlateauPuzzle jeu;
	private final int sizeCase;

	private MouseInputPlateau button_mouse;
	private VueState state;

	public VueState getState() {
		return this.state;
	}

	public void setState(VueState v) {
		this.state = v;
	}

	public PlateauPuzzle getJeu() {
		return this.jeu;
	}

	public boolean finJeu() {
		if(state == VueState.GAME_FINISH)
		{
			this.removeMouseListener(this.button_mouse);
			this.removeMouseMotionListener(this.button_mouse);
			this.jeu.selectionnerPiece(null);
			return true;
		}
		return false;
	}

	public int getSizeCase() {
		return this.sizeCase;
	}

	public PlateauPuzzleVue(PlateauPuzzle jeu, int largeur) {
		this.jeu = jeu;
		this.jeu.ajoutEcouteur(this);

		this.state = VueState.IN_GAME;

		this.sizeCase = (largeur / (Math.max(jeu.getLargeur(), jeu.getHauteur())));
		this.setPreferredSize(new Dimension(1+this.sizeCase*jeu.getLargeur(), 1+this.sizeCase*jeu.getHauteur()));
		this.button_mouse = new MouseInputPlateau(this, this.jeu);


		addKeyListener(new KeyInputVue(this.jeu));
		addMouseListener(this.button_mouse);
		addMouseMotionListener(this.button_mouse);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public void drawPiece(Graphics g, PiecePuzzle p, int x, int y) {
		for(int i = 0; i < p.getHauteur(); i++) {
			for(int j = 0; j < p.getLargeur(); j++) {
				if(p.estOccupee(i, j)) {
					g.fillRect(x + j * sizeCase, y + i * sizeCase, sizeCase, sizeCase);
				}
			}
		}
	}

	public void drawGrid(Graphics g) {
		for(int i = 0; i < jeu.getHauteur(); i++) {
			for(int j = 0; j < jeu.getLargeur(); j++) {
				g.drawRect(j * sizeCase, i * sizeCase, sizeCase, sizeCase);
			}
		}
	}

	public void drawScoreRect(Graphics2D g) {
		int[] posRect = jeu.rectangleMinimal();
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.RED);
		g.drawRect(posRect[1] * sizeCase, posRect[2] * sizeCase, (posRect[3] - posRect[1]) * sizeCase, (posRect[0] - posRect[2]) * sizeCase);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		
		for(PiecePuzzle p : jeu.getListePieces()) {
			int[] pos = p.getPosition();
			PiecePuzzle selected = this.jeu.getSelectedPiece();
			if(selected != null && pos[0] == selected.getPosition()[0] && pos[1] == selected.getPosition()[1]) {
				g.setColor(new Color(p.getCouleur().getRed(), p.getCouleur().getGreen(), p.getCouleur().getBlue(), 150));
				int[] tmp = button_mouse.getDragPosition();
				if(button_mouse.getClickDown()) {
					drawPiece(g, selected, tmp[0], tmp[1]);
				}
			}
			g.setColor(p.getCouleur());
			drawPiece(g, p, (pos[1] - (p.getLargeur() / 2 - (p.getLargeur()+1) % 2)) * sizeCase, (pos[0] - (p.getHauteur() / 2 - (p.getHauteur()+1) % 2)) * sizeCase);
			g.setColor(Color.BLACK);
		}
		drawGrid(g);

		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if (state == VueState.GAME_FINISH) {
			drawScoreRect(g2);
		}
		g.setColor(Color.BLACK);
	}

	@Override
	public void modeleMisAJour(Object source) {
		this.repaint();
	}

}