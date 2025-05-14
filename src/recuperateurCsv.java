
import java.io.*;
import java.util.*;
public class recuperateurCsv implements recuperateur {
    private  String fichierCsv;
    public recuperateurCsv(String fichierCsv) {
        this.fichierCsv = fichierCsv;
    }

    public List<EntreeNom> recuperer() {
        List<EntreeNom> noms = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.fichierCsv))) {
            String ligne;
            boolean premiereLigne = true;

            while ((ligne = reader.readLine()) != null) {
            
                if (premiereLigne) {
                    premiereLigne = false;
                    continue;
                }

                String[] parties = ligne.split(",");

                if (parties.length >= 2) {
                    String nomComplet = parties[0];
                    String id = parties[1];
                    noms.add(new EntreeNom(nomComplet, id));
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        
    return noms;
    }
}