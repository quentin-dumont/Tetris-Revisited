package jeuAssemblage.modele;

import java.util.ArrayList;
import java.io.Serializable;
import pieces.*;

public class Config implements Serializable {

    private ArrayList<PiecePuzzle> copiePieces;
    private int hauteur;
    private int largeur;

    /**
     * Crée un objet Config permettant de représenter une partie.
     * Les pièces sont clonées pour éviter les effets de bord.
     * @param pieces liste de pièces d'une partie
     * @param hauteur nombre de lignes d'une partie
     * @param largeur nombre de colonnes d'une partie
     */
    public Config(ArrayList<PiecePuzzle> pieces, int hauteur, int largeur) {
        this.copiePieces = new ArrayList<PiecePuzzle>();
        for(int i = 0; i < pieces.size(); i++) {
            try {
                this.copiePieces.add(pieces.get(i).clone());
            } catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
        }
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**
     * Surcharge permettant de passer directement un PlateauPuzzle en paramètre.
     * @param jeu une partie
     */
    public Config(PlateauPuzzle jeu) {
        this(jeu.getListePieces(), jeu.getLargeur(), jeu.getHauteur());
    }

    public ArrayList<PiecePuzzle> getPieces() {
        return this.copiePieces;
    }
    public int getLargeur() {
        return this.largeur;
    }
    public int getHauteur() {
        return this.hauteur;
    }
}