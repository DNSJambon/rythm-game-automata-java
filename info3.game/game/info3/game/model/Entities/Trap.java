package info3.game.model.Entities;

import java.io.IOException;

import info3.game.controller.Automate;
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
        
         try {
            m_images = Grille.loadSprite("resources/trap.png", 1, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean do_hit(Entity e, DirRelative dir) { 
        switch (dir) {
            case Devant:
                g.getCell(this.x, this.y -1).GetEntity().get_hit(1);
                return true;
            case Derriere:
                g.getCell(this.x, this.y + 1).GetEntity().get_hit(1);
                return true;
            case Droite:
                g.getCell(this.x + 1, this.y).GetEntity().get_hit(1);
                return true;
            case Gauche:
                g.getCell(this.x - 1, this.y).GetEntity().get_hit(1);
                return true;
            case soi:
                g.getCell(this.x, this.y).GetEntity().get_hit(1);
                return true;
            default:   
                return false;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_pop'");
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
                image_index = (image_index + 1) % 2+1;
                animation_elapsed = 0;
            } 
        }
        
        
    }
    
}
