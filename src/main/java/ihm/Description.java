package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import modele.Article;
import modele.Fromage;

// Classe principale de l'interface "Description"
public class Description extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panneauPrincipal;
    private JComboBox<Object> comboBox;
    private JSpinner spinner;
    private JLabel nomFromageLabel;
    private JButton boutonAjouterPanier;

    // Constructeur de la classe Description
    public Description(Fromage fromage, Panier panierFrame, NosFromages nosFromagesFrame) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 618, 404);
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panneauPrincipal.setBackground(new Color(255, 247, 232));

        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BorderLayout(0, 0));

        nomFromageLabel = new JLabel(fromage.getDésignation());
        nomFromageLabel.setForeground(new Color(254, 172, 1));
        nomFromageLabel.setFont(new Font("Gentium Book Basic", Font.BOLD, 30));
        nomFromageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panneauPrincipal.add(nomFromageLabel, BorderLayout.NORTH);

        JPanel panneauQteAjoutPanierPrix = new JPanel();
        panneauQteAjoutPanierPrix.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauQteAjoutPanierPrix, BorderLayout.SOUTH);
        panneauQteAjoutPanierPrix.setLayout(new BoxLayout(panneauQteAjoutPanierPrix, BoxLayout.Y_AXIS));

        JPanel panneauPrixQuantité = new JPanel();
        panneauPrixQuantité.setBackground(new Color(255, 247, 232));
        panneauQteAjoutPanierPrix.add(panneauPrixQuantité);

        comboBox = new JComboBox<>(fromage.getArticles().toArray());
        comboBox.setRenderer(new CustomComboBoxRenderer());
        panneauPrixQuantité.add(comboBox);

        Article articleSélectionné = (Article) comboBox.getSelectedItem();
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, articleSélectionné != null ? articleSélectionné.getQuantitéEnStock() : 0, 1));
        panneauPrixQuantité.add(spinner);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreÀJourSpinnerEtComboBox();
            }
        });

        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setBackground(new Color(255, 247, 232));
        panneauQteAjoutPanierPrix.add(panneauBoutons);

        boutonAjouterPanier = new JButton("Ajouter au panier");
        boutonAjouterPanier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Article articleSélectionné = (Article) comboBox.getSelectedItem();
                if (articleSélectionné instanceof Article) {
                    int quantite = (int) spinner.getValue();
                    MajPanier.ajouterAuPanier(panierFrame.getTable(), articleSélectionné, quantite);
                    panierFrame.recalculerPanier();
                    nosFromagesFrame.mettreÀJourTotalPanier(MajPanier.calculerSousTotal(panierFrame.getTable()));
                    articleSélectionné.setQuantitéEnStock(articleSélectionné.getQuantitéEnStock() - quantite);
                    mettreÀJourSpinnerEtComboBox();
                    panierFrame.mettreÀJourStockEtDisponibilité(); // Mettre à jour le stock et la disponibilité
                }
            }
        });
        panneauBoutons.add(boutonAjouterPanier);

        JButton boutonAnnulerAchat = new JButton("Annuler");
        boutonAnnulerAchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panneauBoutons.add(boutonAnnulerAchat);

        JPanel panneauInformations = new JPanel();
        panneauInformations.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauInformations, BorderLayout.CENTER);
        panneauInformations.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel labelImageFromage = new JLabel("");
        String cheminImage = getCheminImage(fromage, "hauteur200");
        ImageIcon iconeImage = new ImageIcon(cheminImage);
        Image image = iconeImage.getImage();
        Image imageRedimensionnée = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        iconeImage = new ImageIcon(imageRedimensionnée);
        labelImageFromage.setIcon(iconeImage);
        panneauInformations.add(labelImageFromage);

        JPanel panneauDescription = new JPanel();
        panneauDescription.setBorder(new TitledBorder(new LineBorder(Color.ORANGE), "Description", TitledBorder.LEADING, TitledBorder.TOP, new Font("Source Serif Pro Black", Font.PLAIN, 14), Color.ORANGE));
        panneauDescription.setBackground(new Color(255, 247, 232));
        panneauInformations.add(panneauDescription);
        panneauDescription.setLayout(new BorderLayout(0, 0));

        JTextPane texteDescriptif = new JTextPane();
        texteDescriptif.setEditable(false);
        texteDescriptif.setText(fromage.getDescription());
        panneauDescription.add(texteDescriptif, BorderLayout.CENTER);

        mettreÀJourSpinnerEtComboBox();
    }

    // Met à jour le spinner et la comboBox
    private void mettreÀJourSpinnerEtComboBox() {
        comboBox.setRenderer(new CustomComboBoxRenderer());
        Object articleSélectionné = comboBox.getSelectedItem();

        boolean stockDisponible = false;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            if (item instanceof Article) {
                Article article = (Article) item;
                if (article.getQuantitéEnStock() > 0) {
                    stockDisponible = true;
                    break;
                }
            }
        }

        if (!stockDisponible) {
            comboBox.addItem("Stock indisponible");
            spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
            spinner.setEnabled(false);
            boutonAjouterPanier.setEnabled(false);
        } else {
            if (articleSélectionné instanceof Article) {
                Article article = (Article) articleSélectionné;
                if (article.getQuantitéEnStock() == 0) {
                    comboBox.removeItem(article);
                    if (comboBox.getItemCount() == 0) {
                        comboBox.addItem("Stock indisponible");
                        spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
                        spinner.setEnabled(false);
                        boutonAjouterPanier.setEnabled(false);
                    } else {
                        article = (Article) comboBox.getSelectedItem();
                        if (article != null) {
                            int stock = article.getQuantitéEnStock();
                            spinner.setModel(new SpinnerNumberModel(1, 1, stock, 1));
                            spinner.setEnabled(true);
                            boutonAjouterPanier.setEnabled(true);
                        }
                    }
                } else {
                    int stock = article.getQuantitéEnStock();
                    if (stock > 0) {
                        spinner.setModel(new SpinnerNumberModel(1, 1, stock, 1));
                        spinner.setEnabled(true);
                        boutonAjouterPanier.setEnabled(true);
                    } else {
                        spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
                        spinner.setEnabled(false);
                        boutonAjouterPanier.setEnabled(false);
                    }
                }
            } else {
                spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
                spinner.setEnabled(false);
                boutonAjouterPanier.setEnabled(false);
            }
        }
    }

    // Retourne le chemin de l'image du fromage
    private String getCheminImage(Fromage fromage, String hauteur) {
        String nomFichier = fromage.getNomImage();
        return "src/main/resources/images/fromages/" + hauteur + "/" + nomFichier + ".jpg";
    }

    // Renderer personnalisé pour la comboBox
    private class CustomComboBoxRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if ("Stock indisponible".equals(value)) {
                c.setForeground(Color.GRAY);
                c.setEnabled(false);
            } else {
                c.setEnabled(true);
            }
            return c;
        }
    }
}
