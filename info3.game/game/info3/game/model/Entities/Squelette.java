package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;


public class Squelette extends Entity {
    int life;


    public Squelette(IGrille g, int x, int y, Automate a) {
        super(g);
        etat_courant = a.getState();
        life = 1;
        this.a = a;
        g.getCell(x, y).setEntity(this);
        direction = Direction.Ouest; //Start by moving left
        this.x = x;
        this.y = y;

        try {
            m_images = Grille.loadSprite("resources/squelette.png", 1, 4); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Squelette;
    }

    @Override
    public char getCategory() {
        return Category.E; //Ennemy
    }

    public boolean eval_cell(Entity e, DirRelative dir, char type) {
        switch (dir) {
            case Devant:
                return g.getCell(x, y - 1).getCategory() == type;

            case Gauche:
                return g.getCell(x - 1, y).getCategory() == type;

            case Droite:
                return g.getCell(x + 1, y).getCategory() == type;

            case Derriere:
                return g.getCell(x, y + 1).getCategory() == type;

            default:
                return false;
        }
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        in_movement = nb_frame_move;
        switch (dir) {
            case Gauche:
                this.x--;
                g.getCell(this.x + 1, this.y).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                direction = Direction.Ouest;
                return true;
            case Droite:
                this.x++;
                g.getCell(this.x - 1, this.y).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                direction = Direction.Est;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        switch (dir) {
            case Gauche:
                direction = Direction.Est;
                break;
            case Droite:
                direction = Direction.Ouest;
                break;
            default:
                return false;
        }
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
        throw new UnsupportedOperationException("Unimplemented method 'do_pop'");
    }

    @Override
    public boolean do_wizz(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_wizz'");
    }

    
}

