package jeuAssemblage;

import jeuAssemblage.controleur.*;
import jeuAssemblage.modele.*;

public class Demo {
    public static void main(String[] args)
    {	
		Enregistreur registre = new Enregistreur("sauvegardes/");
		new ControleurGlobal(registre);
	}
}
