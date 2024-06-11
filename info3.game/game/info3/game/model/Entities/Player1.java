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
    BufferedImage[] m_images;
    int image_index = 0;
    //variables pour l'animation de deplacement
    int in_movement = -1;
    int nb_frame_move = 6;

    public Player1(IGrille g) {
        super(g);
        life = 3;
        g.getCell(1, 1).setEntity(this);
        direction = Direction.Nord;
        x = 1;
        y = 1;
        //Automate par defaut
        Transition[] T = new Transition[2];
         try {
            m_images = Grille.loadSprite("resources/squelette.png", 1, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }

      
        Cell droite_vide = new Cell(DirRelative.Droite, Category.V);
        Cell gauche_vide = new Cell(DirRelative.Gauche, Category.V);

        Move devant = new Move(DirRelative.Devant);
        Move droite = new Move(DirRelative.Droite);
        Move gauche = new Move(DirRelative.Gauche);
        

        T[0] = new Transition(droite, droite_vide, 0, 0);
        T[1] = new Transition(gauche, gauche_vide , 0, 0);
        
        Automaton a = new Automaton(0, T);

        this.a = a;
        droite.e_or = this;
        gauche.e_or = this;
        devant.e_or = this;
    }
    
    public Player1(Automaton a,Grille g) {
        super(g);
        super.a = a;
        life = 3;
        g.getCell(1, 1).setEntity(this);
        direction = Direction.Nord;
        x = 1;
        y = 1;

    }

    @Override
    public cellType getType() {
        return cellType.Player1;
    }

     @Override
    public char getCategory() {
        return Category.P;
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
        /* 
        if (in_movement != -1) {
            if (direction == Direction.Nord) {
                y += (height * in_movement) / nb_frame_move;
            } else if (direction == Direction.Est) {
                x -= (width * in_movement) / nb_frame_move;
            } else if (direction == Direction.Sud) {
                y -= (height * in_movement) / nb_frame_move;
            } else if (direction == Direction.Ouest) {
                x += (width * in_movement) / nb_frame_move;
            }
            in_movement--;
        }
            */

        graphics.drawImage(m_images[image_index], x, y, width, height, null);

    }

    
    int animation_elapsed = 0;
    @Override
    public void tick(long elapsed) {
        animation_elapsed += elapsed;
        if (animation_elapsed > 200) {
            image_index = (image_index + 1) % 4;
            animation_elapsed = 0;
        }
        
    }

   

}
