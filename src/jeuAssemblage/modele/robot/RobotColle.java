package jeuAssemblage.modele.robot;

import jeuAssemblage.modele.*;
import pieces.*;

import java.util.*;

public class RobotColle implements Robot {
    private PlateauPuzzle jeu;

    public RobotColle(PlateauPuzzle jeu) {
        this.jeu = jeu;
    }

    private int[] bestPosition(int startX, int startY, int endX, int endY, PiecePuzzle p, PlateauPuzzle copy) {
        Random random = new Random();
        int r = random.nextInt() % copy.getHauteur(), c = random.nextInt() % copy.getLargeur();
        while(!copy.ajoutPiece(p, r, c)) {
            r = random.nextInt() % copy.getHauteur();
            c = random.nextInt() % copy.getLargeur();
        }

        int[] tmp = copy.rectangleMinimal();
        int bestValRect = (tmp[0] - tmp[2] + tmp[3] - tmp[1]);
        int[] res = {r, c, 0};
        copy.selectionnerPiece(p);
        for(int i = startX - p.getMilieu()[0] - 1; i < endX + p.getMilieu()[0] + 1; i++) {
            for(int j = startY - p.getMilieu()[1] - 1; j < endY + p.getMilieu()[1] + 1; j++) {
                for(int k = 0; k < 4; k++) {
                    if(copy.deplacerPiece(i, j)) {
                        int[] miniRect = copy.rectangleMinimal();
                        if((miniRect[0] - miniRect[2] + miniRect[3] - miniRect[1]) < bestValRect) {
                            bestValRect = (miniRect[0] - miniRect[2] + miniRect[3] - miniRect[1]);
                            res[0] = i;
                            res[1] = j;
                            res[2] = k;
                        }
                    }
                    p.tourner(true);
                }
            }
        }
        return res;
    }

    public PlateauPuzzle play() {
        PlateauPuzzle copyJeu = new PlateauPuzzle(jeu.getHauteur(), jeu.getLargeur());
        List<PiecePuzzle> piecePlateau = jeu.getListePieces();

        for(PiecePuzzle p : piecePlateau) {
            p.setPosition(new int[]{-1000, -1000});
        }

        Collections.sort(piecePlateau, new Comparator<PiecePuzzle>() {
            @Override
            public int compare(PiecePuzzle p1, PiecePuzzle p2) {
                return (p2.getHauteur() * p2.getLargeur()) - (p1.getHauteur() * p1.getLargeur());
            }
        });

        copyJeu.ajoutPiece(piecePlateau.get(0), jeu.getHauteur() / 2, jeu.getLargeur() / 2);

        for(int i = 1; i < piecePlateau.size(); i++) {
            int[] rectangle = copyJeu.rectangleMinimal();
            int[] bestPos = this.bestPosition(rectangle[1], rectangle[2], rectangle[3], rectangle[0], piecePlateau.get(i), copyJeu);
            for(int j = 0; j < bestPos[2]; j++) {
                piecePlateau.get(i).tourner(true);
            }
            copyJeu.deplacerPiece(bestPos[0], bestPos[1]);
        }

        return copyJeu;
    }


}
