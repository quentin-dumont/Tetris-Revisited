package jeuAssemblage.vue;


import javax.swing.*;
import java.awt.*;

/**
 * @author Sélian ARSÈNE
 * 
 * Classe héritant de la classe JLabel, et comprenant des méthodes simplifiant la personnalisation
 * des JLabel.
 * 
 */
public class TextLabel extends JLabel{

    
    private String fontFamilyString;
    private int fontWeight;
    private int fontSize;
    /**
     * Constructeur de TextLabel, prenant en paramètre le texte de l'élément, le poids et la taille de la police d'écriture.
     * Par défaut, la police d'écriture est "Arial".
     * @param text une chaîne de caractères correspondant au contenu de l'élément.
     * @param fontWeight un entier correspondant au poids de la police d'écriture.
     * @param fontSize un entier correspondant à la taille de la police d'écriture.
     */
    public TextLabel(String text, int fontWeight,int fontSize){
        this.setText(text);
        this.fontFamilyString = "Arial";
        this.fontSize = fontSize;
        this.fontWeight = Font.PLAIN;
        Font newFont = new Font(fontFamilyString, this.fontWeight, fontSize);
        this.setFont(newFont);
        this.setHorizontalAlignment(JLabel.CENTER);
    }
    
    public TextLabel(String text){
        this(text, Font.PLAIN, 20);
    }

    public TextLabel(String text, int fontWeight){
        this(text, fontWeight, 20);
    }

    public TextLabel(){
        this("Label", Font.PLAIN, 20);
    }

    public void setFontWeight(int weight){
        Font newFont = new Font("Arial", weight, this.fontSize);
        this.setFont(newFont);
    }

    /**
     * Méthode qui prend en paramètre un entier, recrée une police d'écriture avec comme taille l'entier pris en paramètre,
     * puis modifie l'élément avec la nouvelle police.
     * @param size un entier corespondant à la nouvelle taille de police de l'élément.
     */
    public void setFontSize(int size){
        Font newFont = new Font("Arial", this.fontWeight, size);
        this.setFont(newFont);
    }

    public void setColor(Color color){
        this.setForeground(color);
    }

    /**
     * Méthode prenant en paramètre une couleur, taille et une couleur de fond,
     * et modifie l'élément avec ces paramètres. 
     * @param color une couleur correspondant à la nouvelle couleur du texte de l'élément.
     * @param size un entier représentant la nouvelle taille de police de l'élément.
     * @param bgColor une couleur, qui va remplir le fond de l'élément.
     */
    public void setDefaultStyle(Color color, int size, Color bgColor){
        this.setColor(color); this.setFontSize(size);
		this.setOpaque(true); this.setBackground(bgColor);
    }
}
