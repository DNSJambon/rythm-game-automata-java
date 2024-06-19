package info3.game.model.Entities;

import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.BufferAction;
import info3.game.controller.DirRelative;
import info3.game.controller.Conditions.True;
import info3.game.model.Grille;
import info3.game.model.IGrille;
import info3.game.model.cellType;
import info3.game.model.Category;


public class Trap extends Entity{

    boolean activated;

    public Trap(IGrille g, int x, int y,Automate a) {
        super(g);
        this.a=a;
        this.g.getCell(x, y).setTrap(this);
        this.activated=false;
        this.x=x;
        this.y=y;
        this.etat_courant = a.getState();
        this.image_index=0;
        
         try {
            m_images = Grille.loadSprite("resources/trap.png", 1, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public char getCategory() {
        return Category.V;
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
        activated=true;
        return true;
    }

    @Override
    public boolean do_wizz(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_wizz'");
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }
    
    @Override
    public void tick(long elapsed) {
        if (activated) {
            animation_elapsed += elapsed;
            if (animation_elapsed > 200 ) {
                image_index = ((image_index) % 2)+1;
                animation_elapsed = 0;
            } 
        }
    }
        
}

    
