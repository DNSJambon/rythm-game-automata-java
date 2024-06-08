package info3.game;

import info3.game.model.*;
import info3.game.model.Entities.Pomme;
import info3.game.model.Entities.Snake;
import info3.game.controller.*;
import info3.game.controller.Actions.Egg;
import info3.game.controller.Actions.Move;
import info3.game.controller.Actions.Pick;
import info3.game.controller.Actions.Turn;
import info3.game.controller.Conditions.Cell;
import info3.game.controller.Conditions.Random;
import info3.game.controller.Conditions.True;
import info3.game.view.*;

import java.io.IOException;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Grille g = new Grille(6, 6, null);

        Transition[] T = new Transition[1];
        // Move p = new Move();
        Pick p = new Pick();
        Cell c = new Cell(DirRelative.soi, cellType.Snake);
        T[0] = new Transition(p, c, 0, 0);
        Automaton a = new Automaton(0, T);
        Pomme item_pomme = new Pomme(g, a);
        p.e_or = item_pomme;

        // Snake:
        Transition[] T2 = new Transition[5];
        Cell cond_apple = new Cell(DirRelative.Devant, cellType.Apple);
        Cell cond_obsatcle = new Cell(DirRelative.Devant, cellType.Obstacle);
        True cond_true = new True();
        Random r = new Random(20);

        Egg queue = new Egg();
        Turn gauche = new Turn(DirRelative.Gauche);
        Turn droite = new Turn(DirRelative.Droite);
        Move m = new Move(DirRelative.Devant);

        T2[3] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[3] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[1] = new Transition(droite, r, 0, 0);
        T2[2] = new Transition(m, cond_true, 0, 0);
        T2[0] = new Transition(m, cond_apple, 0, 1);
        T2[4] = new Transition(queue, cond_true, 1, 0);

        Automaton a2 = new Automaton(0, T2);
        Snake snake = new Snake(a2, g);
        m.e_or = snake;
        gauche.e_or = snake;
        droite.e_or = snake;
        queue.e_or = snake;


        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            view.createAndShowGUI(g);
        });

        BufferAction buffer = new BufferAction(3);
        // Use a Swing Timer to periodically update the automaton
        Timer timer = new Timer(250, e -> {
            item_pomme.step(buffer);
            snake.step(buffer);
            
            buffer.resolve();
            System.out.println(snake.size);
        });
        timer.start();
    }
}

