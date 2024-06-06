import controller.*;
import model.*;
import view.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Grille g = new Grille(10, 10);

        //Obstacle
        Transition[] Tobs = new Transition[1];

        Cell c2_obs = new Cell(DirRelative.Derriere, cellType.Snake);

        Egg e_obs = new Egg();

        Tobs[0]=new Transition(e_obs,c2_obs, 0, 0);

        Automaton a_obs = new Automaton(0, Tobs);
        Obstacle obs = new Obstacle(g, a_obs, 5, 5);

        //Pomme:
        Transition[] T = new Transition[1];

        Cell c = new Cell(DirRelative.soi, cellType.Snake); 

        Pick p = new Pick();

        T[0] = new Transition(p, c, 0, 0);

        Automaton a = new Automaton(0, T);
        Pomme item_pomme = new Pomme(g, a);

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
            a.step_A(item_pomme);
            a2.step_A(snake);
            a_obs.step_A(obs);
        });
        timer.start();
    }
}

