package info3.game.model.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.cellType;

public class Suiveur extends Ennemi{

    Direction DirClosest;

    public Suiveur(Grille g, int x, int y,Automate a) {
        super(g, x, y);
        etat_courant = a.getState();
        life = 1;
        direction = Direction.Est;;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);
        this.a=a;

        try {
            m_images = Grille.loadSprite("resources/faucheuse.png", 1, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Suiveur;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        in_movement = nb_frame_move;
        switch (dir) {
            
            case Devant:
                this.y--;
                g.getCell(this.x,this.y+1).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Nord;
                return true;
            case Derriere:
                this.y++;
                g.getCell(this.x,this.y-1).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Sud;
                return true;
            case Droite:
                this.x++;
                g.getCell(this.x-1,this.y).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Est;
                return true;
            case Gauche:
                this.x--;
                g.getCell(this.x+1,this.y).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
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

    @Override
    public boolean eval_closest(char c, Direction dir) {
        int xverif=(this.g.getMainEntity().getX())-this.x;
        int yverif=this.g.getMainEntity().getY()-this.y;
        if (xverif==0) {
            xverif=g.getCols()+1;
        }
        else if (yverif==0){
            yverif=g.getRows()+1;
        }
        if (Math.abs(xverif)<=Math.abs(yverif)) {
            if (xverif>0) {
                DirClosest=Direction.Est;
            } else {
                DirClosest=Direction.Ouest;
            }
        }
        else if (Math.abs(xverif)>Math.abs(yverif)){
            if (yverif>0) {
                DirClosest=Direction.Sud;
            } else {
                DirClosest=Direction.Nord;
            }
        }
        return DirClosest == dir ;
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
            default:   
                return false;
        }
    }

    
}


