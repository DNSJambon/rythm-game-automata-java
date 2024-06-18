package info3.game.model.Entities;

import java.awt.Graphics;
import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public class Projectile extends Ennemi{

    public Projectile(IGrille g, int x, int y,Direction dir,Automate a) {
        super(g, x, y);
        this.a=a;
        etat_courant = a.getState();
        
        direction = dir;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);
        try {
            m_images = Grille.loadSprite("resources/sourichauve.png", 1, 4);
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Projectile;
    }

    @Override
    public char getCategory() {
        return Category.T;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
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
