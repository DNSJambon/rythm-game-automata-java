package info3.game.model.Entities;

import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
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
    public char getCategory() {
        return Category.V;
    }

   

    @Override
    public boolean do_pop(Entity e) {
        activated=true;
        return true;
    }

    @Override
    public boolean do_hit(Entity e, DirRelative dir) {
        this.activated=true;
        return super.do_hit(e,dir);
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


    @Override
    public cellType getType() {
        return cellType.Trap;
    }
        
}

    
