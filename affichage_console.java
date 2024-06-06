
import view.display;

import model.*;
import controller.*;

public class affichage_console {
    
    public static void main(String[] args) {
        Grille g = new Grille(5, 5);

        
        Transition[] T = new Transition[1];
        //Move p = new Move();
        Pick p = new Pick();
        Cell c = new Cell(DirRelative.soi, cellType.Snake);
        T[0] = new Transition(p, c, 0, 0);
        Automaton a = new Automaton(0, T);
        Pomme item_pomme = new Pomme(g, a);
        p.e_or=item_pomme;

        //Snake:
        Transition[] T2 = new Transition[5];
        Cell cond_apple= new Cell(DirRelative.Devant,cellType.Apple);
        Cell cond_obsatcle = new Cell(DirRelative.Devant, cellType.Obstacle);
        True cond_true = new True();
        Random r = new Random(20);
        
        Egg queue= new Egg();
        Turn gauche = new Turn(DirRelative.Gauche);
        Turn droite = new Turn(DirRelative.Droite);
        Move m = new Move();

        T2[3] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[1] = new Transition(droite, r, 0, 0);
        T2[2] = new Transition(m, cond_true, 0, 0);
        T2[0]= new Transition(m, cond_apple, 0, 1);
        T2[4]=new Transition(queue, cond_true, 1, 0);
        

        Automaton a2 = new Automaton(0, T2);
        Snake snake = new Snake(a2, g);
        m.e_or=snake;
        gauche.e_or=snake;
        droite.e_or=snake;

        BufferAction buffer = new BufferAction(2);
        while (true) {
            item_pomme.step(buffer);
            snake.step(buffer);
            buffer.resolve();
            display d = new display(g);
            d.afficher_grille();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}
