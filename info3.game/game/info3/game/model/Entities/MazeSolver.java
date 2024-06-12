package info3.game.model.Entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Actions.*;
import info3.game.model.*;

public class MazeSolver extends Entity {

    BufferedImage[] m_images;
    int image_index = 0;

    
    
    public MazeSolver(Grille g, int x, int y) {
        super(g);
        etat_courant = 0;
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);

        try {
            m_images = Grille.loadSprite("resources/slime.png", 1, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Automate par defaut (technique de la main droite)
        Transition[] T = new Transition[6];

        Cell obstacle_devant = new Cell(DirRelative.Devant, Category.O);
        Cell obstacle_droite = new Cell(DirRelative.Droite, Category.O);
        Cell obstacle_gauche = new Cell(DirRelative.Gauche, Category.O);
        Cell droite_vide = new Cell(DirRelative.Droite, Category.V);
        True t = new True();
        //Key devan = new Key('z');
        //Key derrière = new Key('s');
        //Key droit = new Key('d');
        //Key gauch = new Key('q');

        Move devant = new Move(DirRelative.Devant);
        Move droite = new Move(DirRelative.Droite);
        Move gauche = new Move(DirRelative.Gauche);
        Move demi_tour = new Move(DirRelative.Derriere);

        T[0] = new Transition(droite, droite_vide, 0, 0);
        //T[0] = new Transition(droite, droit, 0, 0);
        T[1] = new Transition(demi_tour, new Et(obstacle_devant, obstacle_droite, obstacle_gauche), 0, 0);
        T[2] = new Transition(gauche, new Et(obstacle_devant, obstacle_droite), 0, 0);
        //T[1] = new Transition(gauche, gauch, 0, 0);
        T[3] = new Transition(droite, new Et(obstacle_devant, obstacle_gauche), 0, 0);
        T[4] = new Transition(gauche, obstacle_devant, 0, 0);
        T[5] = new Transition(devant, t, 0, 0);
        //T[2] = new Transition(devant, devan, 0, 0);
        //T[3] = new Transition(demi_tour, derrière, 0, 0);
        

        Automaton a = new Automaton(0, T);

        this.a = a;
        droite.e_or = this;
        gauche.e_or = this;
        devant.e_or = this;
        demi_tour.e_or = this;
        
    }

    @Override
    public cellType getType() {
        return cellType.MazeSolver;
    }

    @Override
    public char getCategory() {
        return Category.T;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        //on change la direction du MazeSolver
        direction = RelativeToAbsolute(dir);

        //on change sa position
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
    

    //variables pour l'animation de deplacement
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
