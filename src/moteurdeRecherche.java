import java.util.ArrayList;
import java.util.List;

public class moteurdeRecherche {
    private comparateurNom comparateurNom;
    private generateurCandidat generateurCandidat;
    private selectionneur selectionneur;
    private List<pretraiteur> pretraiteurs;
    public moteurdeRecherche(comparateurNom comparateurNom,generateurCandidat generateurCandidat,selectionneur selectionneur,List<pretraiteur> pretraiteurs){
        this.comparateurNom = comparateurNom;
        this.generateurCandidat = generateurCandidat ;
        this.selectionneur = selectionneur ;
        this.pretraiteurs=pretraiteurs;

    }
    private List<String> appliquerPretraiteurs(List<String> noms) {
        List<String> resultats = noms;
        for (pretraiteur p : pretraiteurs) {
            resultats = p.traiter(resultats);
        }
        //for(String s :resultats){
           // System.out.println("-"+s);}
        return resultats;
    }
    public List<CoupleNomScore> rechercher(List<EntreeNom> entreeNoms,String nom) {
        List<String> l=new ArrayList<>();
        l.add(nom);
        List<String> Ls = appliquerPretraiteurs(l);
        EntreeNom nomrech =new EntreeNom(Ls.get(0));
        List<EntreeNom> lp= new ArrayList<>();
        lp.add(nomrech);
        List<EntreeNom> lr = new ArrayList<>();
        List<String> nomsaTraites=new ArrayList<>();

        for (EntreeNom n : entreeNoms) {
            nomsaTraites.add(n.get_Nom());
            
        }
        List<String> nomsTraites=appliquerPretraiteurs(nomsaTraites);
        for (String s:nomsTraites){
            lr.add(new EntreeNom(s));
        }

        
        List<CoupleNom> candidats = generateurCandidat.generer(lr,lp);
        //for (CoupleNom candidat : candidats){
            // System.out.println("Nom1: " + candidat.getNom1().get_Nom() + ", Nom2: " + candidat.getNom2().get_Nom());}
        List<CoupleNomScore> couplesScores = new ArrayList<>();

        
      for (int i=0;i<candidats.size();i++){
             double score = comparateurNom.comparer(candidats.get(i).getNom1(),candidats.get(i).getNom2());
             couplesScores.add(new CoupleNomScore((entreeNoms.get(i)).get_Nom(),candidats.get(i).getNom2().get_Nom(), score));
             //System.out.println("Nom1: " + (entreeNoms.get(i)).get_Nom() + ", Nom2: " + (candidats.get(i)).getNom2().get_Nom()+" "+score);
             }

                  
        
        

        return selectionneur.selectionner(couplesScores);
    }


    
    
}
