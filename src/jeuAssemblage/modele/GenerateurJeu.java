package jeuAssemblage.modele;
import java.util.*;
import pieces.*;

public class GenerateurJeu {

	public static final int SEUIL_PIECES = 8;
	public static final int TMAX_APRES_SEUIL = 4;

	private PlateauPuzzle jeu;
	private int hauteurGrille;
	private int largeurGrille;
	private Random random;
	private ArrayList<PiecePuzzle> listePieces;

	public GenerateurJeu ( int hauteurGrille, int largeurGrille, int nbPieces){
		this.jeu = new PlateauPuzzle( hauteurGrille,largeurGrille);
		this.hauteurGrille = hauteurGrille;
		this.largeurGrille =largeurGrille;
		this.random = new Random();
		this.listePieces = new ArrayList<PiecePuzzle>();
		genererPieces(nbPieces);
		placerPieces();
	}
	
	private int randomBetween(int a, int b){
		return a + this.random.nextInt(b - a);
	}

	public void genererPieces(int nbPieces){
		PiecePuzzle piece;
		for(int p = 0; p < nbPieces; p++){
			
			//random.setSeed(i);
			int n = random.nextInt(5);

			int tailleMaxPiece = Math.min(hauteurGrille, largeurGrille);
			tailleMaxPiece = tailleMaxPiece % 2 == 0 ? (tailleMaxPiece/2) - 1: tailleMaxPiece/2;
			if(nbPieces > SEUIL_PIECES) {
				tailleMaxPiece = TMAX_APRES_SEUIL;
			}
			int h = 0;
			int l = 0;
			
			switch (n){

				case 0:
						h = this.randomBetween(2,tailleMaxPiece);
						l = this.randomBetween(2,tailleMaxPiece);
						piece = new PiecePuzzle(h, l, new Piece_L());
						this.listePieces.add(piece);
						break;

				case 1:
						h = this.randomBetween(3,tailleMaxPiece);
						l = this.randomBetween(2,tailleMaxPiece);
						piece = new PiecePuzzle(h, l, new Piece_C());
						this.listePieces.add(piece);
						break;

				case 2:
						h = this.randomBetween(2,tailleMaxPiece);
						l = this.randomBetween(2,tailleMaxPiece);
						piece = new PiecePuzzle(h, l, new Piece_O());
						this.listePieces.add(piece);
						break;

				case 3:
						h = this.randomBetween(3,tailleMaxPiece);
						l = this.randomBetween(3,tailleMaxPiece);
						
						//avoir un nombre impair
						while (l % 2 == 0) {
							l = this.randomBetween(3,tailleMaxPiece);
						}
						piece = new PiecePuzzle(h, l, new Piece_S());
						this.listePieces.add(piece);
						break;

				case 4:
						h = this.randomBetween(2,tailleMaxPiece);
						l = this.randomBetween(3,tailleMaxPiece);
						
						//avoir un nombre impair
						while (l % 2 == 0) {
							l = this.randomBetween(3,tailleMaxPiece);
						}

						piece = new PiecePuzzle(h, l, new Piece_T());
						this.listePieces.add(piece);
						break;

				default:
						break;
			}
		}
	}

	public void placerPieces(){
		int id=0;
		//this.listePieces.size()!=0
		while (id !=this.listePieces.size()){
			//a changer
			PiecePuzzle piece=this.listePieces.get(id);

			int i=random.nextInt(this.hauteurGrille);
			int j=random.nextInt(this.largeurGrille);
			
			if (!(this.jeu.ajoutPiece(piece, i, j))){
				while (this.jeu.ajoutPiece(piece, i, j)==false) {
					i=random.nextInt(hauteurGrille);
					j=random.nextInt(largeurGrille);
				}
			}
			id+=1;
		}
		
	}
	public PlateauPuzzle getJeuAssemblage(){
		return this.jeu;
	}

}
