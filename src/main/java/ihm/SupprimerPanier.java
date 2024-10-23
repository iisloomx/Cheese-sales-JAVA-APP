package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SupprimerPanier extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel panneauPrincipal;

    public SupprimerPanier(Panier panierFrame) {
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 200);
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panneauPrincipal.setBackground(new Color(255, 247, 232)); // couleur de fond de la fenêtre principale

        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BorderLayout(0, 0));

        JPanel panneauNord = new JPanel();
        panneauNord.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauNord, BorderLayout.NORTH);
        panneauNord.setLayout(new BorderLayout(0, 0));

        JPanel panneauCentre = new JPanel();
        panneauCentre.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauCentre, BorderLayout.CENTER);
        panneauCentre.setLayout(new BorderLayout(0, 0));

        JLabel labelConfirmation = new JLabel("Voulez-vous vraiment supprimer le panier ?");
        labelConfirmation.setFont(new Font("Source Serif Pro Black", Font.PLAIN, 14));
        labelConfirmation.setForeground(new Color(255, 128, 0));
        labelConfirmation.setHorizontalAlignment(SwingConstants.CENTER);
        panneauCentre.add(labelConfirmation, BorderLayout.CENTER);

        JPanel panneauSud = new JPanel();
        panneauSud.setBackground(new Color(255, 247, 232));
        panneauPrincipal.add(panneauSud, BorderLayout.SOUTH);
        panneauSud.setLayout(new BorderLayout(0, 0));

        JPanel panneauInterne = new JPanel(new BorderLayout());
        panneauInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauNordInterne = new JPanel();
        panneauNordInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauSudInterne = new JPanel();
        panneauSudInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauEstInterne = new JPanel();
        panneauEstInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauOuestInterne = new JPanel();
        panneauOuestInterne.setBackground(new Color(255, 247, 232));
        JPanel panneauCentreInterne = new JPanel();
        panneauCentreInterne.setBackground(new Color(255, 247, 232));

        JButton boutonOui = new JButton("Oui");
        boutonOui.setBackground(Color.LIGHT_GRAY);
        boutonOui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panierFrame.viderPanierConfirmé(); 
                dispose();
            }
        });

        JButton boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.setBackground(Color.LIGHT_GRAY);
        boutonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panneauCentreInterne.add(boutonOui);
        panneauCentreInterne.add(boutonAnnuler);

        panneauInterne.add(panneauNordInterne, BorderLayout.NORTH);
        panneauInterne.add(panneauSudInterne, BorderLayout.SOUTH);
        panneauInterne.add(panneauEstInterne, BorderLayout.EAST);
        panneauInterne.add(panneauOuestInterne, BorderLayout.WEST);
        panneauInterne.add(panneauCentreInterne, BorderLayout.CENTER);

        panneauSud.add(panneauInterne, BorderLayout.CENTER);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle("Confirmation de suppression");
        setLocationRelativeTo(panierFrame);
    }
}
