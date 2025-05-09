import java.util.ArrayList;
import java.util.List;
public class generateurSimple implements generateurCandidat{
    public List<CoupleNom> generer(List<EntreeNom> l1,List<EntreeNom> l2){
        List<CoupleNom> lf = new ArrayList<>();
        for(EntreeNom e : l1){
            for (EntreeNom c :l2){
                lf.add(new CoupleNom(e,c));
            }
        }
        return lf;
    }
}