package info3.game.model.Entities;


import java.io.IOException;

import info3.game.controller.*;
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
            m_images = Grille.loadSprite("resources/squelette.png", 2, 4); 
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





    
}

