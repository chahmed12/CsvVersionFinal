import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppGUI {
    static moteurdeRecherche moteur = createDefaultMoteur();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Application de recherche");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new GridLayout(5, 1, 10, 10));

            JButton btnRecherche = new JButton("1. Effectuer une recherche");
            JButton btnComparer = new JButton("2. Comparer deux listes");
            JButton btnDedupliquer = new JButton("3. Dédupliquer une liste");
            JButton btnConfigurer = new JButton("4. Configurer les paramètres");
            JButton btnQuitter = new JButton("5. Quitter");

            btnRecherche.addActionListener(e -> effectuerRecherche());
            btnComparer.addActionListener(e -> comparerListes());
            btnDedupliquer.addActionListener(e -> dedupliquerListe());
            btnConfigurer.addActionListener(e -> configurerMoteur());
            btnQuitter.addActionListener(e -> System.exit(0));

            frame.add(btnRecherche);
            frame.add(btnComparer);
            frame.add(btnDedupliquer);
            frame.add(btnConfigurer);
            frame.add(btnQuitter);

            frame.setVisible(true);
        });
    }

    private static moteurdeRecherche createDefaultMoteur() {
        List<pretraiteur> ps = List.of(new PretraiteurSimple());
        generateurCandidat g = new generateurSimple();
        comparateurNom c = new comparateurLevenshtein();
        selectionneur s = new selectionneurSimple(0.4);
        return new moteurdeRecherche(c, g, s, ps);
    }

    private static void effectuerRecherche() {
        String nom = JOptionPane.showInputDialog("Entrez le nom à rechercher :");
        if (nom == null || nom.isBlank()) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir un fichier CSV");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String chemin = fileChooser.getSelectedFile().getAbsolutePath();
        recuperateurCsv r = new recuperateurCsv(chemin);

        List<CoupleNomScore> resultats = moteur.rechercher(r.recuperer(), nom);

        StringBuilder sb = new StringBuilder();
        for (CoupleNomScore res : resultats) {
            sb.append("Nom : ").append(res.getNom11())
              .append(" | Score : ").append(res.getScore()).append("\n");
        }

        JOptionPane.showMessageDialog(null,
            resultats.isEmpty() ? "Aucun résultat trouvé." : sb.toString(),
            "Résultats",
            JOptionPane.INFORMATION_MESSAGE);
    }
    private static void dedupliquerListe() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Choisir un fichier CSV à dédoublonner");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    int result = fileChooser.showOpenDialog(null);
    if (result != JFileChooser.APPROVE_OPTION) return;

    String chemin = fileChooser.getSelectedFile().getAbsolutePath();
    recuperateurCsv r = new recuperateurCsv(chemin);

    List<EntreeNom> noms = r.recuperer();
    List<CoupleNomScore> doublons = moteur.dedupliquerListe(noms);

    StringBuilder sb = new StringBuilder();
    for (CoupleNomScore c : doublons) {
        sb.append("Nom1 : ").append(c.getNom11())
          .append(" | Nom2 : ").append(c.getNom22())
          .append(" | Score : ").append(c.getScore()).append("\n");
    }

    JOptionPane.showMessageDialog(
        null,
        doublons.isEmpty() ? "Aucun doublon détecté." : sb.toString(),
        "Doublons détectés",
        JOptionPane.INFORMATION_MESSAGE
    );
}

    private static void comparerListes() {
    
    JFileChooser fileChooser1 = new JFileChooser();
    fileChooser1.setDialogTitle("Choisir le premier fichier CSV");
    fileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result1 = fileChooser1.showOpenDialog(null);
    if (result1 != JFileChooser.APPROVE_OPTION) return;
    String chemin1 = fileChooser1.getSelectedFile().getAbsolutePath();


    JFileChooser fileChooser2 = new JFileChooser();
    fileChooser2.setDialogTitle("Choisir le deuxième fichier CSV");
    fileChooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result2 = fileChooser2.showOpenDialog(null);
    if (result2 != JFileChooser.APPROVE_OPTION) return;
    String chemin2 = fileChooser2.getSelectedFile().getAbsolutePath();

    recuperateurCsv r1 = new recuperateurCsv(chemin1);
    recuperateurCsv r2 = new recuperateurCsv(chemin2);
    List<EntreeNom> liste1 = r1.recuperer();
    List<EntreeNom> liste2 = r2.recuperer();


    List<CoupleNomScore> resultats = moteur.comparerListes(liste1, liste2);

    StringBuilder sb = new StringBuilder();
    for (CoupleNomScore res : resultats) {
        sb.append("Nom1 : ").append(res.getNom11())
          .append(" | Nom2 : ").append(res.getNom22())
          .append(" | Score : ").append(res.getScore()).append("\n");
    }

    JOptionPane.showMessageDialog(null,
        resultats.isEmpty() ? "Aucune correspondance trouvée." : sb.toString(),
        "Résultats de comparaison",
        JOptionPane.INFORMATION_MESSAGE);
}


    private static void configurerMoteur() {
        List<pretraiteur> pretraiteurs = new ArrayList<>();

        int simple = JOptionPane.showConfirmDialog(null, "Utiliser Pretraiteur Simple ?", "Prétraitement", JOptionPane.YES_NO_OPTION);
        if (simple == JOptionPane.YES_OPTION) pretraiteurs.add(new PretraiteurSimple());

        int phonetique = JOptionPane.showConfirmDialog(null, "Utiliser Pretraiteur Phonetique ?", "Prétraitement", JOptionPane.YES_NO_OPTION);
        if (phonetique == JOptionPane.YES_OPTION) pretraiteurs.add(new pretraiteurPhonetique());

        if (pretraiteurs.isEmpty()) {
            pretraiteurs.add(new PretraiteurSimple());
        }

        String[] comparateurs = {"Exact", "Levenshtein"};
        int choixComparateur = JOptionPane.showOptionDialog(null, "Choisir comparateur :",
                "Comparateur", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, comparateurs, comparateurs[0]);

        comparateurNom comparateur = (choixComparateur == 1) ? new comparateurLevenshtein() : new comparateurExact();
        generateurCandidat generateur = new generateurSimple();

        String seuilStr = JOptionPane.showInputDialog("Donner le seuil de sélection (ex: 0.5):");
        double seuil = 0.5;
        try {
            if (seuilStr != null && !seuilStr.isEmpty()) {
                seuil = Double.parseDouble(seuilStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valeur de seuil invalide, 0.5 utilisé par défaut.");
        }

        selectionneur s = new selectionneurSimple(seuil);
        moteur = new moteurdeRecherche(comparateur, generateur, s, pretraiteurs);
        JOptionPane.showMessageDialog(null, "Paramètres configurés avec succès !");
    }
}
