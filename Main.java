import controller.*;
import model.*;
import view.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Grille g = new Grille(10, 10);

        //Pomme:
        Transition[] T = new Transition[3];

        Cell c1 = new Cell(DirRelative.Devant, cellType.Vide); 

        Egg e1 = new Egg();

        Cell c2 = new Cell(DirRelative.Devant, cellType.Obstacle);

        Turn t2 = new Turn(DirRelative.Derriere);

        Cell c3 = new Cell(DirRelative.Derriere, cellType.Obstacle);

        Move m3 = new Move();

        T[0] = new Transition(m3, c3, 0, 0);
        T[1] = new Transition(t2, c2, 0, 0);
        T[2] = new Transition(e1, c1, 0, 0);

        Automaton a = new Automaton(0, T);
        Obstacle obs = new Obstacle(g, a,2,3);

        //Snake:
        Transition[] T2 = new Transition[4];

        Cell cond_obsatcle = new Cell(DirRelative.Devant, cellType.Obstacle);
        True cond_true = new True();
        Random r = new Random(10);

        Turn gauche = new Turn(DirRelative.Gauche);
        Turn droite = new Turn(DirRelative.Droite);
        Move m = new Move();

        T2[0] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[1] = new Transition(droite, r, 0, 0);
        T2[2] = new Transition(gauche, r, 0, 0);
        T2[3] = new Transition(m, cond_true, 0, 0);  

        Automaton a2 = new Automaton(0, T2);
        Snake snake = new Snake(a2, g);


        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            view.createAndShowGUI(g);
        });

 
        // Use a Swing Timer to periodically update the automaton
        Timer timer = new Timer(250, e -> {
            a.step_A(obs);
            a2.step_A(snake);
        });
        timer.start();
    }
}

