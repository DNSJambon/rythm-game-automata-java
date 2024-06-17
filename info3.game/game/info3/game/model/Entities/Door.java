package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.model.Category;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public class Door extends Entity{
    Key k;
    boolean opened;
    public Door(IGrille g,Automate a,int x,int y,Key k) {
        super(g);
        this.x = x;
        this.y =y;
        this.a = a;
        this.k=k;
        opened=false;
    }

    @Override
    public cellType getType() {
        return cellType.Door;
    }

    @Override
    public char getCategory() {
        return Category.D;
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
        if (k.picked()) {
            opened=true;
            return true;
        }
        return false;
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
