package jeuAssemblage.controleur;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jeuAssemblage.modele.robot.*;
import jeuAssemblage.modele.*;
import jeuAssemblage.vue.*;

public class ControleurGlobal {

    private Enregistreur registre;
    private Config config;
    private BoucleJouable boucle;

    /**
     * Contrôle une partie depuis le menu de paramètres jusqu'aux options de fin de partie.
     * @param registre objet permettant de sauvegarder/restaurer des parties
     */
    public ControleurGlobal(Enregistreur registre){
        this.registre = registre;

        SetupPanel setup = new SetupPanel(registre);

        while(setup.getJeu() == null)
        {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setup.dispose();
        PlateauPuzzle jeu = setup.getJeu();
        this.config = jeu.getConfig();
        if(setup.getMode() == SetupPanel.TERMINAL)
          this.boucle = new JeuTerminal(jeu);
        else
          this.boucle = new PlateauPuzzleFrame(jeu);
        
        //on attend que la boucle de jeu soit terminée
        while(!this.boucle.estTermine())
        {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        String conclusion = conclure(jeu, setup.getMode());
        optionsFin(jeu, conclusion, setup.getMode());
    }

    /**
     * Surchage du constructeur pour ignorer la phase de choix des paramètres.
     * Utilisée si l'utilisateur veut rejouer une partie identique.
     * @param registre objet permettant de sauvegarder/restaurer des parties
     * @param config
     * @param mode SetupPanel.TERMINAL=0 ou SetupPanel.INTERFACE=1
     * 
     */
    private ControleurGlobal(Enregistreur registre, Config config, int mode) {
        this.registre = registre;
        this.config = config;
        PlateauPuzzle jeu = new PlateauPuzzle(config);
        if(mode == SetupPanel.TERMINAL)
          this.boucle = new JeuTerminal(jeu);
        else
          this.boucle = new PlateauPuzzleFrame(jeu);
        
        //on attend que la boucle de jeu soit terminée
        while(!this.boucle.estTermine())
        {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String conclusion = conclure(jeu, mode);
        optionsFin(jeu, conclusion, mode);
    }

    /**
     * La conclusion d'une partie se fait en calculant le score du joueur.
     * On fait jouer le robot sur la même partie et on compare son résultat
     * à celui du joueur.
     * @param jeu la partie que le joueur a joué
     * @return
     */
    private String conclure(PlateauPuzzle jeu, int mode) {
        //on laisse une demi-seconde de latence pour que le joueur comprenne qu'il a bien terminé
        try {
          Thread.sleep(500);
        } catch(Exception e) {
          e.printStackTrace();
        }
        int[] rectJoueur = jeu.rectangleMinimal();
        Robot robot = new RobotColle(jeu);
        PlateauPuzzle plateauRobot = robot.play();
        //On affiche la partie jouée par le robot
        this.boucle.quitter();
        if(mode == SetupPanel.INTERFACE)
          this.boucle = new PlateauPuzzleFrame(plateauRobot);
        else
          this.boucle = new JeuTerminal(jeu);
        this.boucle.terminer();
        int[] rectRobot = plateauRobot.rectangleMinimal();
        int score = jeu.calculScore(rectJoueur, rectRobot);
        String conclusion = "Voici la solution du robot, considérée comme optimale (100%). Votre solution -> "+score+"%.";
        if(score < 25) conclusion += "\nFaites un effort quand même :)";
        else if(score < 50) conclusion += "\nVotre agencement n'est pas terrible. Peut mieux faire !";
        else if(score < 75) conclusion += "\nVous avez réalisé un bon agencement !";
        else if(score < 100) conclusion += "\nC'est un très bon agencement !";
        else if(score == 100) conclusion += "\nFélicitations ! Vous avez trouvé la solution optimale selon le robot !";
        else conclusion += "\nIncroyable, vous avez battu le robot !!!";
        conclusion += " Que souhaitez-vous faire ensuite ?";
        return conclusion;
    }

    /**
     * Options de fin de partie, avec enregistrement de la configuration
     * si l'utilisateur veut sauvegarder.
     * @param jeu
     * @param conclusion
     * @param mode
     * 
     */
    private void optionsFin(PlateauPuzzle jeu, String conclusion, int mode) {
        String[] boutons={"Nouvelle partie", "Rejouer cette partie", "Sauvegarder cette partie","Quitter"}; 
        int retour = JOptionPane.showOptionDialog(new JFrame(), conclusion, "Partie terminée !", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, boutons, boutons[3]); 
        if(retour!=JOptionPane.CLOSED_OPTION) {
            switch (retour) 
            {
                case 0 : //Nouvelle partie
                  this.boucle.quitter();
                  new ControleurGlobal(registre);
                  break;
                case 1 : //Rejouer
                  this.boucle.quitter();
                  new ControleurGlobal(registre, this.config, mode);
                  break;
                case 2 : //Sauvegarder
                  String res = JOptionPane.showInputDialog(new JFrame(), "Nommez votre sauvegarde : ", "Sauvegarder", JOptionPane.OK_CANCEL_OPTION);
                  if(res != null && res.length() > 0)
                  {
                    registre.enregistrer(res, this.config);
                    optionsFin(jeu, "Partie sauvegardée !", mode);
                  }
                  else
                    optionsFin(jeu, "Nom de sauvegarde incorrect", mode);
                  break;
                default :
                  this.boucle.quitter();
                  System.exit(0);
                  break;
            }
        }
    }
} 
