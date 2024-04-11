package jeuAssemblage.controleur;

import jeuAssemblage.vue.*;
import java.awt.event.*;
import java.awt.*;

public class MouseInputBoutons extends MouseAdapter {

    private PlateauPuzzleVue vuePlateau;

    public MouseInputBoutons(PlateauPuzzleVue vue) {
        this.vuePlateau = vue;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        BoutonPerso b = (BoutonPerso) e.getSource();
        b.setColor(new Color(0,203,0));
        b.repaint();
	}

    @Override
    public void mouseExited(MouseEvent e) {
        BoutonPerso b = (BoutonPerso) e.getSource();
        b.setColor(Color.BLACK);
        b.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        BoutonPerso b = (BoutonPerso) e.getSource();
        if(b.getName() == "Terminer la partie") {
            vuePlateau.setState(PlateauPuzzleVue.VueState.GAME_FINISH);
            vuePlateau.repaint();
            b.setText("Le robot calcule une solution...");
            b.repaint();
        }
    }
}
