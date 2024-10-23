package modele;

import static org.junit.Assert.assertEquals;

import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Test;

import ihm.NosFromages;
import ihm.Panier;
import ihm.MajPanier;

public class TestVerificationPanier {

    private Panier panier;
    private Fromage fromage;
    private Article article;

    @Before
    public void setUp() throws PanierException {
        panier = new Panier(new NosFromages());
        panier.viderPanierConfirmé();
        fromage = new Fromage("Brie");
        fromage.addNomImage("brie");
        fromage.addDescription("A soft cheese from France");
        fromage.updateTypeFromage(TypeLait.VACHE); 
        article = new Article(fromage, "250g", 15.0f);
        article.setQuantitéEnStock(10); 
        fromage.addArticle("250g", 15.0f); 
    }

    @Test
    public void testAjouterAuPanierNouvelArticle() {
        int quantite = 1;
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        DefaultTableModel model = (DefaultTableModel) panier.getTable().getModel();
        assertEquals(1, model.getRowCount());
        assertEquals("15,0", model.getValueAt(0, 4).toString()); 
    }

    @Test
    public void testAjouterAuPanierArticleExistant() {
        int quantite = 1;
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        DefaultTableModel model = (DefaultTableModel) panier.getTable().getModel();
        assertEquals(1, model.getRowCount());
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        assertEquals(1, model.getRowCount());
        assertEquals("30,0", model.getValueAt(0, 4).toString()); 
    }

    @Test //(expected = PanierException.class)
    public void testViderPanier() {
        int quantite = 1;
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        DefaultTableModel model = (DefaultTableModel) panier.getTable().getModel();
        assertEquals(1, model.getRowCount());
        panier.viderPanierConfirmé();
        panier.recalculerPanier();
        assertEquals(0, model.getRowCount());
    }

    @Test //(expected = PanierException.class)
    public void testViderPanierConfirméThrowsException() {
        int quantite = 1;
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        panier.viderPanierConfirmé();
    }
}