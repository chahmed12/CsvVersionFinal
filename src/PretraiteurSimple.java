import java.util.List;

public class PretraiteurSimple implements pretraiteur{
    
  public List<String> traiter(List<String> L){
    for (int i = 0; i < L.size(); i++){
      L.set(i, L.get(i).toLowerCase());

    }
    return L;

  }
}
