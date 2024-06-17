package info3.game.model.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.*;
import info3.game.model.Category;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public abstract class Entity {
    IGrille g;
    public String etat_courant;
    Automate a;
    public Direction direction;
    int life;
    int x, y;

    BufferedImage[] m_images;
    int image_index = 0;

    public Entity(IGrille g) {
        this.life=1;
        this.g = g;
        g.addEntity(this);
        direction = Direction.Est;
    }

    public abstract cellType getType();

    public abstract char getCategory();


    public void step(BufferAction buff) {
        a.step_A(this, buff);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IGrille getGrille() {
        return g;
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    

    public Direction RelativeToAbsolute(DirRelative d){
        Direction[] dirs = { Direction.Nord, Direction.Est, Direction.Sud, Direction.Ouest };
        int i = 0;
        while (dirs[i] != this.direction)
            i++;

        switch (d) {
            case Droite:
                i = (i + 1) % 4;
                break;
            case Gauche:
                i = (i + 3) % 4;
                break;
            case Derriere:
                i = (i + 2) % 4;
                break;
            default:
                break;
        }
        
        return dirs[i];
    }
    
    
    //abstract boolean eval(...);
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

    public boolean eval_dir(DirRelative dir) {
        return this.direction == RelativeToAbsolute(dir);
    }

    public boolean eval_closest(char c, Direction dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval_closest'");
    }

    public boolean eval_got(Entity e,char cat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval_got'");
    }

    public boolean eval_got_power(Entity e,int i) {
        return e.getLife()>i;
    }

    public abstract boolean do_egg(Entity e);
    public abstract boolean do_pick(Entity e);
    public abstract boolean do_pop(Entity e);
    public abstract boolean do_wizz(Entity e);
    public abstract boolean do_turn(Entity e, DirRelative dir);

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
    
    
    public boolean do_die(Entity e){
        g.getCell(x, y).resetEntity();
        g.removeEntity(this);
        return true;
    }
    
    public void get_hit(int damage){
        this.life-=damage;
    }


    public boolean do_wait(Entity e) {
        return true;
    }
    
    
    //variables pour l'animation de deplacement
    int in_movement = -1;
    int nb_frame_move = 10;
    float[] slide = {1.0f, 0.7f, 0.36f, 0.22f, 0.13f, 0.07f, 0.05f, 0.03f, 0.015f, 0.007f, 0.0f};
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        if (in_movement != -1) {
            if (direction == Direction.Nord) {
                y += height * slide[nb_frame_move - in_movement];
            } else if (direction == Direction.Est) {
                x -= width * slide[nb_frame_move - in_movement];
            } else if (direction == Direction.Sud) {
                y -= height * slide[nb_frame_move - in_movement];
            } else if (direction == Direction.Ouest) {
                x += width * slide[nb_frame_move - in_movement];
            }
            in_movement--;
        }
            

        graphics.drawImage(m_images[image_index], x, y, width, height, null);

    }

    
    int animation_elapsed = 0;
    public void tick(long elapsed) {
        animation_elapsed += elapsed;
        if (animation_elapsed > 1000/((float)4*100/60)) { //TODO: remplacer 100 par bpm
            image_index = (image_index + 1) % 4;
            animation_elapsed = 0;
        }
        
    }

}
