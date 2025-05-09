import java.util.List;

public class App {
    public static void main(String[] args) {
        pretraiteur p=new PretraiteurSimple();
        pretraiteur p1=new pretraiteurPhonetique();
        List<pretraiteur> ps =List.of(p,p1);
        recuperateurCsv r =new recuperateurCsv("C:/Users/lassa/Downloads/peps_names_658k.csv");
        generateurCandidat g =new generateurSimple();
        comparateurNom c=new comparateurLevenshtein();
        selectionneur s=new selectionneurSimple(0.5);
        moteurdeRecherche m = new moteurdeRecherche(c,g,s,ps);
        for (CoupleNomScore n : m.rechercher(r.recuperer(),"moussa")) { 
            System.out.println("Name: " + n.getNom11() + ", Score: " + n.getScore());
        }

    }
}
