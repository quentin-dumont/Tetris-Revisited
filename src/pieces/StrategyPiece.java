package pieces;

import java.io.Serializable;
import java.util.HashMap;

public abstract class StrategyPiece implements Serializable {

    /* Définit la stratégie de génération de la pièce suivant 
     * sa forme et ses dimensions. Renvoie les grilles de booléens 
     * correspondant aux quatre orientations de la pièce.
     */
    public abstract boolean[][][] generePiece(int hauteur, int largeur);

    /*
     * Génère les orientations GAUCHE, BAS et DROITE de la pièce en fonction d'un
     * tableau de base, et renvoie les 4 tableaux correspondant aux 4 orientations. 
     * (Méthode utilisée dans generePiece)
     */
    protected boolean[][][] genereOrientations(boolean[][] base, int hauteur, int largeur)
    {
        boolean[][][] tableaux = new boolean[4][][];
        tableaux[0] = base;
        //DROITE
        boolean[][] droite = new boolean[largeur][hauteur];
        int x = 0; int y = 0;
        for(int j = 0; j < largeur; j++)
        {
        	y = 0;
            for(int i = hauteur-1; i >= 0; i--)
            {
                droite[x][y] = base[i][j] ? true : false;
                y += 1;
            }
            x += 1;
        }
        tableaux[1] = droite;

        //BAS
        boolean[][] bas = new boolean[hauteur][largeur];
        x = 0;
        for(int i = hauteur-1; i >= 0; i--)
        {
        	y = 0;
            for(int j = largeur-1; j >= 0; j--)
            {
                bas[x][y] = base[i][j] ? true : false;
                y += 1;
            }
            x += 1;
        }
        tableaux[2] = bas;

        //GAUCHE
        boolean[][] gauche = new boolean[largeur][hauteur];
        x = 0;
        for(int j = largeur-1; j >= 0; j--)
        {
        	y = 0;
            for(int i = 0; i < hauteur; i++)
            {
                gauche[x][y] = base[i][j] ? true : false;
                y += 1;
            }
            x += 1;
        }
        tableaux[3] = gauche;
        return tableaux;
    }

    /*
     * Cette méthode sert à optimiser le coût en espace du programme. Si une pièce
     * d'une même forme et de mêmes dimensions a déjà été générée, elle retourne
     * la clé permettant d'accéder aux tableaux de cette pièce. On évite ainsi de
     * générer et stocker deux fois les mêmes tableaux.
     * 
     * Renvoie les tableaux stockés dans la HashMap static si ils existent, 
     * sinon renvoie null.
     */
    protected boolean[][][] recyclage(HashMap<int[], boolean[][][]> cachePieces, int hauteur, int largeur) {
        //génération des tableaux, ou réutilisation
    	int[] keyP = null;
        for(int[] key : cachePieces.keySet())
        {
            if(key[0] == hauteur && key[1] == largeur)
              keyP = key; 
        }
        if(keyP != null) {
            System.out.println("Recyclage !");
            return cachePieces.get(keyP);
        }
        return null;
    }
}
