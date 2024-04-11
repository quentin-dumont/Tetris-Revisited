package jeuAssemblage.vue;

public interface BoucleJouable {
    public boolean estTermine();
    public void quitter();
    public void terminer();
    public BoucleJouable rejouer();
}
