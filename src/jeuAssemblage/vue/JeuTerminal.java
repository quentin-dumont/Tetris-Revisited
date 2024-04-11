package jeuAssemblage.vue;

import java.util.*;

import pieces.*;
import jeuAssemblage.modele.*;

public class JeuTerminal implements BoucleJouable {
    
    private PlateauPuzzle jeu;
    private Scanner scanner;
    private boolean termine;


    /**
     * Classe permettant de jouer en terminal au jeu d'assemblage,
     * via des menus communiquants. Un menu général, un menu de sélection
     * des pièces, et un menu d'actions pour chaque pièce.
     * @param jeu le plateau de jeu
     */
    public JeuTerminal(PlateauPuzzle jeu)
    {
        this.scanner = new Scanner(System.in);
        this.jeu = jeu;
        this.termine = false;

        while(!this.termine){
            menuGeneral();
        }
    }
    
    @Override
    public boolean estTermine() {
        return this.termine;
    }

    @Override
    public void quitter(){};

    @Override
    public void terminer(){
        this.termine = true;
    }

    @Override
    public BoucleJouable rejouer() {
        return new JeuTerminal(this.jeu);
    }

    private void menuGeneral()
    {
        System.out.println("\033c");
        System.out.println(this.jeu.lisible());
        System.out.println("### Général ###\n1 : Pièces\n2 : Terminer");
        int res = 0;
        while(!(res == 1 || res == 2))
            res = this.scanner.nextInt();
        switch(res)
        {
            case 1 :
                menuPieces();
                break;
            case 2 :
                termine = true;
                break;
        }
    }

    private void menuPieces()
    {
        System.out.println("\033c");
        System.out.println(this.jeu.lisible());
        System.out.println("### Pièces ###");
        ArrayList<PiecePuzzle> pieces = jeu.getListePieces();
        for(int i = 0; i < pieces.size(); i++)
        {
            //on affiche les coordonnées top-left de chaque pièce
            int i_piece = pieces.get(i).getPosition()[0]-pieces.get(i).getMilieu()[0]+1;
            int j_piece = pieces.get(i).getPosition()[1]-pieces.get(i).getMilieu()[1]+1;
            System.out.println(i+" : ("+i_piece+","+j_piece+")");
        }
        System.out.println("\n"+pieces.size()+" : Retour\n"+(pieces.size()+1)+" : Terminer");
        int res = -1;
        while(res < 0 || res > pieces.size()+1)
            res = this.scanner.nextInt();
        
        if(res == pieces.size()) {
            menuGeneral();
        } 
        else if(res == pieces.size()+1) {
            this.termine = true;
        }
        else {
            jeu.selectionnerPiece(pieces.get(res));
            actionPiece();
        }
    }

    private void actionPiece()
    {
        System.out.println("\033c");
        System.out.println(this.jeu.lisible());
        System.out.println("### Actions ###\n1 : Déplacer\n2 : Tourner ⟳\n3 : Tourner ⟲");
        System.out.println("\n4 : Retour\n5 : Terminer");
        int res = 0;
        while(res < 1 || res > 5)
            res = this.scanner.nextInt();

        PiecePuzzle piece = this.jeu.getSelectedPiece();
        switch(res)
        {
            case 1 :
                //déplacement en indiquant le top-left
                System.out.println("Ligne ? (1-"+this.jeu.getHauteur()+")");
                int lig = this.scanner.nextInt();
                System.out.println("Colonne ? (1-"+this.jeu.getLargeur()+")");
                int col = this.scanner.nextInt();
                if(!jeu.deplacerPiece(lig-1+piece.getMilieu()[0], col-1+piece.getMilieu()[1]))
		            System.out.println("⚠ déplacement impossible ⚠");
                break;

            case 2 :
                if(!jeu.tournerPiece(false))
		            System.out.println("⚠ rotation horaire impossible ⚠");
                break;

            case 3 :
                if(!jeu.tournerPiece(true))
		            System.out.println("⚠ rotation anti-horaire impossible ⚠");
                break;

            case 5 :
                this.termine = true;
                return;
        }
        menuPieces();
    }

}
