package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;

public class Sourischauve extends Ennemi {

    public Sourischauve(Grille grille, int col, int row, Automate a) {
        super(grille,col,row);
        etat_courant = a.getState();
        life = 2;
        direction = Direction.Est;
        this.x = col;
        this.y = row;
        grille.getCell(col, row).setEntity(this);
        this.a = a;

        try {
            m_images = Grille.loadSprite("resources/sourichauve.png", 2, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Sourischauve;
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
