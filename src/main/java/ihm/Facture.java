package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

// Classe principale pour afficher la facture
public class Facture extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panneauPrincipal;
    private JTable table;
    private JTextPane texteInfoFacture;
    private JLabel labelSousTotal;
    private JLabel labelExpedition;
    private JLabel labelTotal;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Facture frame = new Facture("", "", "", "", "", "", "", "", "", "", "", new DefaultTableModel());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructeur de la classe Facture
    public Facture(String nom, String prénom, String adresse1, String adresse2, String codePostal, String ville, String mail, String téléphone, String modePaiement, String abonnement, String dateTime, DefaultTableModel tableModel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600); // Taille ajustée
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20)); // Ajout de marges autour du contenu
        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BorderLayout(0, 0));

        JLabel labelFacture = new JLabel("Votre facture");
        labelFacture.setForeground(new Color(248, 173, 20));
        labelFacture.setIcon(new ImageIcon("src/main/resources/images/logo.png"));
        labelFacture.setPreferredSize(new Dimension(58, 40));
        labelFacture.setHorizontalAlignment(SwingConstants.CENTER);
        labelFacture.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 32));
        panneauPrincipal.add(labelFacture, BorderLayout.NORTH);

        JPanel corps = new JPanel();
        panneauPrincipal.add(corps, BorderLayout.CENTER);
        corps.setLayout(new BorderLayout(0, 0));

        // Panneau pour le texte "Merci de votre visite"
        JPanel panneauMerci = new JPanel(new BorderLayout());
        panneauMerci.setBorder(new MatteBorder(1, 1, 0, 1, Color.BLACK)); // Ajout de bordures noires en haut et sur les côtés
        JLabel labelMerci = new JLabel("Merci de votre visite !");
        labelMerci.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
        labelMerci.setHorizontalAlignment(SwingConstants.CENTER);
        panneauMerci.add(labelMerci, BorderLayout.CENTER);
        corps.add(panneauMerci, BorderLayout.NORTH);

        texteInfoFacture = new JTextPane();
        texteInfoFacture.setContentType("text/html");
        texteInfoFacture.setText("<html><body style='font-family: DejaVu Sans; font-size: 14px;'>"
            + "<p style='color: orange; font-family: Arial;'>Fromagerie BlancJus pour vous servir et resservir en fromages</p>"
            + "Commande du <i>" + dateTime + "</i>.<br><br>"
            + "<b>" + nom + " " + prénom + "</b><br><br>"
            + "<span style='margin-left: 20px;'>Adresse : <i>" + adresse1 + " " + adresse2 + " " + codePostal + " " + ville + "</i></span><br><br>"
            + "<span style='margin-left: 20px;'>Téléphone : <i>" + téléphone + "</i></span><br><br>"
            + "<span style='margin-left: 20px;'>Mèl : <i>" + mail + "</i></span><br><br>"
            + "<span style='margin-left: 20px;'>Mode de paiement : <i>" + modePaiement + "</i></span><br><br>"
            + "<span style='margin-left: 20px;'>Abonnement à la newsletter : <i>" + abonnement + "</i></span>"
            + "</body></html>");
        texteInfoFacture.setEditable(false);

        JScrollPane panneauTexte = new JScrollPane(texteInfoFacture);
        panneauTexte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        corps.add(panneauTexte, BorderLayout.NORTH);

        JPanel panneauTable = new JPanel(new BorderLayout());
        table = new JTable(tableModel);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));

        table.removeColumn(table.getColumn("Supprimer"));
        JTableHeader enTeteTable = table.getTableHeader();
        enTeteTable.setDefaultRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel labelEnTete = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                labelEnTete.setFont(new Font("Roboto", Font.BOLD, 14));
                labelEnTete.setBackground(new Color(239, 171, 70));
                labelEnTete.setOpaque(true);
                return labelEnTete;
            }
        });

        table.setRowHeight(30); // Ajustement de la hauteur des lignes pour une meilleure lisibilité
        table.setPreferredScrollableViewportSize(table.getPreferredSize()); // Ajustement de la taille de la table en fonction de son contenu
        JScrollPane panneauTableTexte = new JScrollPane(table);
        panneauTable.add(panneauTableTexte, BorderLayout.CENTER);
        corps.add(panneauTable, BorderLayout.CENTER);

        JPanel panneauPied = new JPanel(new BorderLayout());
        panneauPied.setBorder(new EmptyBorder(5, 5, 5, 5));
        corps.add(panneauPied, BorderLayout.SOUTH);

        JPanel panneauTotaux = new JPanel();
        panneauTotaux.setLayout(new BoxLayout(panneauTotaux, BoxLayout.Y_AXIS));
        
        JPanel panneauSousTotal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTitreSousTotal = new JLabel("Sous-total :");
        labelTitreSousTotal.setFont(new Font("Roboto", Font.BOLD, 14));
        panneauSousTotal.add(labelTitreSousTotal);

        labelSousTotal = new JLabel(); // À définir plus tard
        labelSousTotal.setFont(new Font("Roboto", Font.PLAIN, 14));
        panneauSousTotal.add(labelSousTotal);
        double sousTotal = MajPanier.calculerSousTotal(table);
        labelSousTotal.setText(String.valueOf(sousTotal)+"€");
        panneauTotaux.add(panneauSousTotal);

        JPanel panneauExpedition = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTitreExpedition = new JLabel("Frais d'expédition :");
        labelTitreExpedition.setFont(new Font("Roboto", Font.BOLD, 14));
        panneauExpedition.add(labelTitreExpedition);

        labelExpedition = new JLabel(); // À définir plus tard
        labelExpedition.setFont(new Font("Roboto", Font.PLAIN, 14));
        panneauExpedition.add(labelExpedition);
        double fraisExpedition = MajPanier.calculerExpedition(table, Panier.comboBox.getSelectedItem().toString()); // Calcul des frais d'expédition
        labelExpedition.setText(String.valueOf(fraisExpedition)+"€");
        panneauTotaux.add(panneauExpedition);

        JPanel panneauTotal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTitreTotal = new JLabel("Total :");
        labelTitreTotal.setFont(new Font("Roboto", Font.BOLD, 14));
        panneauTotal.add(labelTitreTotal);

        labelTotal = new JLabel(); // À définir plus tard
        labelTotal.setFont(new Font("Roboto", Font.PLAIN, 14));
        panneauTotal.add(labelTotal);
        double total = fraisExpedition + sousTotal; // Calcul du total
        labelTotal.setText(total+"€");
        panneauTotaux.add(panneauTotal);

        panneauPied.add(panneauTotaux, BorderLayout.WEST);

        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setBorder(new EmptyBorder(5, 5, 5, 5)); // Ajout d'une petite marge autour des boutons
        panneauPied.add(panneauBoutons, BorderLayout.EAST);

        JButton boutonImprimer = new JButton("Imprimer");
        boutonImprimer.setFont(new Font("Roboto", Font.PLAIN, 14));
        boutonImprimer.setBackground(new Color(255, 206, 111));
        panneauBoutons.add(boutonImprimer);

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.setFont(new Font("Roboto", Font.PLAIN, 14));
        boutonQuitter.setBackground(new Color(239, 171, 70));
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panneauBoutons.add(boutonQuitter);

        pack(); // Ajustement de la taille de la fenêtre pour inclure tous les composants
    }
    
    // Méthode pour mettre à jour la table
    public void updateTable(DefaultTableModel tableModel) {
        table.setModel(tableModel);
    }

    // Méthode pour mettre à jour les prix affichés
    public void updatePrice(double sousTotal, double expedition, double total) {
        String sousTotalHtml = String.format("Sous-total : %.2f €<br>", sousTotal);
        String expeditionHtml = String.format("Frais d'expédition : %.2f €<br>", expedition);
        String totalHtml = String.format("Total : %.2f €", total);

        texteInfoFacture.setText(texteInfoFacture.getText() + sousTotalHtml + expeditionHtml + totalHtml);
    }
}
