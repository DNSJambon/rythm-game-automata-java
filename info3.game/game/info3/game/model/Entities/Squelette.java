package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;


public class Squelette extends Ennemi {

    public Squelette(Grille g, int x, int y, Automate a) {
        super(g, x, y);
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

