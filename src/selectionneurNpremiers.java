import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class selectionneurNpremiers implements selectionneur {
    private int n;

    public selectionneurNpremiers(int n) {
        this.n = n;
    }

    public List<CoupleNomScore> selectionner(List<CoupleNomScore> candidats) {
       
        candidats.sort(Comparator.comparingDouble(CoupleNomScore::getScore).reversed());

    

    
        List<CoupleNomScore> resultat = new ArrayList<>();
        for (int i = 0; i < Math.min(n, candidats.size()); i++) {
            resultat.add(candidats.get(i));
        }

        return resultat;
    }
}
