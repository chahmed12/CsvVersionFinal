import java.util.ArrayList;
import java.util.List;

public class pretraiteurPhonetique implements pretraiteur{
    public List<String> traiter(List<String> noms) {
        List<String> resultats = new ArrayList<>();
        for (String nom : noms) {
            String transforme = nom.toLowerCase();

            transforme = transforme.replace("ph", "f");
            transforme = transforme.replace("ch", "k");
            transforme = transforme.replace("qu", "k");
            transforme = transforme.replace("c", "k");
            transforme = transforme.replace("ou", "u");

        
            if (transforme.endsWith("e")) {
                transforme = transforme.substring(0, transforme.length() - 1);
            }

            resultats.add(transforme);
        }
        return resultats;
    }
}