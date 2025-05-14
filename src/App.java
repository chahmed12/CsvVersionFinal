import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
       AppGUI.main(args);
    
        
        Scanner scanner = new Scanner(System.in);
        pretraiteur p=new PretraiteurSimple();
        pretraiteur p1=new pretraiteurPhonetique();
        List<pretraiteur> ps =List.of(p,p1);
        generateurCandidat g =new generateurSimple();
        comparateurNom c=new comparateurLevenshtein();
        selectionneur s=new selectionneurNpremiers(10);
        moteurdeRecherche m = new moteurdeRecherche(c,g,s,ps);
        
         while(true){
            afficherMenuPrincipal();
            System.out.print("Choisissez une option : ");
            String choix = scanner.nextLine();
             switch (choix) {
                case "1":
                    effectuerRecherche(scanner,m);
                    break;
                case "2":
                    comparerListes(scanner,m);
                    break;
                case "3":
                 
                   dedupliquerListe(scanner,m);
                    break;
                case "4":
                    m=configurerParametres(scanner);
                    break;
                case "5":
                    System.out.println("Fermeture de l'application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
         }
        
    }
     private static void afficherMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Effectuer une recherche");
        System.out.println("2. Comparer deux listes");
        System.out.println("3. Dedupliquer une liste");
        System.out.println("4. Configurer les parametres");
        System.out.println("5. Quitter");
    }
    
     private static void effectuerRecherche(Scanner scanner,moteurdeRecherche m) {
        System.out.print("Entrez le nom à rechercher : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le chemin du fichier CSV : ");
        String fichier = scanner.nextLine();
        recuperateurCsv r =new recuperateurCsv(fichier);
        System.out.println("Recherche de \"" + nom + "\" dans le fichier : " + fichier);
        long startTime = System.nanoTime();

        List<CoupleNomScore> rech=m.rechercher(r.recuperer(),nom);
        
        long endTime = System.nanoTime();
         long duration = endTime - startTime;

        for (CoupleNomScore n : rech) { 
            System.out.println("Name: " + n.getNom11() + ", Score: " + n.getScore());
        }
        System.out.println("Temps d'exécution : " + (duration / 1000000.0) + " millisecondes");
    }
    private static void comparerListes(Scanner scanner, moteurdeRecherche m){
        System.out.print("Entrez le chemin du  1er fichier CSV : ");
        String fichier1 = scanner.nextLine();
        System.out.print("Entrez le chemin du  2eme fichier CSV : ");
        String fichier2 = scanner.nextLine();
        recuperateurCsv r1 =new recuperateurCsv(fichier1);
        recuperateurCsv r2 =new recuperateurCsv(fichier2);
        
         long startTime = System.nanoTime();
        List<CoupleNomScore> ns = m.comparerListes(r1.recuperer(),r2.recuperer());
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        for (CoupleNomScore n : ns ) { 
           System.out.println("Nom1: " + n.getNom11() +" "+" Nom2: "+n.getNom22()+ ", Score: " + n.getScore());
        }
        System.out.println("Comparaison de \"" + fichier1 + "\" avec le fichier : " + fichier2);
        System.out.println("Temps d'exécution : " + (duration / 1000000.0) + " millisecondes");







    }
    private static void dedupliquerListe(Scanner scanner, moteurdeRecherche m) {
    System.out.print("Entrez le chemin du fichier CSV à dédoublonner : ");
    String fichier = scanner.nextLine();

    recuperateurCsv r = new recuperateurCsv(fichier);

    System.out.println("Détection de doublons dans le fichier : " + fichier);
    List<EntreeNom> liste = r.recuperer();
    long startTime = System.nanoTime();

    
    List<CoupleNomScore> doublons = m.dedupliquerListe(liste);

    
    long endTime = System.nanoTime();
    long duration = endTime - startTime;

    for (CoupleNomScore n : doublons) {
        System.out.println("Nom1: " + n.getNom11() + "  Nom2: " + n.getNom22() + ", Score: " + n.getScore());
    }
    System.out.println("Temps d'exécution : " + (duration / 1000000.0) + " millisecondes");
}

    private static moteurdeRecherche configurerParametres(Scanner scanner) {
          System.out.println("Configuration du moteur de recherche (laisser vide pour les valeurs par défaut)");
          List<pretraiteur> pretraiteurs = new ArrayList<>();
          System.out.print("Appliquer 'pretraiteur simple' ? (O/N) : ");
          String choix = scanner.nextLine();
         if (choix.equalsIgnoreCase("O")) {
              pretraiteurs.add(new PretraiteurSimple());
              }
        System.out.print("Appliquer 'pretraiteur phonetique' ? (O/N) : ");
        choix = scanner.nextLine();
         if (choix.equalsIgnoreCase("O")) {
              pretraiteurs.add(new pretraiteurPhonetique());
           }
        if (pretraiteurs.isEmpty()) {
    
        pretraiteurs.add(new PretraiteurSimple());
         }
        System.out.print("Choisir comparateur (1: Exact, 2: Levenshtein, 3: JaroWinkler) : ");
        choix = scanner.nextLine();
        comparateurNom comparateur;
        if (choix.equals("2")) {
        comparateur = new comparateurLevenshtein(); 
        } 
         if (choix.equals("3")) {
        comparateur = new comparateurJarowinkler(); 
        }  
       else {
        comparateur = new comparateurExact(); 
        }
         System.out.print("Choisir generateur (1: Simple, 2: Indexer, 3: Prefixe) : ");
        choix = scanner.nextLine();
        generateurCandidat generateur;
         if (choix.equals("1")) {
        generateur = new generateurSimple(); 
        }
       else if (choix.equals("2")) {
        generateur = new generateurIndexer();
} 
        else  {
       System.out.print("donner la taille de prefixe ");
       int taille = scanner.nextInt();
       scanner.nextLine(); 
        generateur = new generateurPrefixe(taille); 
        }
        System.out.print("Choisir selectionner (1: Simple, 2: Npremieres) : ");
        choix = scanner.nextLine();
        selectionneur s;
        if (choix.equals("1")) {
        System.out.print("donner le seuil de selection ");
        double Seuil=scanner.nextDouble();
        scanner.nextLine();
         s =new selectionneurSimple(Seuil); 
        }
        else{
            System.out.print("donner N ");
            int n = scanner.nextInt(); 
             scanner.nextLine();
            s=new selectionneurNpremiers(n);

        }
        

        return new moteurdeRecherche(comparateur, generateur, s, pretraiteurs);
        

    }

}
