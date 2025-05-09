public class NomScore {
    private EntreeNom nom;
    private double score;
    public NomScore(EntreeNom nom,double score){
        this.nom = nom ;
        this.score = score;
    }
    public double get_score(){
        return score;
    }
    public EntreeNom get_EntreeNom(){
        return nom;
    }
}
