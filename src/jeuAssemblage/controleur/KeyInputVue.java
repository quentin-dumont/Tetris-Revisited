package jeuAssemblage.controleur;

import java.awt.event.*;
import jeuAssemblage.modele.*;

public class KeyInputVue implements KeyListener {
    private PlateauPuzzle jeu;

    public KeyInputVue(PlateauPuzzle jeu) {
        this.jeu = jeu;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            jeu.tournerPiece(true);
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            jeu.tournerPiece(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
