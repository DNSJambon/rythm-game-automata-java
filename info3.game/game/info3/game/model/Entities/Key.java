package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.Automaton;
import info3.game.controller.DirRelative;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public class Key extends Entity {

    boolean picked;

    public Key(IGrille g,Automaton a,int x, int y) {
        super(g);
        this.x = x;
        this.y = y;
        this.a = a;
        g.getCell(x, y).setEntity(this);
        picked=false;
    }
    @Override
    public cellType getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public char getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
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
            picked = true;
            return true;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paint'");
    }

    @Override
    public void tick(long elapsed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }
    
}
