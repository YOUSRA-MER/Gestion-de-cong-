package Model;
public class Employees {
    private String nom,prenom,email,telephone;
    private double salaire;
    Role role;
    Poste poste;
    private int id;
    private int solde;
    public Employees(int id,String nom, String prenom, String email, String telephone, double salaire, Role role,
                     Poste poste) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.salaire = salaire;
        this.role = role;
        this.poste = poste;
        this.solde = 25;
    }
    public int getSolde() {
        return solde;
    }
    public void setSolde(int newSolde) {
        if(this.solde > 0) {
            this.solde = solde - newSolde ;
        }
    }
    public String getNom() {
        return nom;
    }
    @Override
    public String toString() {
        return "Employees [nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone=" + telephone
                + ", salaire=" + salaire + ", role=" + role + ", poste=" + poste + ", id=" + id + ", solde=" + solde
                + "]";
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public double getSalaire() {
        return salaire;
    }
    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Poste getPoste() {
        return poste;
    }
    public void setPoste(Poste poste) {
        this.poste = poste;
    }
    public int getId() {
        return id;
    }


}
