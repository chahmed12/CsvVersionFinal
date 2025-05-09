import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        pretraiteur p=new PretraiteurSimple();
        recuperateurCsv r =new recuperateurCsv("C:/Users/lassa/Downloads/noms.csv");
        List<EntreeNom> lr=new ArrayList<>();
        for (EntreeNom n :r.recuperer()){
            lr.add(new EntreeNom(p.traiter(n.get_Nom())));
        }

        generateurCandidat g =new generateurSimple();
        comparateurExact c=new comparateurExact();
        selectionneur s=new selectionneurSimple(1.0);
        moteurdeRecherche m = new moteurdeRecherche(c,g,s,p,r);
        for (EntreeNom n : m.rechercher("paul")) {
            System.out.println("- les noms similaires : " + n.get_Nom());
        }

    }
}
