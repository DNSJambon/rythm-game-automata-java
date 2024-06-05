
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



        while (true) {
            a.step_A(item_pomme);
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
