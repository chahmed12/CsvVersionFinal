import java.util.ArrayList;
import java.util.List;

public class selectionneurSimple implements selectionneur {
    private double seuil;
    public selectionneurSimple(double seuil){
        this.seuil=seuil;

    }
     public List<EntreeNom> selectionner(List<NomScore> candidats){
        List<EntreeNom> meilleur= new ArrayList<>();
        for (NomScore c: candidats){
            if (c.get_score()>=seuil){
                meilleur.add(c.get_EntreeNom());
            }
        }
       
        return meilleur;
     }
    
}
