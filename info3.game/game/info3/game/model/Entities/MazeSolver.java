package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;

public class MazeSolver extends Ennemi {

    public MazeSolver(Grille grille, int col, int row, Automate a) {
        super(grille,col,row);
        etat_courant = a.getState();
        life = 1;
        direction = Direction.Est;
        this.x = col;
        this.y = row;
        grille.getCell(col, row).setEntity(this);
        this.a = a;

        try {
            m_images = Grille.loadSprite("resources/slime.png", 1, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.MazeSolver;
    }

    @Override
    public char getCategory() {
        return Category.T;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        //on change la direction du MazeSolver
        direction = RelativeToAbsolute(dir);

        //on change sa position
        g.getCell(x, y).resetEntity();
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
        in_movement = nb_frame_move;
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
    public boolean do_pop(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_wizz(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }
    

    
    
}
