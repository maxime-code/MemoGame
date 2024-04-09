import java.awt.Color;
import java.util.Random;

public class Memo { // class memo
    private Color memo[][] = new Color[4][4]; // tableau de couleur
    private boolean memo_visibility[][] = new boolean[4][4]; // tableau de boolean
    private boolean b = false; // boolean
    private int nbActions = 0; // nombre d'actions
    private int oldI; // garder en mémoire ancienne coordonnée i (psk joue par pair)
    private int oldJ; // garder en mémoire ancienne coordonnée j (psk joue par pair)

    public boolean getMemo_visibility(int i,int j){
        return memo_visibility[i][j];
    }
    public Memo() { // constructeur
        int k = 0;
        Color[] palette = {Color.red, Color.blue, Color.green, Color.yellow, Color.cyan, Color.magenta, Color.pink, Color.orange,Color.red, Color.blue, Color.green, Color.yellow, Color.cyan, Color.magenta, Color.pink, Color.orange };

        Random rand = new Random(); // melanger la palette
        for (int i = 0; i < palette.length; i++) {
            int randomIndexToSwap = rand.nextInt(palette.length);
            Color temp = palette[randomIndexToSwap];
            palette[randomIndexToSwap] = palette[i];
            palette[i] = temp;
        }

        for (int i = 0; i < 4; i++) { // remplir le tableau de couleur
            for(int j = 0; j<4; j++){
                memo[i][j] = palette[k];
                k++;
            }
        }
        for (int i = 0; i < 4; i++) { // remplir le tableau de boolean false pour commencer
            for(int j = 0; j<4; j++){
                memo_visibility[i][j] = false;
            }
        }
    }
    public Color play(int i,int j,boolean b){ // jouer

        if(memo_visibility[i][j]){ // si déjà cliqué
            return Color.black;
        }

        if(isOver()){ // si partie terminée
            System.out.println("Partie terminée");
            System.out.println(getNbActions());
        }

        if(this.b){ // si on a déjà cliqué une fois

            memo_visibility[i][j] = true;
            nbActions++;

            if(memo[i][j] == memo[oldI][oldJ]){ // si trouvé
                System.out.println("Trouvé");
                memo_visibility[i][j] = true;
                memo_visibility[oldI][oldJ] = true;
            }

            else{ // si pas trouvé
                System.out.println("Pas trouvé");
                memo_visibility[i][j] = false;
                memo_visibility[oldI][oldJ] = false;
            }

            print(); // affichage console -> pour test
            this.b = false;
            return memo[i][j]; // retourne la couleur pour MemoWindow
        }
        else{ // si on a pas encore cliqué
            oldI = i;
            oldJ = j;
            memo_visibility[i][j] = true;
            nbActions++;
            print();
            this.b = true;
            return memo[i][j];
        }
    }
    public int getNbActions(){ // getter
        return nbActions;
    }
    public boolean isOver(){ // si partie terminée
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j<4; j++){
                if(!memo_visibility[i][j]){
                    return false;
                }
            }
        }
        return true; // si toutes les cases sont cliquées
    }
    public void print(){ // affichage console -> pour test

        for (int i = 0; i < 4; i++) {
            for(int j = 0; j<4; j++){

                if(memo_visibility[i][j]){
                    System.out.print("X");

                }
                else{
                    System.out.print("?");
                }
            }
            System.out.println();
        }
    }
}
