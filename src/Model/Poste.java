package Model;

public enum Poste {
    DEVEOPPEUR_FULL_STACK("Développeur Full Stack"),
    Administrateur_Systèmes_et_Réseaux("Administrateur Systèmes et Réseaux"),
    Ingénieur_DevOps("Ingénieur DevOps"),
    Chef_de_Projet_Informatique("Chef de Projet Informatique"),
    Analyste_de_Données("Analyste de Données");


    private final String description;

    Poste(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}