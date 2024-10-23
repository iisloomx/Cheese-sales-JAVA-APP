package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.stream.Collectors;
import modele.Fromage;
import modele.Fromages;
import modele.GenerationFromages;
import modele.TypeLait;
import java.io.File;

// Classe principale de l'interface "Nos Fromages"
public class NosFromages extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panneauPrincipal;
    private int indexSurvolé = -1;
    private Fromages fromagesObj;
    private JList<Fromage> listeFromages;
    private JComboBox<String> comboBox;
    private Panier panierFrame;
    public static JLabel labelTotalPanier; 

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    NosFromages frame = new NosFromages();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructeur de la classe NosFromages
    public NosFromages() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 642, 484);
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panneauPrincipal.setBackground(new Color(255, 255, 204));
        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BorderLayout(0, 0));

        fromagesObj = GenerationFromages.générationBaseFromages(); // Génération de la liste des fromages

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panneauPrincipal.add(scrollPane, BorderLayout.CENTER);

        listeFromages = new JList<>();
        listeFromages.setCellRenderer(new FromageListCellRenderer());
        listeFromages.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = listeFromages.locationToIndex(e.getPoint());
                if (index != indexSurvolé) {
                    indexSurvolé = index;
                    listeFromages.repaint();
                }
            }
        });
        listeFromages.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                indexSurvolé = -1;
                listeFromages.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listeFromages.locationToIndex(e.getPoint());
                    Fromage fromageSélectionné = listeFromages.getModel().getElementAt(index);
                    if (fromageSélectionné != null) {
                        ouvrirFenetreDescription(fromageSélectionné);
                    }
                }
            }
        });

        scrollPane.setViewportView(listeFromages);
        scrollPane.getViewport().setBackground(new Color(255, 255, 224));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 240, 200));
        scrollPane.setColumnHeaderView(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panneauPanier = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panneauPanier.setBackground(new Color(255, 240, 200));

        // Ajustement de la taille de l'icône du panier à 30x30
        ImageIcon iconePanierOriginale = new ImageIcon("src/main/resources/images/Panier/Panier.png");
        Image imagePanier = iconePanierOriginale.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon iconePanierRedimensionnée = new ImageIcon(imagePanier);

        JButton boutonPanier = new JButton(iconePanierRedimensionnée);
        boutonPanier.setPreferredSize(new Dimension(30, 30)); 
        boutonPanier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panierFrame == null) {
                    panierFrame = new Panier(NosFromages.this);  // Passe l'instance actuelle de NosFromages
                }
                panierFrame.setVisible(true);
            }
        });
        boutonPanier.setBorder(null);
        boutonPanier.setBorderPainted(false);
        boutonPanier.setContentAreaFilled(false);
        panneauPanier.add(boutonPanier);

        labelTotalPanier = new JLabel();
        labelTotalPanier.setText("0.0 €");
        panneauPanier.add(labelTotalPanier);

        panel.add(panneauPanier, BorderLayout.EAST);

        JPanel panneauTitre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauTitre.setBackground(new Color(255, 240, 200));
        panel.add(panneauTitre, BorderLayout.CENTER);

        JLabel titre = new JLabel("Nos fromages");
        titre.setBackground(new Color(255, 240, 200));
        titre.setForeground(new Color(255, 128, 64));
        titre.setFont(new Font("Source Serif Pro Black", Font.BOLD, 25));
        panneauTitre.add(titre);

        // Ajouter l'icône à côté du titre
        ImageIcon iconeFromage = new ImageIcon("src/main/resources/images/Panier/IconFrom.png");
        Image imageIcone = iconeFromage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel labelIcone = new JLabel(new ImageIcon(imageIcone));
        panneauTitre.add(labelIcone);

        JPanel panneauFiltre = new JPanel();
        panneauFiltre.setBackground(new Color(255, 255, 204));
        panneauPrincipal.add(panneauFiltre, BorderLayout.SOUTH);
        panneauFiltre.setLayout(new BorderLayout(0, 0));

        JLabel labelFiltre = new JLabel("Filtre");
        labelFiltre.setFont(new Font("Source Serif Pro Black", Font.BOLD, 15));
        labelFiltre.setForeground(new Color(255, 128, 64));
        labelFiltre.setBackground(new Color(255, 234, 193));
        panneauFiltre.add(labelFiltre, BorderLayout.NORTH);

        JPanel panneauComboBox = new JPanel();
        panneauComboBox.setBackground(new Color(255, 255, 204));
        panneauFiltre.add(panneauComboBox, BorderLayout.SOUTH);
        panneauComboBox.setLayout(new BorderLayout(0, 0));

        comboBox = new JComboBox<>(new String[]{"Trier par :", TypeLait.VACHE.toString(), TypeLait.CHEVRE.toString(), TypeLait.BREBIS.toString()});
        comboBox.setBackground(new Color(255, 204, 153));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == indexSurvolé) {
                    c.setBackground(new Color(255, 128, 64));
                } else {
                    c.setBackground(new Color(255, 204, 153));
                }
                return c;
            }
        });
        comboBox.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = comboBox.getSelectedIndex();
                if (index != indexSurvolé) {
                    indexSurvolé = index;
                    comboBox.repaint();
                }
            }
        });
        comboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                indexSurvolé = -1;
                comboBox.repaint();
            }
        });

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String typeSélectionné = (String) e.getItem();
                    mettreÀJourListeFromages(typeSélectionné);
                }
            }
        });

        panneauComboBox.add(comboBox);

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.setBackground(Color.LIGHT_GRAY);
        boutonQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panneauComboBox.add(boutonQuitter, BorderLayout.EAST);

        comboBox.setSelectedIndex(0); 
        mettreÀJourListeFromages("Trier par :");
    }

    // Méthode pour mettre à jour la liste des fromages selon le type de lait sélectionné
    void mettreÀJourListeFromages(String typeLait) {
        List<Fromage> fromagesFiltrés;
        if (typeLait.equals("Trier par :")) {
            fromagesFiltrés = fromagesObj.getFromages();
        } else {
            TypeLait typeSélectionné = TypeLait.valueOf(typeLait.toUpperCase());
            fromagesFiltrés = fromagesObj.getFromages().stream()
                    .filter(f -> f.getTypeFromage() == typeSélectionné)
                    .collect(Collectors.toList());
        }
        listeFromages.setListData(fromagesFiltrés.toArray(new Fromage[0]));
    }

    // Méthode pour ouvrir la fenêtre de description d'un fromage
    private void ouvrirFenetreDescription(Fromage fromage) {
        if (!estFromageDisponible(fromage)) {
            JOptionPane.showMessageDialog(this, "Le Fromage n'est actuellement plus disponible", "Indisponible", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (panierFrame == null) {
            panierFrame = new Panier(NosFromages.this); // Passe l'instance actuelle de NosFromages
        }
        Description descriptionFrame = new Description(fromage, panierFrame, this);
        descriptionFrame.setVisible(true);
    }

    // Vérifie si un fromage est disponible en stock
    private boolean estFromageDisponible(Fromage fromage) {
        return fromage.getArticles().stream().anyMatch(article -> article.getQuantitéEnStock() > 0);
    }

    // Mise à jour du total du panier
    public void mettreÀJourTotalPanier(double total) {
        labelTotalPanier.setText(String.format("%.1f", total) + " €");
    }

    // Renderer personnalisé pour la liste des fromages
    class FromageListCellRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (index == indexSurvolé) {
                c.setBackground(new Color(255, 255, 153));
            } else {
                c.setBackground(new Color(255, 255, 224));
            }

            if (value instanceof Fromage) {
                Fromage fromage = (Fromage) value;
                String nomImageFromage = fromage.getNomImage();
                setText(fromage.getDésignation());
                File fichierImage = new File("src/main/resources/images/fromages/hauteur40/" + nomImageFromage + ".jpg");
                if (fichierImage.exists()) {
                    ImageIcon iconeImage = new ImageIcon(fichierImage.getPath());
                    Image image = iconeImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(image));
                } else {
                    setIcon(null); 
                }
            }

            return c;
        }
    }
}
