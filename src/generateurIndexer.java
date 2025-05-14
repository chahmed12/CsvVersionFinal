import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class generateurIndexer implements generateurCandidat {
    private indexeur indexeur = new indexeur();

  
    public List<CoupleNom> generer(List<EntreeNom> liste1, List<EntreeNom> liste2) {
        List<CoupleNom> couples = new ArrayList<>();
        Map<Integer, List<EntreeNom>> indexMap = indexeur.getIndexMap(liste1);

        for (EntreeNom nom2 : liste2) {
            int longueur = nom2.get_Nom().length();
            
            for (int l = Math.max(1, longueur - 1); l <= longueur + 1; l++) {
                List<EntreeNom> candidats = indexMap.getOrDefault(l, Collections.emptyList());

                for (EntreeNom nom1 : candidats) {
                   
                    //if (!nom1.get_Nom().equalsIgnoreCase(nom2.get_Nom())) {
                        couples.add(new CoupleNom(nom1, nom2));
                   // }
                }
            }
        }

        return couples;
    }
}
