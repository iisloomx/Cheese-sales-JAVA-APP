package ihm;

import modele.Article;
import modele.Fromage;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Image;

// Classe utilitaire pour les opérations du panier
public class MajPanier {

    // Ajoute un article au panier
    public static void ajouterAuPanier(JTable table, Article article, int quantite) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        boolean found = false;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Article existingArticle = (Article) tableModel.getValueAt(i, 0);
            if (existingArticle.equals(article)) {
                int quantiteExistante = (int) tableModel.getValueAt(i, 3);
                int nouvelleQuantite = quantiteExistante + quantite;
                tableModel.setValueAt(nouvelleQuantite, i, 3);
                tableModel.setValueAt(String.format("%.1f", nouvelleQuantite * article.getPrixTTC()), i, 4);
                found = true;
                break;
            }
        }

        if (!found) {
            String imagePath = getImagePath(article.getFromage(), "hauteur40");
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            tableModel.addRow(new Object[]{
                article,
                imageIcon,
                article.getFromage().getDésignation() + " - " + article.getClé(),
                quantite,
                String.format("%.1f", quantite * article.getPrixTTC()),
                "P" // Ajouter le bouton de suppression
            });
        }
    }

    // Calcule le sous-total du panier
    public static double calculerSousTotal(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        double sousTotal = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object prixTotalObj = tableModel.getValueAt(i, 4);
            double prixTotal = 0.0;
            if (prixTotalObj instanceof String) {
                prixTotal = Double.parseDouble(((String) prixTotalObj).replace(",", "."));
            } else if (prixTotalObj instanceof Double) {
                prixTotal = (Double) prixTotalObj;
            } else if (prixTotalObj instanceof Float) {
                prixTotal = ((Float) prixTotalObj).doubleValue();
            }
            sousTotal += prixTotal;
        }
        return sousTotal;
    }

    // Calcule les frais d'expédition
    public static double calculerExpedition(JTable table, String transporteur) {
        double sousTotal = calculerSousTotal(table);
        double expedition = 0.0;
        switch (transporteur) {
            case "Colissimo":
            case "Chronorelais":
                if (sousTotal < 60) {
                    expedition = 14.90;
                } else if (sousTotal < 90) {
                    expedition = 9.90;
                } else if (sousTotal < 120) {
                    expedition = 4.90;
                } else {
                    expedition = 0.0;
                }
                break;
            case "Chronofresh":
                if (sousTotal < 50) {
                    expedition = 23.80;
                } else if (sousTotal < 80) {
                    expedition = 17.80;
                } else if (sousTotal < 120) {
                    expedition = 9.90;
                } else {
                    expedition = 0.0;
                }
                break;
            default:
                expedition = 0.0;
        }
        return expedition;
    }

    // Retourne le chemin de l'image du fromage
    private static String getImagePath(Fromage fromage, String hauteur) {
        String fileName = fromage.getNomImage();
        return "src/main/resources/images/fromages/" + hauteur + "/" + fileName + ".jpg";
    }
}
