package modele;

import static org.junit.Assert.assertEquals;

import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Test;

import ihm.NosFromages;
import ihm.Panier;
import ihm.MajPanier;

public class TestMiseAjourStocks {

    private Panier panier;
    private Fromage fromage;
    private Article article;

    @Before
    public void setUp() {
        panier = new Panier(new NosFromages()); 
        panier.viderPanierConfirmé();
        fromage = new Fromage("Camembert");
        fromage.addDescription("Un délicieux camembert");
        fromage.addNomImage("camembert");
        fromage.updateTypeFromage(TypeLait.VACHE); 
        article = new Article(fromage, "250g", 10.0f);
        article.setQuantitéEnStock(10);
        fromage.addArticle("250g", 10.0f); 
    }

    @Test
    public void testAjouterAuPanier() {
        int quantite = 2;

        // Ajoute l'article au panier
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();

        // Vérifie que l'article a ete ajoute au panier
        DefaultTableModel tableModel = (DefaultTableModel) panier.getTable().getModel();
        assertEquals(1, tableModel.getRowCount());
        assertEquals(article, tableModel.getValueAt(0, 0)); 
        assertEquals(2, tableModel.getValueAt(0, 3));
        assertEquals("20,0", tableModel.getValueAt(0, 4).toString());

        // Ajoute encore une fois le meme article
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();

        // Verifie que la quantite a ete mise a jour correctement
        assertEquals(1, tableModel.getRowCount()); 
        assertEquals(4, tableModel.getValueAt(0, 3)); // Quantite mise a jour
        assertEquals("40,0", tableModel.getValueAt(0, 4).toString()); // Prix total mis a jour
    }

    @Test //(expected = PanierException.class)
    public void testViderPanier() {
        int quantite = 1;

        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();

        panier.viderPanierConfirmé();

        DefaultTableModel tableModel = (DefaultTableModel) panier.getTable().getModel();
        assertEquals(0, tableModel.getRowCount());
    }
}