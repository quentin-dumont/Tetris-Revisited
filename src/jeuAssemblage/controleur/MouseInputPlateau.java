package jeuAssemblage.controleur;

import pieces.*;
import jeuAssemblage.modele.*;
import jeuAssemblage.vue.*;
import java.awt.event.*;

public class MouseInputPlateau extends MouseAdapter {
	PlateauPuzzleVue vue;
	PlateauPuzzle jeu;
	int[] lastClickPosition, dragPosition;
	boolean isClickDown;

	public MouseInputPlateau(PlateauPuzzleVue vue, PlateauPuzzle jeu) {
		this.vue = vue;
		this.jeu = jeu;
		this.lastClickPosition = new int[2];
		this.dragPosition = new int[2];
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		PiecePuzzle selected = this.jeu.getSelectedPiece();
		if(vue.getState() == PlateauPuzzleVue.VueState.IN_GAME && isClickDown && selected != null) {
			int[] posSelect = selected.getPosition();
			dragPosition[0] = (posSelect[1] - (selected.getLargeur() / 2 - (selected.getLargeur() + 1) % 2)) * vue.getSizeCase() + (e.getX() - lastClickPosition[0]);
			dragPosition[1] = (posSelect[0] - (selected.getHauteur() / 2 - (selected.getHauteur() + 1) % 2)) * vue.getSizeCase() + (e.getY() - lastClickPosition[1]);
			this.vue.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX(), my = e.getY();
		isClickDown = true;
		int cx = mx / vue.getSizeCase(), cy = my / vue.getSizeCase();
		this.jeu.selectionnerPiece(cy, cx);
		PiecePuzzle selected = jeu.getSelectedPiece();
		if(selected != null)
		{
			this.lastClickPosition[0] = mx;
			this.lastClickPosition[1] = my;

			this.dragPosition[0] = (selected.getPosition()[1] - (selected.getLargeur() / 2 - (selected.getLargeur()+1) % 2)) * vue.getSizeCase() + (e.getX() - lastClickPosition[0]);
			this.dragPosition[1] = (selected.getPosition()[0] - (selected.getHauteur() / 2 - (selected.getHauteur()+1) % 2)) * vue.getSizeCase() + (e.getY() - lastClickPosition[1]);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isClickDown = false;
		PiecePuzzle selected = jeu.getSelectedPiece();
		if(selected != null) {
			int mx = e.getX(), my = e.getY();
			int end_x = mx / vue.getSizeCase() - lastClickPosition[0] / vue.getSizeCase();
			int end_y = my / vue.getSizeCase() - lastClickPosition[1] / vue.getSizeCase();
			jeu.deplacerPiece(selected.getPosition()[0] + end_y, selected.getPosition()[1] + end_x);
		}
	}

	public int[] getDragPosition() {
		return this.dragPosition;
	}

	public boolean getClickDown() {
		return this.isClickDown;
	}

}
