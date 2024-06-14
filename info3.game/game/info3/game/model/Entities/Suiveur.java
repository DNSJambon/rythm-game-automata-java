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

    BufferedImage[] m_images;
    int image_index = 0;
    Direction DirClosest;

    public Suiveur(Grille g, int x, int y,Automate a) {
        super(g, x, y);
        etat_courant = "0";
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
    public char getCategory() {
        return Category.E;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {

        direction = RelativeToAbsolute(dir);

        g.getCell(x, y).resetEntity();
        switch (direction) {
            case Nord:
                if (y == 0) {
                    return false;
                }
                y -= 1;
                break;
            case Est:
                if (x == g.getCols() - 1) {
                    return false;
                }
                x += 1;
                break;
            case Sud:
                if (y == g.getRows() - 1) {
                    return false;
                }
                y += 1;
                break;
            case Ouest:
                if (x == 0) {
                    return false;
                }
                x -= 1;
                break;
        }
        g.getCell(x, y).setEntity(this);
        in_movement = nb_frame_move;
        return true;
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
    public boolean eval_closest(Category c, Direction dir) {
        int xverif=this.g.getMainEntity().getX();
        int yverif=this.g.getMainEntity().getY();
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

    int in_movement = -1;
    int nb_frame_move = 8;
    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {

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


