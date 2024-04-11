package pieces;

import java.util.HashMap;

public class Piece_L extends StrategyPiece {

    private static HashMap<int[], boolean[][][]> cachePieces;

    static {
    	cachePieces = new HashMap<int[], boolean[][][]>();
    }
    
    @Override
    public boolean[][][] generePiece(int hauteur, int largeur)
    {
        boolean[][][] orientations = this.recyclage(cachePieces, hauteur, largeur);
        if(orientations != null) return orientations;
    	boolean[][] base = new boolean[hauteur][largeur];
    	for(int i = 0; i < hauteur; i++) 
        {
            for(int j = 0; j < largeur; j++) 
            {
                if(j == 0 || i == hauteur-1){
                  base[i][j] = true;
                }else{
                  base[i][j] = false;
                }
            }
        }
    	orientations = this.genereOrientations(base, hauteur, largeur);
        cachePieces.put(new int[] {hauteur, largeur}, orientations);
    	return orientations;
    }
    
    
}
