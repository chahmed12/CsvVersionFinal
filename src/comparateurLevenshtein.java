public class comparateurLevenshtein implements comparateurNom{
    public double comparer(EntreeNom nom1, EntreeNom nom2) {
        // Récupère les noms sous forme de chaînes de caractères
        String nom1Str = nom1.get_Nom();
        String nom2Str = nom2.get_Nom();

        // Calcule la distance de Levenshtein entre les deux noms
        int distance = distanceLevenshtein(nom1Str, nom2Str);
        
        // Plus la distance est faible, plus les noms sont similaires, donc nous calculons un score inversé
        // Normalisation entre 0 et 1 : un score plus proche de 1 indique plus de similarité.
        int maxLength = Math.max(nom1Str.length(), nom2Str.length());
        double score = 1.0 - (double) distance / maxLength;
        
        return score;
    }

    // Méthode pour calculer la distance de Levenshtein
    private int distanceLevenshtein(String str1, String str2) {
        int lenStr1 = str1.length();
        int lenStr2 = str2.length();
        int[][] dp = new int[lenStr1 + 1][lenStr2 + 1];

        for (int i = 0; i <= lenStr1; i++) {
            for (int j = 0; j <= lenStr2; j++) {
                if (i == 0) {
                    dp[i][j] = j; // La distance entre une chaîne vide et str2
                } else if (j == 0) {
                    dp[i][j] = i; // La distance entre une chaîne vide et str1
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1));
                }
            }
        }
        return dp[lenStr1][lenStr2];
}}
