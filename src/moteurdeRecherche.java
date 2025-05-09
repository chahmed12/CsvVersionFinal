import java.util.ArrayList;
import java.util.List;

public class moteurdeRecherche {
    private comparateurNom comparateurNom;
    private generateurCandidat generateurCandidat;
    private selectionneur selectionneur;
    private pretraiteur pretraiteur;
    private recuperateur recuperateur;
    public moteurdeRecherche(comparateurNom comparateurNom,generateurCandidat generateurCandidat,selectionneur selectionneur,pretraiteur pretraiteur,recuperateur recuperateur){
        this.comparateurNom = comparateurNom;
        this.generateurCandidat = generateurCandidat ;
        this.selectionneur = selectionneur ;
        this.pretraiteur=pretraiteur;
        this.recuperateur=recuperateur;

    }
    public List<EntreeNom> rechercher(String nom) {
        String nomTraite = pretraiteur.traiter(nom);
        EntreeNom nomrech =new EntreeNom(nomTraite);
        List<EntreeNom> lp= new ArrayList<>();
        lp.add(nomrech);
        List<EntreeNom> lr=new ArrayList<>();
        for (EntreeNom n :recuperateur.recuperer()){
            lr.add(new EntreeNom(pretraiteur.traiter(n.get_Nom())));
        }

        List<CoupleNom> candidats = generateurCandidat.generer(lr,lp);
        List<NomScore> scores = new ArrayList<>();

        for (CoupleNom candidat : candidats) {
             double score = comparateurNom.comparer(candidat.getNom1(),candidat.getNom2());
            scores.add(new NomScore(candidat.getNom1(), score));
        }

        return selectionneur.selectionner(scores); 
    }


    
    
}
