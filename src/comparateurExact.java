public class comparateurExact implements comparateurNom {
    public double comparer(EntreeNom nom1,EntreeNom nom2){
        if (nom1.get_Nom().equals(nom2.get_Nom())){
            return 1.0;
        
        }
        return 0.0;
    }
    
}
