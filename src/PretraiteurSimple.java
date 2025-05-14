import java.util.ArrayList;
import java.util.List;

public class PretraiteurSimple implements pretraiteur{
    
 public List<String> traiter(List<String> noms) {
    List<String> resultats = new ArrayList<>(noms); 
    for (int i = 0; i < resultats.size(); i++) {
        String nom = resultats.get(i);
        nom = nom.toLowerCase().replaceAll("\\s+", "");  
        resultats.set(i, nom); 
    }
    return resultats;
}
}
