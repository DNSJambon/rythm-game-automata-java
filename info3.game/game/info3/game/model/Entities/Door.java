package info3.game.model.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public class Door extends Entity {
    Key k;
    boolean opened;
    BufferedImage porte_ferme;
    BufferedImage porte_ouverte;

    public Door(IGrille g, Automate a, int x, int y, Key k) {
        super(g);
        this.x = x;
        this.y = y;
        this.a = a;
        this.k = k;
        this.etat_courant=a.getState();
        g.getCell(x, y).setTrap(this);
        opened = false;
        try {
            BufferedImage[] images = Grille.loadSprite("resources/door.png", 1, 2);
            if (images != null && images.length == 2) {
                porte_ferme = images[1];  
                porte_ouverte = images[0];  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Door;
    }

    @Override
    public char getCategory() {
        return Category.V;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_egg(Entity e) {
       ((Grille)g).game_over=1;
       return true;
    }

    @Override
    public boolean do_pick(Entity e) {
        open(); 
        return opened;
    }

    @Override
    public boolean eval_closest(char c, Direction dir) {
        return this.k.isPicked();
                
        
    }

    @Override
    public boolean do_pop(Entity e) {
        throw new UnsupportedOperationException("Unimplemented method 'do_pop'");
    }

  
    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }

    @Override
    public boolean do_hit(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_hit'");
    }

    public void open() {
        if (k.isPicked()) {
            opened = true;
        }
    }

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        if (opened) {
            if (porte_ouverte != null) {
                graphics.drawImage(porte_ouverte, x, y, width, height, null);
            }
        } else {
            if (porte_ferme != null) {
                graphics.drawImage(porte_ferme, x, y, width, height, null);
            }
        }
    }

    @Override
    public boolean do_wizz(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_wizz'");
    }

    
}
