import java.util.*;

public class indexeur {

    public Map<Integer, List<EntreeNom>> getIndexMap(List<EntreeNom> noms) {
        Map<Integer, List<EntreeNom>> indexMap = new HashMap<>();

        for (EntreeNom nom : noms){
            int longueur = nom.get_Nom().length();

           
         if (!indexMap.containsKey(longueur)) {
                indexMap.put(longueur, new ArrayList<>());
}
                indexMap.get(longueur).add(nom);
        }
        
        return indexMap;
    
}}
