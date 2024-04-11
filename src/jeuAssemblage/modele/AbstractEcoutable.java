package jeuAssemblage.modele;
import java.util.*;

public abstract class AbstractEcoutable {

  private ArrayList<Ecouteur> ecouteurs = new ArrayList<>();

  public void ajoutEcouteur(Ecouteur e)
  {
    this.ecouteurs.add(e);
  }

  public void retraitEcouteur(Ecouteur e)
  {
    this.ecouteurs.remove(e);
  }

  protected void prevenirEcouteurs()
  {
    for(Ecouteur e : ecouteurs)
    {
      e.modeleMisAJour(this);
    }
  }
}
