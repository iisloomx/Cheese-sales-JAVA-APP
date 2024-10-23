package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import modele.Article;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Panier extends JFrame {
    private static final long serialVersionUID = 1L;
    private static Panier panier;
    private JPanel panneauPrincipal;
    JTable table;
    private DefaultTableModel modeleTable;
    private JTextField champTexteSousTotal;
    private JTextField champTexteExpedition;
    private JTextField champTexteTotal;
    static JComboBox<String> comboBox;
    private JLabel labelImageExpedition;
    private NosFromages nosFromagesFrame;

    public Panier(NosFromages nosFromagesFrame) {
        this.nosFromagesFrame = nosFromagesFrame;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 500);
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panneauPrincipal.setBackground(new Color(255, 247, 232));

        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));

        JPanel panneauTitreEtPanier = new JPanel();
        panneauTitreEtPanier.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauTitreEtPanier);
        panneauTitreEtPanier.setLayout(new BoxLayout(panneauTitreEtPanier, BoxLayout.X_AXIS));

        JPanel panneauTitrePanier = new JPanel();
        panneauTitrePanier.setBackground(new Color(255, 247, 232));
        panneauTitrePanier.setBorder(new EmptyBorder(0, 0, 0, 10));
        panneauTitreEtPanier.add(panneauTitrePanier);
        panneauTitrePanier.setLayout(new BorderLayout(0, 0));

        ImageIcon iconePanier = new ImageIcon(new ImageIcon("src/main/resources/images/Panier/IconFrom.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JLabel labelTitrePanier = new JLabel("Votre Panier", iconePanier, JLabel.LEFT);
        panneauTitrePanier.add(labelTitrePanier, BorderLayout.CENTER);
        labelTitrePanier.setForeground(new Color(255, 128, 0));
        labelTitrePanier.setFont(new Font("Source Serif Pro Black", Font.PLAIN, 18));
        labelTitrePanier.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelBoutonRecalculer = new JPanel();
        panelBoutonRecalculer.setBackground(new Color(255, 247, 232));
        panelBoutonRecalculer.setBorder(new EmptyBorder(0, 10, 0, 0));
        panneauTitreEtPanier.add(panelBoutonRecalculer);
        panelBoutonRecalculer.setLayout(new BorderLayout(0, 0));

        JButton boutonRecalculerPanier = new JButton("Recalculer le panier");
        boutonRecalculerPanier.setForeground(Color.BLACK);
        boutonRecalculerPanier.setBackground(new Color(255, 153, 0));
        boutonRecalculerPanier.setFont(new Font("Source Serif Pro Black", Font.PLAIN, 10));
        boutonRecalculerPanier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculerPanier();
            }
        });
        panelBoutonRecalculer.add(boutonRecalculerPanier, BorderLayout.CENTER);

        panelBoutonRecalculer.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        
        JPanel panneauTable = new JPanel();
        panneauTable.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauTable);

        JPanel panneauScrollTable = new JPanel();
        panneauScrollTable.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauScrollTable);
        panneauScrollTable.setLayout(new BorderLayout(0, 0));

        modeleTable = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Article", "Image", "Nom et Poids", "Quantité", "Prix Total", "Supprimer"
            }
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 1) {
                    return ImageIcon.class;
                } else if (column == 5) {
                    return JButton.class;
                } else {
                    return Object.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 5;
            }
        };

        table = new JTable(modeleTable) {
            private static final long serialVersionUID = 1L;

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 3) {
                    return new SpinnerRenderer();
                } else if (column == 5) {
                    return new ButtonRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 3) {
                    return new SpinnerEditor();
                } else if (column == 5) {
                    return new ButtonEditor(new JCheckBox());
                }
                return super.getCellEditor(row, column);
            }
        };

        table.setRowHeight(40);
        JScrollPane scrollPane = new JScrollPane(table);
        panneauScrollTable.add(scrollPane, BorderLayout.CENTER);

        JPanel panneauExpédition = new JPanel();
        panneauExpédition.setBackground(new Color(255, 247, 232));
        panneauExpédition.setBorder(new EmptyBorder(10, 0, 0, 0));
        panneauPrincipal.add(panneauExpédition);
        panneauExpédition.setLayout(new BoxLayout(panneauExpédition, BoxLayout.X_AXIS));

        JPanel panneauTransporteur = new JPanel();
        panneauTransporteur.setBackground(new Color(255, 247, 232));
        panneauExpédition.add(panneauTransporteur);
        panneauTransporteur.setLayout(new BorderLayout(0, 0));

        JPanel panneauInfoTransporteur = new JPanel();
        panneauInfoTransporteur.setBorder(new TitledBorder(new LineBorder(Color.ORANGE), "Transporteur", TitledBorder.LEADING, TitledBorder.TOP, null, Color.ORANGE));
        panneauInfoTransporteur.setBackground(new Color(255, 247, 232));
        panneauInfoTransporteur.setLayout(new BorderLayout(0, 0));
        panneauTransporteur.add(panneauInfoTransporteur);

        JLabel labelInfoFraisPort = new JLabel("Frais de port offerts à partir de 120€");
        labelInfoFraisPort.setFont(new Font("Source Serif Pro Black", Font.BOLD, 10));
        labelInfoFraisPort.setHorizontalAlignment(SwingConstants.CENTER);
        panneauInfoTransporteur.add(labelInfoFraisPort, BorderLayout.NORTH);

        JPanel panneauImageExpedition = new JPanel();
        panneauImageExpedition.setBackground(new Color(255, 247, 232));
        panneauInfoTransporteur.add(panneauImageExpedition, BorderLayout.SOUTH);
        panneauImageExpedition.setLayout(new BoxLayout(panneauImageExpedition, BoxLayout.X_AXIS));

        JPanel panneauCentreExpedition = new JPanel();
        panneauCentreExpedition.setBackground(new Color(255, 247, 232));
        panneauImageExpedition.add(panneauCentreExpedition);
        panneauCentreExpedition.setLayout(new BorderLayout(0, 0));

        JPanel panneauNordExpedition = new JPanel();
        panneauNordExpedition.setBackground(new Color(255, 247, 232));
        panneauCentreExpedition.add(panneauNordExpedition, BorderLayout.NORTH);

        JPanel panneauSudExpedition = new JPanel();
        panneauSudExpedition.setBackground(new Color(255, 247, 232));
        panneauCentreExpedition.add(panneauSudExpedition, BorderLayout.SOUTH);

        // Initialisation du label pour l'image d'expédition
        labelImageExpedition = new JLabel();
        labelImageExpedition.setHorizontalAlignment(SwingConstants.CENTER);

        // Ajout de labelImageExpedition seulement à panneauSudExpedition
        panneauSudExpedition.add(labelImageExpedition);

        // Suppression de cette ligne pour éviter la double association
        // panneauCentreExpedition.add(labelImageExpedition, BorderLayout.CENTER);

        JPanel panneauOuestExpedition = new JPanel();
        panneauOuestExpedition.setBackground(new Color(255, 247, 232));
        panneauCentreExpedition.add(panneauOuestExpedition, BorderLayout.WEST);

        JPanel panneauEstExpedition = new JPanel();
        panneauEstExpedition.setBackground(new Color(255, 247, 232));
        panneauCentreExpedition.add(panneauEstExpedition, BorderLayout.EAST);

        comboBox = new JComboBox<>(new String[]{"Colissimo", "Chronorelais", "Chronofresh"});
        comboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXX");
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreÀJourExpéditionEtImage();
            }
        });
        panneauCentreExpedition.add(comboBox, BorderLayout.CENTER);

        panneauCentreExpedition.add(comboBox, BorderLayout.CENTER);

        panneauSudExpedition.add(labelImageExpedition); // Add labelImageExpedition here only

        JPanel panneauTotaux = new JPanel();
        panneauTotaux.setBackground(new Color(255, 247, 232));
        panneauExpédition.add(panneauTotaux);
        panneauTotaux.setLayout(new BorderLayout(0, 0));

        JPanel panneauSudTotaux = new JPanel();
        panneauSudTotaux.setBackground(new Color(255, 247, 232));
        panneauTotaux.add(panneauSudTotaux, BorderLayout.NORTH);
        panneauSudTotaux.setLayout(new BorderLayout(0, 0));

        JPanel panneauTotauxNord = new JPanel();
        panneauTotauxNord.setBackground(new Color(255, 247, 232));
        panneauTotaux.add(panneauTotauxNord, BorderLayout.SOUTH);

        JPanel panneauOuestTotaux = new JPanel();
        panneauOuestTotaux.setBackground(new Color(255, 247, 232));
        panneauTotaux.add(panneauOuestTotaux, BorderLayout.WEST);

        JPanel panneauEstTotaux = new JPanel();
        panneauEstTotaux.setBackground(new Color(255, 247, 232));
        panneauTotaux.add(panneauEstTotaux, BorderLayout.EAST);

        JPanel panneauCentreTotaux = new JPanel();
        panneauCentreTotaux.setBackground(new Color(255, 247, 232));
        panneauTotaux.add(panneauCentreTotaux, BorderLayout.CENTER);
        panneauCentreTotaux.setLayout(new BoxLayout(panneauCentreTotaux, BoxLayout.Y_AXIS));

        JPanel panneauSousTotal = new JPanel(new GridBagLayout());
        panneauSousTotal.setBackground(new Color(255, 247, 232));
        panneauCentreTotaux.add(panneauSousTotal);

        GridBagConstraints gbc_labelSousTotal = new GridBagConstraints();
        gbc_labelSousTotal.insets = new Insets(5, 5, 5, 5);
        gbc_labelSousTotal.anchor = GridBagConstraints.WEST;
        gbc_labelSousTotal.gridx = 0;
        gbc_labelSousTotal.gridy = 0;

        JLabel labelSousTotal = new JLabel("Sous-total :");
        labelSousTotal.setForeground(new Color(255, 128, 0));
        panneauSousTotal.add(labelSousTotal, gbc_labelSousTotal);

        GridBagConstraints gbc_champTexteSousTotal = new GridBagConstraints();
        gbc_champTexteSousTotal.insets = new Insets(5, 5, 5, 5);
        gbc_champTexteSousTotal.anchor = GridBagConstraints.WEST;
        gbc_champTexteSousTotal.gridx = 1;
        gbc_champTexteSousTotal.gridy = 0;

        champTexteSousTotal = new JTextField();
        champTexteSousTotal.setColumns(10);
        champTexteSousTotal.setEditable(false);
        panneauSousTotal.add(champTexteSousTotal, gbc_champTexteSousTotal);

        JPanel panneauExpedition = new JPanel(new GridBagLayout());
        panneauExpedition.setBackground(new Color(255, 247, 232));
        panneauCentreTotaux.add(panneauExpedition);

        GridBagConstraints gbc_labelExpedition = new GridBagConstraints();
        gbc_labelExpedition.insets = new Insets(5, 5, 5, 5);
        gbc_labelExpedition.anchor = GridBagConstraints.WEST;
        gbc_labelExpedition.gridx = 0;
        gbc_labelExpedition.gridy = 0;

        JLabel labelExpedition = new JLabel("Expédition :");
        labelExpedition.setForeground(new Color(255, 128, 0));
        panneauExpedition.add(labelExpedition, gbc_labelExpedition);

        GridBagConstraints gbc_champTexteExpedition = new GridBagConstraints();
        gbc_champTexteExpedition.insets = new Insets(5, 5, 5, 5);
        gbc_champTexteExpedition.anchor = GridBagConstraints.WEST;
        gbc_champTexteExpedition.gridx = 1;
        gbc_labelExpedition.gridy = 0;

        champTexteExpedition = new JTextField();
        champTexteExpedition.setColumns(10);
        champTexteExpedition.setEditable(false);
        panneauExpedition.add(champTexteExpedition, gbc_champTexteExpedition);

        JPanel panneauTotal = new JPanel(new GridBagLayout());
        panneauTotal.setBackground(new Color(255, 247, 232));
        panneauCentreTotaux.add(panneauTotal);

        GridBagConstraints gbc_labelTotal = new GridBagConstraints();
        gbc_labelTotal.insets = new Insets(5, 5, 5, 5);
        gbc_labelTotal.anchor = GridBagConstraints.WEST;
        gbc_labelTotal.gridx = 0;
        gbc_labelTotal.gridy = 0;

        JLabel labelTotal = new JLabel("Total :        ");
        labelTotal.setForeground(new Color(255, 128, 0));
        panneauTotal.add(labelTotal, gbc_labelTotal);

        GridBagConstraints gbc_champTexteTotal = new GridBagConstraints();
        gbc_champTexteTotal.insets = new Insets(5, 5, 5, 5);
        gbc_champTexteTotal.anchor = GridBagConstraints.WEST;
        gbc_champTexteTotal.gridx = 1;
        gbc_champTexteTotal.gridy = 0;

        champTexteTotal = new JTextField();
        champTexteTotal.setColumns(10);
        champTexteTotal.setEditable(false);
        panneauTotal.add(champTexteTotal, gbc_champTexteTotal);

        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauBoutons);
        panneauBoutons.setLayout(new BoxLayout(panneauBoutons, BoxLayout.X_AXIS));

        JPanel panneauValider = new JPanel();
        panneauValider.setBackground(new Color(255, 247, 232));
        panneauBoutons.add(panneauValider);
        panneauValider.setLayout(new BorderLayout(0, 0));

        JPanel panneauValiderInterne = new JPanel(new BorderLayout());
        panneauValiderInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauNordValider = new JPanel();
        panneauNordValider.setBackground(new Color(255, 247, 232));
        JPanel panneauSudValider = new JPanel();
        panneauSudValider.setBackground(new Color(255, 247, 232));
        JPanel panneauEstValider = new JPanel();
        panneauEstValider.setBackground(new Color(255, 247, 232));
        JPanel panneauOuestValider = new JPanel();
        panneauOuestValider.setBackground(new Color(255, 247, 232));
        JPanel panneauCentreValider = new JPanel();
        panneauCentreValider.setBackground(new Color(255, 247, 232));
        JButton boutonValiderPanier = new JButton("Valider le panier");

        panneauCentreValider.add(boutonValiderPanier);
        panneauValiderInterne.add(panneauNordValider, BorderLayout.NORTH);
        panneauValiderInterne.add(panneauSudValider, BorderLayout.SOUTH);
        panneauValiderInterne.add(panneauEstValider, BorderLayout.EAST);
        panneauValiderInterne.add(panneauOuestValider, BorderLayout.WEST);
        panneauValiderInterne.add(panneauCentreValider, BorderLayout.CENTER);

        boutonValiderPanier.setBackground(Color.LIGHT_GRAY);
        boutonValiderPanier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validerPanier();
            }
        });

        panneauValider.add(panneauValiderInterne, BorderLayout.CENTER);

        JPanel panneauVider = new JPanel();
        panneauVider.setBackground(new Color(255, 247, 232));
        panneauBoutons.add(panneauVider);
        panneauVider.setLayout(new BorderLayout(0, 0));

        JPanel panneauViderInterne = new JPanel(new BorderLayout());
        panneauViderInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauNordVider = new JPanel();
        panneauNordVider.setBackground(new Color(255, 247, 232));
        JPanel panneauSudVider = new JPanel();
        panneauSudVider.setBackground(new Color(255, 247, 232));
        JPanel panneauEstVider = new JPanel();
        panneauEstVider.setBackground(new Color(255, 247, 232));
        JPanel panneauOuestVider = new JPanel();
        panneauOuestVider.setBackground(new Color(255, 247, 232));
        JPanel panneauCentreVider = new JPanel();
        panneauCentreVider.setBackground(new Color(255, 247, 232));
        JButton boutonViderPanier = new JButton("Vider le panier");

        panneauCentreVider.add(boutonViderPanier);
        panneauViderInterne.add(panneauNordVider, BorderLayout.NORTH);
        panneauViderInterne.add(panneauSudVider, BorderLayout.SOUTH);
        panneauViderInterne.add(panneauEstVider, BorderLayout.EAST);
        panneauViderInterne.add(panneauOuestVider, BorderLayout.WEST);
        panneauViderInterne.add(panneauCentreVider, BorderLayout.CENTER);

        boutonViderPanier.setBackground(Color.LIGHT_GRAY);
        boutonViderPanier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viderPanier();
            }
        });

        panneauVider.add(panneauViderInterne, BorderLayout.CENTER);

        JPanel panneauContinuer = new JPanel();
        panneauContinuer.setBackground(new Color(255, 247, 232));
        panneauBoutons.add(panneauContinuer);
        panneauContinuer.setLayout(new BorderLayout(0, 0));

        JPanel panneauContinuerInterne = new JPanel(new BorderLayout());
        panneauContinuerInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauNordContinuer = new JPanel();
        panneauNordContinuer.setBackground(new Color(255, 247, 232));
        JPanel panneauSudContinuer = new JPanel();
        panneauSudContinuer.setBackground(new Color(255, 247, 232));
        JPanel panneauEstContinuer = new JPanel();
        panneauEstContinuer.setBackground(new Color(255, 247, 232));
        JPanel panneauOuestContinuer = new JPanel();
        panneauOuestContinuer.setBackground(new Color(255, 247, 232));
        JPanel panneauCentreContinuer = new JPanel();
        panneauCentreContinuer.setBackground(new Color(255, 247, 232));
        JButton boutonContinuerAchats = new JButton("Continuer les achats");

        panneauCentreContinuer.add(boutonContinuerAchats);
        panneauContinuerInterne.add(panneauNordContinuer, BorderLayout.NORTH);
        panneauContinuerInterne.add(panneauSudContinuer, BorderLayout.SOUTH);
        panneauContinuerInterne.add(panneauEstContinuer, BorderLayout.EAST);
        panneauContinuerInterne.add(panneauOuestContinuer, BorderLayout.WEST);
        panneauContinuerInterne.add(panneauCentreContinuer, BorderLayout.CENTER);

        boutonContinuerAchats.setBackground(new Color(255, 153, 0));
        boutonContinuerAchats.setForeground(Color.BLACK);
        boutonContinuerAchats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panneauContinuer.add(panneauContinuerInterne, BorderLayout.CENTER);
    }

    private void mettreÀJourExpéditionEtImage() {
        String optionSélectionnée = (String) comboBox.getSelectedItem();
        double fraisExpedition = MajPanier.calculerExpedition(table, optionSélectionnée);
        champTexteExpedition.setText(String.format("%.1f", fraisExpedition));

        String cheminImage = "";
        switch (optionSélectionnée) {
            case "Colissimo":
                cheminImage = "src/main/resources/images/livraison/Colissimo.png";
                break;
            case "Chronorelais":
                cheminImage = "src/main/resources/images/livraison/Chronorelais.png";
                break;
            case "Chronofresh":
                cheminImage = "src/main/resources/images/livraison/Chronofresh.png";
                break;
            default:
                cheminImage = "src/main/resources/images/default.png";
                break;
        }
        ImageIcon icone = new ImageIcon(new ImageIcon(cheminImage).getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH));
        labelImageExpedition.setIcon(icone);
    }

    public void recalculerPanier() {
        double sousTotal = MajPanier.calculerSousTotal(table);
        champTexteSousTotal.setText(String.format("%.1f", sousTotal));
        mettreÀJourExpéditionEtImage();
        double fraisExpedition = Double.parseDouble(champTexteExpedition.getText().replace(",", "."));
        double total = sousTotal + fraisExpedition;
        champTexteTotal.setText(String.format("%.1f", total));
        nosFromagesFrame.mettreÀJourTotalPanier(total);
    }

    public void viderPanier() {
        SupprimerPanier supprimerPanierDialog = new SupprimerPanier(this);
        supprimerPanierDialog.setVisible(true);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModeleTable() {
        return modeleTable;
    }

    private void validerPanier() {
        Coordonnées coordonnées = new Coordonnées(modeleTable);
        coordonnées.setVisible(true);
    }

    public void viderPanierConfirmé() {
        for (int i = 0; i < modeleTable.getRowCount(); i++) {
            ((modele.Article) modeleTable.getValueAt(i, 0)).rendreQuantité((int) modeleTable.getValueAt(i, 3));
        }
        modeleTable.setRowCount(0);
        recalculerPanier();
        ihm.NosFromages.labelTotalPanier.setText("0.0 €");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    panier = new Panier(null);
                    panier.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class SpinnerRenderer extends JSpinner implements TableCellRenderer {
        private static final long serialVersionUID = 1L;

        public SpinnerRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            setValue(value);
            return this;
        }
    }

    class SpinnerEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 1L;
        private JSpinner spinner;

        public SpinnerEditor() {
            super(new JTextField());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Article article = (Article) table.getValueAt(row, 0);
            int quantiteMax = article.getQuantitéEnStock() + (int) value;
            spinner = new JSpinner(new SpinnerNumberModel((int) value, 1, quantiteMax, 1));
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int quantite = (int) spinner.getValue();
                    table.setValueAt(quantite, row, 3);
                    table.setValueAt(String.format("%.1f", quantite * article.getPrixTTC()), row, 4);
                    recalculerPanier();
                    mettreÀJourStockEtDisponibilité();
                }
            });
            return spinner;
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        private ImageIcon iconePoubelle;

        public ButtonRenderer() {
            setOpaque(true);
            iconePoubelle = new ImageIcon(new ImageIcon("src/main/resources/images/Panier/trash.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            setIcon(iconePoubelle);
            setText(null);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 1L;
        private JButton button;
        private boolean isPushed;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setIcon(new ImageIcon(new ImageIcon("src/main/resources/images/Panier/trash.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            isPushed = true;
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (table.getCellEditor() != null) {
                    table.getCellEditor().stopCellEditing();
                }
                if (row >= 0 && row < modeleTable.getRowCount()) {
                    Article article = (Article) modeleTable.getValueAt(row, 0);
                    int quantite = (int) modeleTable.getValueAt(row, 3);
                    article.rendreQuantité(quantite);
                    modeleTable.removeRow(row);
                    recalculerPanier();
                    mettreÀJourStockEtDisponibilité();
                }
            }
            isPushed = false;
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    void mettreÀJourStockEtDisponibilité() {
        if (nosFromagesFrame != null) {
            nosFromagesFrame.mettreÀJourListeFromages("Trier par :");
        }
    }
}
