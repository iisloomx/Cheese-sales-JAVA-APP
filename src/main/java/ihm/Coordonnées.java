package ihm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Coordonnées extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panneauPrincipal;
    private JTextField champTexteNom;
    private JTextField champTextePrenom;
    private JTextField champTexteAdresse1;
    private JTextField champTexteAdresse2;
    private JTextField champTexteCodePostal;
    private JTextField champTexteVille;
    private JTextField champTexteMail;
    private JTextField champTexteTelephone;
    private final ButtonGroup groupePaiement = new ButtonGroup();
    private final ButtonGroup groupeAbonnement = new ButtonGroup();
    private JRadioButton boutonCarteCredit;
    private JRadioButton boutonPaypal;
    private JRadioButton boutonCheque;
    private JRadioButton boutonAbonnementOui;
    private JRadioButton boutonAbonnementNon;

    public Coordonnées(DefaultTableModel tableModel) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 620, 516);
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panneauPrincipal.setBackground(new Color(255, 247, 232)); // couleur de fond de la fenêtre principale
        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BorderLayout(0, 0));

        JPanel panneauTitre = new JPanel();
        panneauTitre.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauTitre, BorderLayout.NORTH);
        panneauTitre.setLayout(new BorderLayout(0, 0));

        // Ajout des images à gauche et à droite du titre
        ImageIcon iconeCoordonnees = new ImageIcon(new ImageIcon("src/main/resources/images/Panier/coordonnees.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JLabel labelIconeGauche = new JLabel(iconeCoordonnees);
        JLabel labelIconeDroite = new JLabel(iconeCoordonnees);
        panneauTitre.add(labelIconeGauche, BorderLayout.WEST);
        panneauTitre.add(labelIconeDroite, BorderLayout.EAST);

        JLabel labelTitre = new JLabel("Vos coordonnées");
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitre.setForeground(new Color(255, 128, 0));
        panneauTitre.add(labelTitre, BorderLayout.CENTER);

        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauBoutons, BorderLayout.SOUTH);

        JButton boutonAnnuler = new JButton("Annuler");
        panneauBoutons.add(boutonAnnuler);

        JButton boutonValider = new JButton("OK");
        panneauBoutons.add(boutonValider);

        JButton boutonRemplir = new JButton("Remplir");
        panneauBoutons.add(boutonRemplir);

        JPanel panneauInfos = new JPanel();
        panneauInfos.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauInfos, BorderLayout.CENTER);
        panneauInfos.setLayout(null);

        JLabel labelNom = new JLabel("Nom :");
        labelNom.setBounds(30, 25, 130, 14);
        panneauInfos.add(labelNom);

        JLabel labelPrenom = new JLabel("Prénom :");
        labelPrenom.setBounds(30, 50, 130, 14);
        panneauInfos.add(labelPrenom);

        JLabel labelAdresse1 = new JLabel("Adresse 1 :");
        labelAdresse1.setBounds(30, 75, 130, 14);
        panneauInfos.add(labelAdresse1);

        JLabel labelAdresse2 = new JLabel("Adresse 2 :");
        labelAdresse2.setBounds(30, 100, 130, 14);
        panneauInfos.add(labelAdresse2);

        JLabel labelCodePostal = new JLabel("Code Postal :");
        labelCodePostal.setBounds(30, 125, 130, 14);
        panneauInfos.add(labelCodePostal);

        JLabel labelVille = new JLabel("Ville :");
        labelVille.setBounds(30, 150, 130, 14);
        panneauInfos.add(labelVille);

        JLabel labelMail = new JLabel("Mail :");
        labelMail.setBounds(30, 175, 130, 14);
        panneauInfos.add(labelMail);

        JLabel labelTelephone = new JLabel("Téléphone :");
        labelTelephone.setBounds(30, 200, 130, 14);
        panneauInfos.add(labelTelephone);

        champTexteNom = new JTextField();
        champTexteNom.setBounds(190, 22, 372, 20);
        panneauInfos.add(champTexteNom);
        champTexteNom.setColumns(10);

        champTextePrenom = new JTextField();
        champTextePrenom.setBounds(190, 47, 372, 20);
        panneauInfos.add(champTextePrenom);
        champTextePrenom.setColumns(10);

        champTexteAdresse1 = new JTextField();
        champTexteAdresse1.setBounds(190, 72, 372, 20);
        panneauInfos.add(champTexteAdresse1);
        champTexteAdresse1.setColumns(10);

        champTexteAdresse2 = new JTextField();
        champTexteAdresse2.setBounds(190, 97, 372, 20);
        panneauInfos.add(champTexteAdresse2);
        champTexteAdresse2.setColumns(10);

        champTexteCodePostal = new JTextField();
        champTexteCodePostal.setBounds(190, 122, 372, 20);
        panneauInfos.add(champTexteCodePostal);
        champTexteCodePostal.setColumns(10);

        champTexteVille = new JTextField();
        champTexteVille.setBounds(190, 147, 372, 20);
        panneauInfos.add(champTexteVille);
        champTexteVille.setColumns(10);

        champTexteMail = new JTextField();
        champTexteMail.setBounds(190, 172, 372, 20);
        panneauInfos.add(champTexteMail);
        champTexteMail.setColumns(10);

        champTexteTelephone = new JTextField();
        champTexteTelephone.setBounds(190, 197, 372, 20);
        panneauInfos.add(champTexteTelephone);
        champTexteTelephone.setColumns(10);

        // Ajout des placeholders pour tous les champs
        addPlaceholder(champTexteNom, "Ex: Dupont");
        addPlaceholder(champTextePrenom, "Ex: Jean");
        addPlaceholder(champTexteAdresse1, "Ex: 123 rue de Paris");
        addPlaceholder(champTexteAdresse2, "Ex: Appartement 4B");
        addPlaceholder(champTexteCodePostal, "Ex: 75001");
        addPlaceholder(champTexteVille, "Ex: Paris");
        addPlaceholder(champTexteMail, "Ex: jean.dupont@example.com");
        addPlaceholder(champTexteTelephone, "Ex: 0612345678");

        // Ajouter les boutons radio pour le mode de paiement
        JPanel panneauPaiement = new JPanel();
        panneauPaiement.setBorder(new TitledBorder(new LineBorder(Color.ORANGE), "Moyen de paiement", TitledBorder.LEADING, TitledBorder.TOP, null, Color.ORANGE));
        panneauPaiement.setBackground(new Color(255, 247, 232));
        panneauPaiement.setBounds(30, 230, 532, 60);
        panneauInfos.add(panneauPaiement);
        panneauPaiement.setLayout(null);

        boutonCarteCredit = new JRadioButton("Carte de crédit");
        boutonCarteCredit.setBackground(new Color(255, 247, 232));
        boutonCarteCredit.setBounds(20, 25, 109, 23);
        groupePaiement.add(boutonCarteCredit);
        panneauPaiement.add(boutonCarteCredit);

        boutonPaypal = new JRadioButton("Paypal");
        boutonPaypal.setBackground(new Color(255, 247, 232));
        boutonPaypal.setBounds(200, 25, 109, 23);
        groupePaiement.add(boutonPaypal);
        panneauPaiement.add(boutonPaypal);

        boutonCheque = new JRadioButton("Chèque");
        boutonCheque.setBackground(new Color(255, 247, 232));
        boutonCheque.setBounds(380, 25, 109, 23);
        groupePaiement.add(boutonCheque);
        panneauPaiement.add(boutonCheque);

        // Ajouter les boutons radio pour l'abonnement à la newsletter
        JPanel panneauAbonnement = new JPanel();
        panneauAbonnement.setBorder(new TitledBorder(new LineBorder(Color.ORANGE), "Abonnement à notre Newsletter", TitledBorder.LEADING, TitledBorder.TOP, null, Color.ORANGE));
        panneauAbonnement.setBackground(new Color(255, 247, 232));
        panneauAbonnement.setBounds(30, 300, 532, 60);
        panneauInfos.add(panneauAbonnement);
        panneauAbonnement.setLayout(null);

        boutonAbonnementOui = new JRadioButton("Oui");
        boutonAbonnementOui.setBackground(new Color(255, 247, 232));
        boutonAbonnementOui.setBounds(170, 25, 109, 23);
        groupeAbonnement.add(boutonAbonnementOui);
        panneauAbonnement.add(boutonAbonnementOui);

        boutonAbonnementNon = new JRadioButton("Non");
        boutonAbonnementNon.setBackground(new Color(255, 247, 232));
        boutonAbonnementNon.setBounds(310, 25, 109, 23);
        groupeAbonnement.add(boutonAbonnementNon);
        panneauAbonnement.add(boutonAbonnementNon);

        // Ajouter les listeners pour passer d'un champ à l'autre avec la touche Entrée
        addEnterKeyTraversal(champTexteNom, champTextePrenom);
        addEnterKeyTraversal(champTextePrenom, champTexteAdresse1);
        addEnterKeyTraversal(champTexteAdresse1, champTexteAdresse2);
        addEnterKeyTraversal(champTexteAdresse2, champTexteCodePostal);
        addEnterKeyTraversal(champTexteCodePostal, champTexteVille);
        addEnterKeyTraversal(champTexteVille, champTexteMail);
        addEnterKeyTraversal(champTexteMail, champTexteTelephone);

        boutonValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder messageErreur = new StringBuilder("Veuillez remplir les champs suivants :\n");

                boolean valide = true;
                if (champTexteNom.getText().trim().isEmpty() || champTexteNom.getText().equals("Ex: Dupont")) {
                    valide = false;
                    messageErreur.append("- Nom\n");
                }
                if (champTextePrenom.getText().trim().isEmpty() || champTextePrenom.getText().equals("Ex: Jean")) {
                    valide = false;
                    messageErreur.append("- Prénom\n");
                }
                if (champTexteAdresse1.getText().trim().isEmpty() || champTexteAdresse1.getText().equals("Ex: 123 rue de Paris")) {
                    valide = false;
                    messageErreur.append("- Adresse 1\n");
                }
                if (champTexteCodePostal.getText().trim().isEmpty() || champTexteCodePostal.getText().equals("Ex: 75001")) {
                    valide = false;
                    messageErreur.append("- Code Postal\n");
                }
                if (champTexteVille.getText().trim().isEmpty() || champTexteVille.getText().equals("Ex: Paris")) {
                    valide = false;
                    messageErreur.append("- Ville\n");
                }
                if (champTexteMail.getText().trim().isEmpty() || champTexteMail.getText().equals("Ex: jean.dupont@example.com")) {
                    valide = false;
                    messageErreur.append("- Mail\n");
                }
                if (champTexteTelephone.getText().trim().isEmpty() || champTexteTelephone.getText().equals("Ex: 0612345678")) {
                    valide = false;
                    messageErreur.append("- Téléphone\n");
                }
                if (groupePaiement.getSelection() == null) {
                    valide = false;
                    messageErreur.append("- Moyen de paiement\n");
                }
                if (groupeAbonnement.getSelection() == null) {
                    valide = false;
                    messageErreur.append("- Abonnement\n");
                }

                if (valide) {
                    String nom = champTexteNom.getText();
                    String prénom = champTextePrenom.getText();
                    String adresse1 = champTexteAdresse1.getText();
                    String adresse2 = champTexteAdresse2.getText();
                    String codePostal = champTexteCodePostal.getText();
                    String ville = champTexteVille.getText();
                    String mail = champTexteMail.getText();
                    String téléphone = champTexteTelephone.getText();
                    String modePaiement = boutonCarteCredit.isSelected() ? "Carte de crédit" : boutonPaypal.isSelected() ? "Paypal" : "Chèque";
                    String abonnement = boutonAbonnementOui.isSelected() ? "Oui" : "Non";

                    LocalDateTime maintenant = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy 'à' HH:mm:ss");
                    String dateHeureFormatee = maintenant.format(formatter);

                    Facture facture = new Facture(nom, prénom, adresse1, adresse2, codePostal, ville, mail, téléphone, modePaiement, abonnement, dateHeureFormatee, tableModel);
                    facture.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Coordonnées.this, messageErreur.toString(), "Champs manquants", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        boutonRemplir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                champTexteNom.setText("Dupont");
                champTextePrenom.setText("Jean");
                champTexteAdresse1.setText("123 rue de Paris");
                champTexteAdresse2.setText("Appartement 4B");
                champTexteCodePostal.setText("75001");
                champTexteVille.setText("Paris");
                champTexteMail.setText("jean.dupont@example.com");
                champTexteTelephone.setText("0612345678");
                boutonCarteCredit.setSelected(true);
                boutonAbonnementOui.setSelected(true);
            }
        });

        boutonAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addEnterKeyTraversal(JTextField champActuel, JTextField champSuivant) {
        champActuel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    champSuivant.requestFocus();
                }
            }
        });
    }

    private void addPlaceholder(JTextField champTexte, String placeholder) {
        champTexte.setForeground(Color.GRAY);
        champTexte.setText(placeholder);
        champTexte.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (champTexte.getText().equals(placeholder)) {
                    champTexte.setText("");
                    champTexte.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (champTexte.getText().isEmpty()) {
                    champTexte.setForeground(Color.GRAY);
                    champTexte.setText(placeholder);
                }
            }
        });
        champTexte.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isDigit(c) && c != ' ' && c != '-' && c != '@' && c != '.') {
                    e.consume();
                }
            }
        });
    }
}
