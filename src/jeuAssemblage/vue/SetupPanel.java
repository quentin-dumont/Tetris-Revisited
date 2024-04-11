package jeuAssemblage.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import jeuAssemblage.modele.*;
/**
 * @author Sélian ARSÈNE, modifications techniques par Quentin Dumont
 * Classe qui créé le menu du jeu.
 * 
 */
public class SetupPanel extends JFrame implements ActionListener, KeyListener {

	private static final int HAUTEUR_FENETRE = 720;
	private static final int LARGEUR_FENETRE = 1080;

	public static final int TERMINAL = 0;
	public static final int INTERFACE = 1;

	private static final int BASE_LARGEUR = 15;
	private static final int BASE_HAUTEUR = 15;
	private static final int BASE_NB_PIECES = 5;

	private static final Color TEXT_LABEL_TITLE_BG = new Color(0,0,0,127);
	private static final Color TEXT_LABEL_BG = new Color(20, 55, 155, 200);

	private Enregistreur registre;
	private int mode;

	private JButton btnInterface;
	private JButton btnTerminal;
	private JButton btnClearNomFichier;
	private boolean isReady;

	private TextLabel nomFichier;
	private JFileChooser selecteurFichier;
	private JButton btnSelectionFichier;
	private int etatSelecteurFichier;
	private boolean isSelecteurActive; 

	private JTextField hauteurGrilleInput;
	private JTextField largeurGrilleInput;
	private JTextField nbPiecesInput;

	private PlateauPuzzle jeuTemporaire = null;
	private PlateauPuzzle jeu = null;

	/**
	 * Constructeur du menu
	 * 
	 * @param registre est une instance de Enregistreur qui permet d'ouvrir une sauvegarde de jeu.
	 */
	public SetupPanel(Enregistreur registre) {
		super("Jeu Assemblage");
		this.registre = registre;
		
		this.isSelecteurActive = false;
		this.isReady = false;

		this.setLayout(null);

		ImageIcon bg = new ImageIcon(SetupPanel.class.getResource("images/bg_menu_V3.png"));
		ImageIcon logo = new ImageIcon(SetupPanel.class.getResource("images/logo.png"));
		JLabel bgLabel = new JLabel(bg);
		JLabel logoLabel = new JLabel(logo);

		TextLabel creerPartieLabel = new TextLabel("Nouvelle partie", Font.BOLD);
		TextLabel chargerPartieLabel = new TextLabel("Charger une partie", Font.BOLD);
        TextLabel largeurGrilleLabel = new TextLabel("Largeur du plateau (>10):", Font.BOLD);
        TextLabel hauteurGrilleLabel = new TextLabel("Hauteur du plateau (>10):", Font.BOLD);
        TextLabel nbPiecesLabel = new TextLabel("Nombre de pièces:", Font.BOLD);
		nomFichier = new TextLabel("", Font.PLAIN);
		
		hauteurGrilleInput = new JTextField();
    	largeurGrilleInput = new JTextField();
		nbPiecesInput = new JTextField();
		hauteurGrilleInput.addKeyListener(this); 
		largeurGrilleInput.addKeyListener(this); 
		nbPiecesInput.addKeyListener(this);

		btnTerminal = new JButton("Jouer en terminal");
		btnInterface = new JButton("Jouer dans l'interface");
		btnSelectionFichier = new JButton("Choisir une sauvegarde");
		btnClearNomFichier = new JButton("X");	
		btnTerminal.addActionListener(this); btnInterface.addActionListener(this); btnSelectionFichier.addActionListener(this);
		btnClearNomFichier.addActionListener(this); 
		
		creerPartieLabel.setDefaultStyle(Color.WHITE, 25, TEXT_LABEL_TITLE_BG);
		chargerPartieLabel.setDefaultStyle(Color.WHITE, 25, TEXT_LABEL_TITLE_BG);
		largeurGrilleLabel.setDefaultStyle(Color.WHITE, 20, TEXT_LABEL_BG);
		hauteurGrilleLabel.setDefaultStyle(Color.WHITE, 20, TEXT_LABEL_BG);
		nbPiecesLabel.setDefaultStyle(Color.WHITE, 20, TEXT_LABEL_BG);
		nomFichier.setDefaultStyle(Color.BLACK, 25, Color.WHITE);

		btnTerminal.setFont(new Font("Arial", Font.BOLD, 25));
		btnInterface.setFont(new Font("Arial", Font.BOLD, 25));
		btnSelectionFichier.setFont(new Font("Arial", Font.BOLD, 15));
		btnClearNomFichier.setFont(new Font("Arial", Font.BOLD, 15));

		//Positionnment des indications des paramètres de jeu
		creerPartieLabel.setBounds((LARGEUR_FENETRE/4)-120, (HAUTEUR_FENETRE/4) + 160, 210, 30);
		chargerPartieLabel.setBounds(((LARGEUR_FENETRE*3)/4)-150, (HAUTEUR_FENETRE/4) + 160, 300, 30);
		hauteurGrilleLabel.setBounds((LARGEUR_FENETRE/12)-60, (HAUTEUR_FENETRE/4) + 230, 280, 30);
		largeurGrilleLabel.setBounds((LARGEUR_FENETRE/12)-60, (HAUTEUR_FENETRE/4) + 280, 280, 30);
		nbPiecesLabel.setBounds((LARGEUR_FENETRE/12)-60, (HAUTEUR_FENETRE/4) + 330, 280, 30);
		nomFichier.setBounds(((LARGEUR_FENETRE*3)/4)-110, HAUTEUR_FENETRE-220, 210, 30);

		//Positionnement des champs de texte
		hauteurGrilleInput.setBounds((LARGEUR_FENETRE/3)-20, (HAUTEUR_FENETRE/4) + 230, 200, 30);
		largeurGrilleInput.setBounds((LARGEUR_FENETRE/3)-20, (HAUTEUR_FENETRE/4) + 280, 200, 30);
		nbPiecesInput.setBounds((LARGEUR_FENETRE/3)-20, (HAUTEUR_FENETRE/4) + 330, 200, 30);
		hauteurGrilleInput.setText(""+BASE_HAUTEUR); largeurGrilleInput.setText(""+BASE_LARGEUR);
		nbPiecesInput.setText(""+BASE_NB_PIECES);
		//Positionnement des boutons
		btnTerminal.setBounds(0, HAUTEUR_FENETRE-100, LARGEUR_FENETRE/2, 50);
		btnInterface.setBounds(LARGEUR_FENETRE/2, HAUTEUR_FENETRE-100, LARGEUR_FENETRE/2, 50);
		btnSelectionFichier.setBounds(((LARGEUR_FENETRE*3)/4)-120, HAUTEUR_FENETRE-320, 230, 80);
		btnClearNomFichier.setBounds(((LARGEUR_FENETRE*4)/5)+50, HAUTEUR_FENETRE-220, 50, 30);
		//Positionnement des images
		bgLabel.setBounds(0, 0, LARGEUR_FENETRE, HAUTEUR_FENETRE);
		logoLabel.setBounds((LARGEUR_FENETRE/2)-100, 70, 200, 200);
		
		this.add(hauteurGrilleLabel); this.add(largeurGrilleLabel); this.add(nbPiecesLabel); 
		this.add(creerPartieLabel); this.add(chargerPartieLabel); this.add(nomFichier);
		this.add(hauteurGrilleInput); this.add(largeurGrilleInput); this.add(nbPiecesInput);
		this.add(btnTerminal); this.add(btnInterface); this.add(btnSelectionFichier); this.add(btnClearNomFichier);
		this.add(logoLabel);this.add(bgLabel);  

		this.setSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	public PlateauPuzzle getJeu() {
		return this.jeu;
	}

	/**
	 * Méthode qui créant une instance de JFileChooser, ouvert sur le dossier où sont sauvegardées
	 * les parties préalablement enregistrées. Le sélecteur de fichier (JFileChooser) est paramétré
	 * tel que l'on ne peut sélectionner qu'un seul fichier, afin de n'ouvrir qu'une seule sauvegarde.
	 *
	 */
	public void creerSelecteurFichier(){
		this.selecteurFichier = new JFileChooser(this.registre.getSavePath());
		this.selecteurFichier.setDialogTitle("Sélectionner une sauvegarde");
		this.selecteurFichier.setAcceptAllFileFilterUsed(false);
		this.selecteurFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.etatSelecteurFichier = this.selecteurFichier.showOpenDialog(null);
		this.isSelecteurActive = true;
	}

	/**
	 * Méthode exécutée que lorsqu'un sélecteur de fichiers (JFileChooser) a été instancié,
	 * et enregistre le fichier sélectionné, ou affiche les champs de texte permettant de créer une 
	 * partie si aucune sauvegarde n'a été ni chargée, ni sélectionnée.
	 */
	public void selecteurResponseHandler(){
		switch(this.etatSelecteurFichier){
				case(JFileChooser.APPROVE_OPTION):
					//on vide le contenu de nomFichier s'il y en a
					this.nomFichier.setText("");
					//on ajoute le nom de la sauvegarde sélectionnée
					this.nomFichier.setText(this.selecteurFichier.getSelectedFile().getName());
					Config sauvegarde = this.registre.restaurer(this.nomFichier.getText());
					this.hauteurGrilleInput.setText("");this.largeurGrilleInput.setText("");this.nbPiecesInput.setText("");
					this.jeuTemporaire = new PlateauPuzzle(sauvegarde);
				
				case(JFileChooser.CANCEL_OPTION):
					//Si aucune sauvegarde n'est chargée, on affiche les champs de texte de création de partie.
					if(this.nomFichier.getText().isBlank()){
						this.setInputsEnabled(true);
					}
			}
		//le sélecteur de fichier est fermé, on met à "false" isSelecteurActive
		this.isSelecteurActive = false;
	}

	/**
	 * Méthode rendant les champs de texte de création d'une partie visibles/invisibles selon la valeur du paramètre.
	 * 
	 * @param value un booléen servant de "toggle". Si la valeur est vraie, on affiche les champs de texte, sinon on les cache.
	 */
	public void setInputsEnabled(boolean value){
		if (!value){
			this.hauteurGrilleInput.setVisible(value);
			this.largeurGrilleInput.setVisible(value);
			this.nbPiecesInput.setVisible(value);
		}else{
			this.hauteurGrilleInput.setVisible(value);
			this.largeurGrilleInput.setVisible(value);
			this.nbPiecesInput.setVisible(value);
		}
	}

	public int getMode() {
		return this.mode;
	}

	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == this.btnTerminal){
		  this.mode = TERMINAL;
		  this.isReady = true;
		}
		if(e.getSource() == this.btnInterface){
		  this.mode = INTERFACE;
		  this.isReady = true;
		}
		if (e.getSource() == this.btnSelectionFichier){
			//On affiche un sélecteur de fichier de sauvegarde
			this.isSelecteurActive = true;
			this.setInputsEnabled(false);
			this.creerSelecteurFichier();
		}
		//Décharge le fichier de sauvegarde s'il existe
		if(e.getSource() == this.btnClearNomFichier){
			//System.out.println("pressed");
			this.nomFichier.setText("");
			this.setInputsEnabled(true);
		}
		//Si un sélecteur de fichier a été ouvert, on appelle une méthode qui traite les réponses
		//du sélecteur de fichier.
		if (this.isSelecteurActive){
			this.selecteurResponseHandler();
		}	
		if(isReady){
			if(!this.nomFichier.getText().isBlank()) {
				this.jeu = this.jeuTemporaire;
			}
			else if(!this.hauteurGrilleInput.getText().isBlank() && !this.largeurGrilleInput.getText().isBlank() && !this.nbPiecesInput.getText().isBlank()) {
				int h = Integer.parseInt(this.hauteurGrilleInput.getText());
				int l = Integer.parseInt(this.largeurGrilleInput.getText());
				int p = Integer.parseInt(this.nbPiecesInput.getText());
				//surface max d'une pièce quand le nombre de pièces est supérieur à GenerateurJeu.SEUIL_PIECES
				if(h < 10 || l < 10 || GenerateurJeu.TMAX_APRES_SEUIL*GenerateurJeu.TMAX_APRES_SEUIL*p >= h*l) {
					this.hauteurGrilleInput.setText(BASE_HAUTEUR+"");
					this.largeurGrilleInput.setText(BASE_HAUTEUR+"");
					this.nbPiecesInput.setText(BASE_NB_PIECES+"");
				}
				else  
				  this.jeu = new GenerateurJeu(h,l,p).getJeuAssemblage();
			}
			this.isReady = false;
		}
	}

	@Override
    public void keyPressed(KeyEvent e){};
    @Override
    public void keyReleased(KeyEvent e){};
    //Pour s'assurer que les entrées soient numériques
    @Override
    public void keyTyped(KeyEvent e)
    {
        char touche = e.getKeyChar();
        if (((touche < '0') || (touche > '9')) && (touche != KeyEvent.VK_BACK_SPACE))
          e.consume();  // ignorer l'événement
    }

}