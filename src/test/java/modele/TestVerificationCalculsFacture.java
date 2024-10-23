package modele;

import static org.junit.Assert.assertEquals;

import javax.swing.table.DefaultTableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ihm.NosFromages;
import ihm.Panier;
import ihm.MajPanier;

public class TestVerificationCalculsFacture {
	private Article article;
	private Panier panier;
	private Fromage fromage;
	
	@Before
    public void setUp() {
		panier = new Panier(new NosFromages());
        panier.viderPanierConfirmé();
        fromage = new Fromage("Gorgonzola");
        fromage.addNomImage("gorgonzola");
        fromage.addDescription("A soft cheese from France");
        fromage.updateTypeFromage(TypeLait.VACHE); 
        article = new Article(fromage, "250g", 5.20f);
        article.setQuantitéEnStock(10); 
        fromage.addArticle("250g", 5.20f); 
        panier.viderPanierConfirmé();
        fromage = new Fromage("Gorgonzola");
    }
	
	@After
	public void tearDown() {
		panier = null;
		fromage = null;
	}
	
	@Test
	public void testAcheterUnFromage() {
		int quantite = 1;
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        DefaultTableModel model = (DefaultTableModel) panier.getTable().getModel();
        assertEquals("5,2",model.getValueAt(0,4));
	}
	
	@Test
	public void testAcheterTousLesFromagesEnStock() {
		int quantite = 10;
        MajPanier.ajouterAuPanier(panier.getTable(), article, quantite);
        panier.recalculerPanier();
        DefaultTableModel model = (DefaultTableModel) panier.getTable().getModel();
        assertEquals("52,0",model.getValueAt(0,4));
	}
	
	@Test (expected = modele.PanierException.class)
	public void testPlusDeFromagesEnStock() {
        MajPanier.ajouterAuPanier(panier.getTable(), article, 10);
        article.préempterQuantité(10);
        panier.recalculerPanier();
        MajPanier.ajouterAuPanier(panier.getTable(), article, 1);
        article.préempterQuantité(1);
        article.setQuantitéEnStock(article.getQuantitéEnStock());
	}
}