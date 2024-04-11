package jeuAssemblage.modele;
import java.util.*;
import pieces.*;
import java.awt.Rectangle;

public class PlateauPuzzle extends AbstractEcoutable {
	
	private int hauteur;
	private int largeur;
	private ArrayList<PiecePuzzle> listePieces;
	private PiecePuzzle pSelected;
	
	/**
	 * Initialise un nouveau plateau de jeu, sans aucune
	 * pièce.
	 * @param hauteur nombre de lignes du plateau
	 * @param largeur nombre de colonnes du plateau
	 */
	public PlateauPuzzle(int hauteur, int largeur){
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.listePieces = new ArrayList<PiecePuzzle>();
		this.pSelected = null;
	}

	/**
	 * Surcharge pour créer un plateau de jeu à partir d'une sauvegarde.
	 * Clonage des pièces pour éviter les effets de bords.
	 * @param config la sauvegarde
	 */
	public PlateauPuzzle(Config config) {
		this(config.getHauteur(), config.getLargeur());
		for(int i = 0; i < config.getPieces().size(); i++) {
			try {
				this.listePieces.add(config.getPieces().get(i).clone());
			} catch(CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getHauteur() {
		return this.hauteur;
	}

	public int getLargeur() {
		return this.largeur;
	}

	public PiecePuzzle getSelectedPiece() {
		return this.pSelected;
	}

	public ArrayList<PiecePuzzle> getListePieces() {
		return this.listePieces;
	}

	public Config getConfig() {
		return new Config(this);
	}

	/**
	 * Ajoute une pièce aux coordonnées précisées. 
	 * Renvoie false si l'ajout n'est pas possible à cet endroit.
	 * @param piece la pièce à ajouter
	 * @param i la ligne
	 * @param j la colonne
	 * @return boolean
	 * 
	 */
	public boolean ajoutPiece(PiecePuzzle piece, int i, int j) {
		if(!estPositionnable(piece, i, j))
		  return false;
		int[] pos = {i,j};
		piece.setPosition(pos);
		this.listePieces.add(piece);
		return true;
	}

	/**
	 * Sélectionne la pièce dont une des cases se trouve aux coordonnées
	 * passées en paramètres. Renvoie true si elle existe aux
	 * coordonnées, sinon renvoie false.
	 * @param i la ligne
	 * @param j la colonne
	 * @return boolean
	 * 
	 */
	public boolean selectionnerPiece(int i, int j) {
		for(PiecePuzzle p : this.listePieces) {
			int[] pos = p.getPosition();
			int pieceI = i - (pos[0] - (p.getHauteur() / 2 - (p.getHauteur()+1) % 2));
			int pieceJ = j - (pos[1] - (p.getLargeur() / 2 - (p.getLargeur()+1) % 2));
			if(pieceI >= 0 && pieceI < p.getHauteur() && pieceJ >= 0 && pieceJ < p.getLargeur() && p.estOccupee(pieceI, pieceJ)) {
				this.pSelected = p;
				this.prevenirEcouteurs();
				return true;
			}
		}
		this.pSelected = null;
		this.prevenirEcouteurs();
		return false;
	}

	/**
	 * Surcharge pour prendre sélectionner directement une instance de pièce.
	 * @param p la pièce a sélectionner
	 * @return boolean
	 *
	 */
	public boolean selectionnerPiece(PiecePuzzle p) {
		if(this.listePieces.contains(p))
		{
			this.pSelected = p;
			this.prevenirEcouteurs();
			return true;
		}
		this.pSelected = null;
		this.prevenirEcouteurs();
		return false;
	}

	/**
	 * Déplace la pièce sélectionnée aux coordonnées précisées.
	 * Renvoie false si le déplacement n'est pas possible.
	 * @param newX la nouvelle ligne
	 * @param newY la nouvelle colonne
	 * @return boolean
	 * 
	 */
	public boolean deplacerPiece(int newX, int newY)
	{
		if(this.pSelected == null)
		  return false;
		listePieces.remove(this.pSelected);
		if(!estPositionnable(this.pSelected, newX, newY))
		{
			listePieces.add(this.pSelected);
			this.prevenirEcouteurs();			
			return false;
		}
		int[] newPos = new int[] {newX, newY};
		this.pSelected.setPosition(newPos);
		listePieces.add(this.pSelected);
		this.prevenirEcouteurs();
		return true;
	}

	/**
	 * Tourne la pièce sélectionnée de 90° dans le sens voulu. 
	 * Renvoie false si la rotation n'est pas possible.
	 * @param antihoraire sens de rotation, true pour anti-horaire false sinon
	 * @return boolean
	 * 
	 */
	public boolean tournerPiece(boolean antihoraire)
	{
		if(this.pSelected == null)
		  return false;
		this.pSelected.tourner(antihoraire);
		listePieces.remove(this.pSelected);
		if(!estPositionnable(this.pSelected, this.pSelected.getPosition()[0], this.pSelected.getPosition()[1]))
		{
			this.pSelected.tourner(!antihoraire);
			listePieces.add(this.pSelected);
			this.prevenirEcouteurs();
			return false;
		}
		listePieces.add(this.pSelected);
		this.prevenirEcouteurs();
		return true;
	}

	/**
	 * Retourne le score en pourcentage du rapport
	 * entre la somme des cases occupées par les pièces,
	 * et la surface du plus petit rectangle encadrant 
	 * toutes les pièces.
	 * @return le rapport entre la surface prise par la somme des cases occupées des pièces, et la surface occupée par le rectangle min du joueur.
	 * 
	 */
	public int calculScore()
	{
		float espaceMin = espaceOccupe();
		int[] points = rectangleMinimal();
		//calcul surface
		int i1 = points[0], j1 = points[1], i2 = points[2], j2 = points[3];
		float surfaceRect = Math.abs(i1-i2) * Math.abs(j1-j2);
		System.out.println("espace occupé : "+espaceMin+", surface actuelle : "+surfaceRect);
		int score = (int) Math.floor(espaceMin / surfaceRect * 100);
		return score;
	}

	/**
	 * Surcharge pour calculer le score en fonction de la solution trouvée
	 * par le robot. Prends le rectangle min obtenu par le robot en paramètre.
	 * @param rectJoueur les quatre points du rectangle minimal du joueur
	 * @param rectRobot les quatre points du rectangle minimal du robot
	 * @return le rapport entre la solution du joueur et celle du robot en pourcentage
	 * 
	 */
	public int calculScore(int[] rectJoueur, int[] rectRobot)
	{
		//calcul surfaces
		int i1 = rectJoueur[0], j1 = rectJoueur[1], i2 = rectJoueur[2], j2 = rectJoueur[3];
		float surfaceJoueur = Math.abs(i1-i2) * Math.abs(j1-j2);
		int a1 = rectRobot[0], b1 = rectRobot[1], a2 = rectRobot[2], b2 = rectRobot[3];
		float surfaceRobot = Math.abs(a1-a2) * Math.abs(b1-b2);
		int score = (int) Math.floor(surfaceRobot / surfaceJoueur * 100);
		return score;
	}

	/**
	 * Retourne les points S,O,N,E du plus petit rectangle
	 * encadrant les pièces du jeu.
	 * @return les quatre points du rectangle minimal
	 */
	public int[] rectangleMinimal()
	{
		int sud = -1, est = -1, nord = 1000, ouest = 1000;
		for(int i = 0; i < listePieces.size(); i++)
		{
			int nord_piece = listePieces.get(i).getPosition()[0] - listePieces.get(i).getMilieu()[0];
			int sud_piece = listePieces.get(i).getPosition()[0] - listePieces.get(i).getMilieu()[0] + listePieces.get(i).getHauteur();
			int ouest_piece = listePieces.get(i).getPosition()[1] - listePieces.get(i).getMilieu()[1];
			int est_piece = listePieces.get(i).getPosition()[1] - listePieces.get(i).getMilieu()[1] + listePieces.get(i).getLargeur();
			if(sud < sud_piece) sud = sud_piece;
			if(est < est_piece) est = est_piece;
			if(nord_piece >= 0 && nord > nord_piece) nord = nord_piece;
			if(ouest_piece >= 0 && ouest > ouest_piece) ouest = ouest_piece;
		}
		return new int[] {sud, ouest, nord, est};
	} 

	/**
	 * Retourne la somme des cases occupées
	 * par les pièces.
	 */
	private float espaceOccupe()
	{
		String plateau = this.toString();
		int espace = 0;
		for (int i = 0; i < plateau.length(); i++)
			if (plateau.substring(i,i+1).equals(PiecePuzzle.PLEIN))
				espace += 1;
		return espace;
	}

	private ArrayList<PiecePuzzle> collisions(PiecePuzzle piece, int x, int y) {
		ArrayList<PiecePuzzle> col = new ArrayList<PiecePuzzle>();
		int debutP_I = x - piece.getMilieu()[0];
		int debutP_J = y - piece.getMilieu()[1];
		Rectangle rectPiece = new Rectangle(debutP_I, debutP_J, piece.getHauteur(), piece.getLargeur());
		for(int i = 0; i < listePieces.size(); i++)
		{
			PiecePuzzle autre = listePieces.get(i);
			int debutA_I = autre.getPosition()[0]-autre.getMilieu()[0];
			int debutA_J = autre.getPosition()[1]-autre.getMilieu()[1];
			Rectangle rectAutre = new Rectangle(debutA_I, debutA_J, autre.getHauteur(), autre.getLargeur());
			if(rectPiece.intersects(rectAutre))
			  col.add(autre);
		}
		return col;
	}

	private boolean seTouchent(PiecePuzzle piece, int x, int y, ArrayList<PiecePuzzle> autres)
	{
		int debutP_I = x - piece.getMilieu()[0];
		int debutP_J = y - piece.getMilieu()[1];
		ArrayList<int[]> occupes = new ArrayList<int[]>();
		//liste des positions occupées par la pièce
		for(int a = 0; a < piece.getHauteur(); a++)
			for(int b = 0; b < piece.getLargeur(); b++)
				if(piece.estOccupee(a, b))
					occupes.add(new int[] {debutP_I+a, debutP_J+b});
		for(int i = 0; i < autres.size(); i++)
		{
			PiecePuzzle autre = autres.get(i);
			int[] posAutre = autre.getPosition();
			int[] milieuAutre = autre.getMilieu();
			//on regarde si une des positions est occupée par une autre pièce
			for(int a = 0; a < autre.getHauteur(); a++)
				for(int b = 0; b < autre.getLargeur(); b++)
					if(autre.estOccupee(a, b))
						for(int occ = 0; occ < occupes.size(); occ++)
						{
							int[] occupe = occupes.get(occ);
							if(posAutre[0]-milieuAutre[0]+a == occupe[0] && posAutre[1]-milieuAutre[1]+b == occupe[1])
								return true;
						}
		}
		return false;
	}

	private boolean estPositionnable(PiecePuzzle piece, int x, int y) {
		int debutP_I = x - piece.getMilieu()[0];
		int debutP_J = y - piece.getMilieu()[1];
		//on vérifie si la pièce n'est pas en dehors du plateau
		if(debutP_I < 0 || debutP_J < 0)
		  return false;
		if(debutP_I+piece.getHauteur() > this.hauteur || debutP_J+piece.getLargeur() > this.largeur)
		  return false;
		//on liste les pièces avec lesquelles notre pièce est en collision
		ArrayList<PiecePuzzle> autres = collisions(piece, x, y);
		if(autres.size() > 0)
		{
			//on vérifie si aucune des pièces listées ne touche réellement la pièce
			return !seTouchent(piece, x, y, autres);
		}
		return true;
	}

	/**
	 * Renvoie une représentation du plateau en 2D.
	 *
	 */
	@Override
	public String toString()
	{
		String[][] ch = new String[hauteur][largeur];
		for(int i = 0; i < hauteur; i++)
			for(int j = 0; j < largeur; j++)
				ch[i][j] = PiecePuzzle.VIDE;
		for(int p = 0; p < this.listePieces.size(); p++)
		{
			PiecePuzzle piece = listePieces.get(p);
			String[] pieceStrings = piece.toString().split("\n");
			/*for(String s : pieceStrings) {
				System.out.println(s);
			}*/
			int debutI = piece.getPosition()[0] - piece.getMilieu()[0];
			int debutJ = piece.getPosition()[1] - piece.getMilieu()[1];
			//System.out.println("topLeft ->"+debutI+" "+debutJ);

			for(int i = 0; i < piece.getHauteur(); i++)
			{
				for(int j = 0; j < piece.getLargeur(); j++)
				{
					String pos = pieceStrings[i].substring(j,j+1);
					if(pos.equals(PiecePuzzle.PLEIN)) {
						ch[debutI+i][debutJ+j] = piece.getSymbole();
					}
					  
				}
			}
		}
		String chaine = "";
		for(int i = 0; i < hauteur; i++)
		{
			for(int j = 0; j < largeur; j++)
				chaine += ch[i][j]+" ";
			chaine += "\n";
		}
		return chaine;
	}

	/**
	 * 
	 * @return une représentation 2D indicée du plateau
	 */
	public String lisible()
	{
		String tableau = this.toString();
		String[] lignes = tableau.split("\n");
		String res = " ";
		for(int i = 1; i <= lignes[0].length()/2; i++)
		{
			res += i % 10+" ";
		}
		res += "\n";
		for(int i = 1; i <= lignes.length; i++)
		{
			res += (i%10)+lignes[i-1]+"\n";
		}
		return res;
	}
    
}
