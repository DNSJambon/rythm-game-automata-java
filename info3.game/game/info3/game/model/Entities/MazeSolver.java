package info3.game.model.Entities;


import java.awt.Graphics;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;

public class MazeSolver extends Entity {
    
    public MazeSolver(Grille g, int x, int y) {
        super(g);
        etat_courant = 0;
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);


        //Automate par defaut (technique de la main droite)
        Transition[] T = new Transition[6];

        Cell obstacle_devant = new Cell(DirRelative.Devant, cellType.Obstacle);
        Cell obstacle_droite = new Cell(DirRelative.Droite, cellType.Obstacle);
        Cell obstacle_gauche = new Cell(DirRelative.Gauche, cellType.Obstacle);
        Cell droite_vide = new Cell(DirRelative.Droite, cellType.Vide);
        True t = new True();

        Move devant = new Move(DirRelative.Devant);
        Move droite = new Move(DirRelative.Droite);
        Move gauche = new Move(DirRelative.Gauche);
        Move demi_tour = new Move(DirRelative.Derriere);

        T[0] = new Transition(droite, droite_vide, 0, 0);
        T[1] = new Transition(demi_tour, new Et(obstacle_devant, obstacle_droite, obstacle_gauche), 0, 0);
        T[2] = new Transition(gauche, new Et(obstacle_devant, obstacle_droite), 0, 0);
        T[3] = new Transition(droite, new Et(obstacle_devant, obstacle_gauche), 0, 0);
        T[4] = new Transition(gauche, obstacle_devant, 0, 0);
        T[5] = new Transition(devant, t, 0, 0);

        Automaton a = new Automaton(0, T);

        this.a = a;
        droite.e_or = this;
        gauche.e_or = this;
        devant.e_or = this;
        demi_tour.e_or = this;
        
    }

    @Override
    public cellType getType() {
        return cellType.MazeSolver;
    }


    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        //on change la direction du MazeSolver
        direction = RelativeToAbsolute(dir);

        //on change sa position
        g.getCell(x, y).reset();
        switch (direction) {
            case Nord:
                if (y == 0) {
                    return false;
                }
                y -= 1;
                break;
            case Est:
                if (x == g.getCols() - 1) {
                    return false;
                }
                x += 1;
                break;
            case Sud:
                if (y == g.getRows() - 1) {
                    return false;
                }
                y += 1;
                break;
            case Ouest:
                if (x == 0) {
                    return false;
                }
                x -= 1;
                break;
        }
        g.getCell(x, y).setEntity(this);
        return true;

    }

    @Override
    public boolean do_egg(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_egg'");
    }

    @Override
    public boolean do_pick(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_pick'");
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }

    @Override
    public boolean do_wait(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_wait'");
    }

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(g.getImage(362), x, y, width, height, null);
    }
    
}
