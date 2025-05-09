import java.util.ArrayList;
import java.util.List;

public class selectionneurSimple implements selectionneur {
    private double seuil;
    public selectionneurSimple(double seuil){
        this.seuil=seuil;

    }
    public List<CoupleNomScore> selectionner(List<CoupleNomScore> candidats) {
        List<CoupleNomScore> meilleurs = new ArrayList<>();
        for (CoupleNomScore c : candidats) {
            if (c.getScore() >= seuil) {
                meilleurs.add(c);
            }
        }
        return meilleurs;
    }
}
