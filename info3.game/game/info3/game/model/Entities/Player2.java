package info3.game.model.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.cellType;

public class Player2 extends Entity {

    int cooldown_egg = 0;
    int cooldown_pop = 0;
    int cooldown_wizz = 0;

    public Player2(Grille g, int x, int y, Automate a) {
        super(g);
        etat_courant = a.getState();
        this.a = a;
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setP2(this);

        try{
            m_images = Grille.loadSprite("resources/cursor.png", 1, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Player2;
    }

    @Override
    public char getCategory() {
        return Category.A;
    }

    @Override
    public boolean eval_cell(Entity e, DirRelative dir, char type) {
        switch (dir) {
            case soi:
                return g.getCell(x, y).getCategory() == type;

            case Devant:
                if (y == 0) {
                    return false;
                }
                return g.getCell(x, y - 1).getCategory() == type;
            
                case Gauche:
                if (x == 0) {
                    return false;
                }
                return g.getCell(x-1, y).getCategory() ==type;
        
            
            case Droite:
                if (x == g.getCols()-1) {
                    return false;
                }
                return g.getCell(x+1, y).getCategory() ==type;

            case Derriere:
                if (y == g.getRows()-1) {
                    return false;
                }
                return g.getCell(x, y+1).getCategory() ==type;

            default:

                return g.getCell(x, y).getCategory() ==type;
        }
    }

    public boolean do_move(Entity e, DirRelative dir) {
        in_movement = nb_frame_move;
        if (cooldown_egg != 0)
            cooldown_egg--;
        if (cooldown_pop != 0)
            cooldown_pop--;
        if (cooldown_wizz != 0)
            cooldown_wizz--;
        switch (dir) {
            case Devant:
                if (y == 0) {
                    return false;
                }
                this.y--;
                g.getCell(this.x,this.y+1).resetP2();
                g.getCell(this.x,this.y).setP2(this);
                direction = Direction.Nord;
                return true;
            
            case Derriere:
                if (y == g.getRows()-1) {
                    return false;
                }
                this.y++;
                g.getCell(this.x,this.y-1).resetP2();
                g.getCell(this.x,this.y).setP2(this);
                direction = Direction.Sud;
                return true;
            
            case Droite:
                if (x == g.getCols()-1) {
                    return false;
                }
                this.x++;
                g.getCell(this.x-1,this.y).resetP2();
                g.getCell(this.x,this.y).setP2(this);
                direction = Direction.Est;
                return true;
            
            case Gauche:
                if (x == 0) {
                    return false;
                }
                this.x--;
                g.getCell(this.x+1,this.y).resetP2();
                g.getCell(this.x,this.y).setP2(this);
                direction = Direction.Ouest;
                return true;
            
            default:
                return false;
        }
    }


    @Override
    public boolean do_egg(Entity e) {
        if (cooldown_egg == 0) {
            new MazeSolver((Grille) g, x, y, ((Grille) g).automates.get("MazeSolver"));
            cooldown_egg = 3;
        }
        else
            cooldown_egg --;
        return true;
    }

    public int getCooldown_egg() {
        return cooldown_egg;
    }
   

    @Override
    public boolean do_pop(Entity e) {
        return false;
    }

    @Override
    public boolean do_wizz(Entity e) {
        return false;
    }

    @Override
    public boolean do_wait(Entity e) {
        if (cooldown_egg != 0)
            cooldown_egg--;
        if (cooldown_pop != 0)
            cooldown_pop--;
        if (cooldown_wizz != 0)
            cooldown_wizz--;
        return true;
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }

    @Override
    public boolean do_pick(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_pick'");
    }

    @Override
    public boolean do_hit(Entity e, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_hit'");
    }

   
    

}
