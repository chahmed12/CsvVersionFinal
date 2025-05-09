public class EntreeNom {
    private String identifiant;
    private String nom;

    public EntreeNom(String identifiant , String nom){
        this.identifiant=identifiant;
        this.nom=nom;
    }
    public EntreeNom(String nom){
        this("DEFAUT", nom);
    }
    public  String get_Nom(){
        return nom;
    }
    public String get_id(){
        return identifiant;
    }
    
}
