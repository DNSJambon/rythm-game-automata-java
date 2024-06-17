package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;


public class Slime extends Ennemi {

    public Slime(Grille g, int x, int y, Automate a) {
        super(g, x, y);
        etat_courant = a.getState();
        this.life=1;
        this.a = a;
        g.getCell(x, y).setEntity(this);
        direction = Direction.Nord;
        this.x = x;
        this.y = y;

        try {
            m_images = Grille.loadSprite("resources/faucheuse.png", 1, 4); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Slime;
    }

    @Override
    public char getCategory() {
        return Category.E;
    }

    public boolean eval_cell(Entity e, DirRelative dir, char type) {
        switch (dir) {
            case Devant:
                if (y == 0) {
                    return type == Category.O;
                }
                return g.getCell(x, y - 1).getCategory() == type;

            case Gauche:
                if (x == 0) {
                    return type == Category.O;
                }
                return g.getCell(x - 1, y).getCategory() == type;

            case Droite:
                if (x == g.getCols() - 1) {
                    return type == Category.O;
                }
                return g.getCell(x + 1, y).getCategory() == type;

            case Derriere:
                if (y == g.getRows() - 1) {
                    return type == Category.O;
                }
                return g.getCell(x, y + 1).getCategory() == type;

            default:
                return g.getCell(x, y).getCategory() == type;
        }
    }



    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        in_movement = nb_frame_move;
        switch (dir) {
            case Devant:
                this.y--;
                g.getCell(this.x, this.y + 1).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                direction = Direction.Nord;
                return true;
            case Derriere:
                this.y++;
                g.getCell(this.x, this.y - 1).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                direction = Direction.Sud;
                return true;
            default:
                return false;
        }
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
        if (dir == DirRelative.Devant || dir == DirRelative.Derriere) {
            direction = (direction == Direction.Nord) ? Direction.Sud : Direction.Nord;
            return true;
        }
        return false;
    }

   
}
