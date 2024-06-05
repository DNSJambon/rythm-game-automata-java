
import view.display;

import model.*;
import controller.*;

public class test_affichage {
    
    public static void main(String[] args) {
        Grille g = new Grille(10, 10);


        Transition[] T = new Transition[1];
        //Move p = new Move();
        Pick p = new Pick();
        Cell c = new Cell(DirRelative.soi, cellType.Apple);
        T[0] = new Transition(p, c, 0, 0);
        Automaton a = new Automaton(0, T);
        Pomme item_pomme = new Pomme(g, a);

        //
        Transition[] T2 = new Transition[2];
        Cell cond_obsatcle = new Cell(DirRelative.Devant, cellType.Obstacle);
        True cond_true = new True();
        Turn gauche = new Turn(DirRelative.Gauche);
        Turn droite = new Turn(DirRelative.Droite);
        Move m = new Move();
        T2[0] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[1] = new Transition(m, cond_true, 0, 0);
        Automaton a2 = new Automaton(0, T2);
        Snake snake = new Snake(a2, g);





        while (true) {
            a.step_A(item_pomme);
            a2.step_A(snake);
            display d = new display(g);
            d.afficher_grille();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}
