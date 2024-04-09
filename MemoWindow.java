import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class MemoWindow extends JFrame {
    private JButton[][] buttonGrid = new JButton[4][4]; // 4x4 grid de boutons
    private JButton but; // ancien bouton cliqué (à garder en mémoire)
    private JLabel label1; // affichage utilisateur (nombre d'actions)
    private Memo memo; // jeu du memo

    public MemoWindow() {
        super("Memo");

        this.memo = new Memo(); // initialisation du jeu du memo

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JPanel border = new JPanel(new BorderLayout()); // panel principal
        this.add(border);

        JPanel grid = new JPanel(new GridLayout(4,4)); // panel de la grille
        border.add(grid, BorderLayout.CENTER);

        for(int i = 0; i<4; i++){ // remplir la grille de boutons
            for(int j=0; j<4; j++){
                buttonGrid[i][j] = new JButton();
                buttonGrid[i][j].addActionListener(new ActionListener() { // event sur chaque boutons
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button clicked");

                        JButton button = (JButton) e.getSource();
                        int i = 0;
                        int j = 0;
                        for(int k = 0; k<4; k++){
                            for(int l=0; l<4; l++){
                                if(buttonGrid[k][l] == button){ // trouver les coordonnées du bouton cliqué
                                    i = k;
                                    j = l;
                                }
                            }
                        }
                        int nb = memo.getNbActions(); // nombre d'actions

                        if(nb%2==0){ // si nombre d'actions pair

                            Color test = memo.play(i,j,false);

                            if(test==Color.black){ // si déjà cliqué
                                System.out.println("Déjà cliqué");
                            }
                            else{  // si pas déjà cliqué
                                button.setBackground(test);
                                but = button;
                            }
                        }
                        if(nb%2==1){ // si nombre d'actions impair

                            Color test = memo.play(i,j,true);
                            JButton button2 = (JButton) e.getSource();
                            i = 0;
                            j = 0;
                            for(int k = 0; k<4; k++){
                                for(int l=0; l<4; l++){
                                    if(buttonGrid[k][l] == button2){ // trouver les coordonnées du bouton cliqué
                                        i = k;
                                        j = l;
                                    }
                                }
                            }
                            System.out.println(i);
                            System.out.println(j);

                            if (test == Color.black) {
                                System.out.println("Déjà cliqué");
                            } else {
                                button.setBackground(test); // Définir la couleur du bouton

                                if (!memo.getMemo_visibility(i, j)) { // si pas trouvé
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            button.setBackground(null); // Remettre la couleur du bouton à null après une seconde
                                            but.setBackground(null); // remettre les boutons à null
                                        }
                                    }, 1000); // programmer la tâche Timer pour réinitialiser la couleur après une seconde
                                }
                            }
                        }
                        if(memo.isOver()){ // si partie terminée
                            System.out.println("Partie terminée");
                            label1.setText("Partie terminée"+" Nombre d'actions : "+memo.getNbActions()); // affichage utilisateur
                        }
                    }
                });
                grid.add(buttonGrid[i][j], BorderLayout.CENTER); // ajouter les boutons à la grille
            }
        }

        label1 = new JLabel("Bienvenue dans le jeu du memo"); // affichage utilisateur du début
        border.add(label1, BorderLayout.SOUTH);
    }
}
