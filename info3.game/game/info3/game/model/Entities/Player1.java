package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;

public class Player1 extends Entity{

    int life;
    
    

    public Player1(IGrille g, int x, int y, Automate a) {
        super(g);
        life = 6;
        etat_courant = a.getState();
        this.a = a;
        g.getCell(x, y).setEntity(this);
        direction = Direction.Nord;
        this.x = x;
        this.y = y;

         try {
            m_images = Grille.loadSprite("resources/magesquelette.png", 1, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void takeDamage(int damage) {
        life-=damage;
    }

    @Override
    public cellType getType() {
        return cellType.Player1;
    }

     @Override
    public char getCategory() {
        return Category.H;
    }

    @Override
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
                return g.getCell(x-1, y).getCategory() ==type;
        
            
            case Droite:
                if (x == g.getCols()-1) {
                    return type == Category.O;
                }
                return g.getCell(x+1, y).getCategory() ==type;

            case Derriere:
                if (y == g.getRows()-1) {
                    return type == Category.O;
                }
                return g.getCell(x, y+1).getCategory() ==type;

            default:

                return g.getCell(x, y).getCategory() ==type;
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
            case Droite:
                this.x++;
                g.getCell(this.x - 1, this.y).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                direction = Direction.Est;
                return true;
            case Gauche:
                this.x--;
                g.getCell(this.x + 1, this.y).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                direction = Direction.Ouest;
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

    public void gethit(int damage){
        this.life-=damage;
    }

    @Override
    public boolean do_hit(Entity e, DirRelative dir) {
        if (eval_cell(e, dir, 'E')){
        
            switch (dir) {

            case Devant:
                g.getCell(this.x, this.y + 1).GetEntity().get_hit(1);
                return true;
            case Derriere:
                g.getCell(this.x, this.y + 1).GetEntity().get_hit(1);
                return true;
            case Droite:
                g.getCell(this.x, this.y + 1).GetEntity().get_hit(1);
                return true;
            case Gauche:
                g.getCell(this.x, this.y + 1).GetEntity().get_hit(1);
                return true;
            default:   
                return false;
        }
    }
    return false;
    }
}
