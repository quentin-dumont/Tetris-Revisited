package pieces;

import java.util.*;
import java.awt.*;
import java.io.Serializable;

public class PiecePuzzle implements Serializable, Cloneable {

    private static final Color[] COULEURS = {Color.RED, Color.ORANGE, Color.PINK, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};
    private static final String[] SYMBOLES = {"/","0","X","H","O"};
    public static final String VIDE = "_";
    public static final String PLEIN = "/";

    protected boolean[][][] tableaux;
    protected final int hauteur;
    protected final int largeur;
    private final int[] milieu;
    private int[] position; //dans le jeu, position du centre de la pièce dans la grille
    private int orientation;
    private final Color couleur;
    private final String symbole;

    public PiecePuzzle(int hauteur, int largeur, StrategyPiece strategy) {
    	this.hauteur = hauteur;
        this.largeur = largeur;

        int milieuI = hauteur%2 == 0 ? hauteur/2-1 : hauteur/2;
        int milieuJ = largeur%2 == 0 ? largeur/2-1 : largeur/2;
        this.milieu = new int[] {milieuI, milieuJ};
        this.position = new int[2];
        this.orientation = 0;

        this.tableaux = strategy.generePiece(hauteur, largeur);

        Random rand = new Random();
        this.couleur = COULEURS[rand.nextInt(COULEURS.length)];
        this.symbole = SYMBOLES[rand.nextInt(SYMBOLES.length)];
    }

    @Override	
    public PiecePuzzle clone() throws CloneNotSupportedException {   
        PiecePuzzle copie = (PiecePuzzle)super.clone();
        return copie;
    }

    public int getHauteur() {
        if(this.orientation == 1 || this.orientation == 3)
          return this.largeur;
        return this.hauteur;
    }

    public int getLargeur() {
        if(this.orientation == 1 || this.orientation == 3)
          return this.hauteur;
        return this.largeur;
    }

    public Color getCouleur() {
        return this.couleur;
    }

    public String getSymbole() {
        return this.symbole;
    }

    public int[] getMilieu() {
        if(this.orientation == 1 || this.orientation == 3)
          return new int[] {this.milieu[1], this.milieu[0]};
        return this.milieu;
    }

    public int[] getPosition() {
        return this.position;
    }

    public void setPosition(int[] newPos) {
        this.position = newPos;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation % 4;
    }

    public boolean estOccupee(int i, int j) {
        return this.tableaux[this.orientation][i][j] == true;
    }

    public void tourner(boolean antiHoraire)
    {
        this.orientation = antiHoraire ? (this.orientation-1)%4 : (this.orientation+1)%4;
        if(this.orientation < 0){ this.orientation += 4; }
    }

    public String toString() {
        String ch = "";
        for(int i = 0; i < this.getHauteur(); i++)
        {
            for(int j = 0; j < this.getLargeur(); j++)
            {
                ch += this.tableaux[this.orientation][i][j] ? PLEIN : VIDE;
            }
            ch += "\n";
        }
        return ch;
    }
}