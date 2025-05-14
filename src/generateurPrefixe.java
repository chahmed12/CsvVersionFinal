import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class generateurPrefixe implements generateurCandidat {
    
   private int taillePrefixe;

    public generateurPrefixe(int taillePrefixe) {
        this.taillePrefixe = taillePrefixe;
    }

    public List<CoupleNom> generer(List<EntreeNom> l1, List<EntreeNom> l2) {
        List<CoupleNom> couples = new ArrayList<>();

        // Création d'un index hashmap pour l2 (groupe les EntreeNom par préfixe)
        Map<String, List<EntreeNom>> indexPrefixe = new HashMap<>();
        for (EntreeNom nom : l2) {
            String cle = prefixe(nom.get_Nom());
            if (cle != null) {
                 if (!indexPrefixe.containsKey(cle)) {
                indexPrefixe.put(cle, new ArrayList<>());
}
                indexPrefixe.get(cle).add(nom);
            }
        }

        // Pour chaque nom de l1, on cherche les correspondants dans l2 via le préfixe
        for (EntreeNom nom1 : l1) {
            String cle = prefixe(nom1.get_Nom());
            if (cle != null && indexPrefixe.containsKey(cle)) {
                for (EntreeNom nom2 : indexPrefixe.get(cle)) {
                    couples.add(new CoupleNom(nom1, nom2));
                }
            }
        }

        return couples;
    }

    private String prefixe(String nom) {
        if (nom.length() < taillePrefixe) return null;
        return nom.toLowerCase().substring(0, taillePrefixe);
    }}