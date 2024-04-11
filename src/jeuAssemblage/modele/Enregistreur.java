package jeuAssemblage.modele;

import java.io.*;

public class Enregistreur {

    private final String savePath;

    /**
     * Prend un chemin de sauvegarde en paramètre et crée un dossier à cet endroit
     * s'il n'existe pas déjà.
     * @param savePath le chemin de sauvegarde
     */
    public Enregistreur(String savePath) {
        this.savePath = savePath;
        try {
            new File(this.savePath).mkdir();
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }

    public String getSavePath(){
        return this.savePath;
    }

    /**
     * Enregistre la partie sous la forme d'un fichier de nom 'nom' contenant 
     * une instance de classe Config sérialisée.
     * Renvoie true si la sauvegarde s'est bien effectuée, false sinon.
     * @param nom le nom de la sauvegarde
     * @param partie la configuration à enregistrer
     * @return boolean
     */
    public boolean enregistrer(String nom, Config partie) {
        try {
            File sauvegarde = new File(this.savePath+nom);
            ObjectOutputStream fluxEcriture = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(sauvegarde)));
            fluxEcriture.writeObject(partie);
            fluxEcriture.close();
        } catch (FileNotFoundException error) {
            error.printStackTrace();
        } catch (Exception error) {
            error.printStackTrace();
        }
        return true;
    }

    /**
     * Restaure une partie à partir d'une instance de classe contenue dans
     * le fichier de sauvegarde portant le nom passé en paramètre.
     * Renvoie un objet Config si la restauration s'est bien déroulée,
     * false sinon.
     * @param nom le nom de la sauvegarde à restaurer
     * @return data : Config
     */
    public Config restaurer(String nom) {
        try {
            File sauvegarde = new File(this.savePath+nom);
            if(!sauvegarde.exists())
                return null;
            ObjectInputStream fluxLecture = new ObjectInputStream(new BufferedInputStream(new FileInputStream(sauvegarde)));
            Config data = (Config) fluxLecture.readObject();
            fluxLecture.close();
            return data;

        } catch (FileNotFoundException error) {
            error.printStackTrace();
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }
}