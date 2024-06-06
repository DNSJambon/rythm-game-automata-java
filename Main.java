import controller.*;
import model.*;
import view.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Grille g = new Grille(10, 10);

        Transition[] T = new Transition[1];
        Pick p = new Pick();
        Cell c = new Cell(DirRelative.soi, cellType.Snake);
        T[0] = new Transition(p, c, 0, 0);
        Automaton a = new Automaton(0, T);
        Pomme item_pomme = new Pomme(g, a);

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

        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            view.createAndShowGUI(g);
        });

        // Use a Swing Timer to periodically update the automaton and repaint the view
        Timer timer = new Timer(800, e -> {
            a.step_A(item_pomme);
            a2.step_A(snake);
            // Trigger a repaint of the view
            SwingUtilities.invokeLater(() -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(new view(g));
                if (topFrame != null) {
                    topFrame.repaint();
                }
            });
        });
        timer.start();
    }
}

