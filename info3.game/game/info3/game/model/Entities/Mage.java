package info3.game.model.Entities;

import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Grille;
import info3.game.model.IGrille;

public class Mage extends Ennemi {
    Automate projectile;
    public Mage(IGrille g, int x, int y, Automate a,Automate Proj) {
        super(g, x, y);
        projectile=Proj;
        life = 1;
        this.a = a;
        etat_courant = a.getState();
        
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

    @Override
    public boolean do_egg(Entity e) {
        Projectile p;
        switch (direction) {
            case Nord:
                p = new Projectile(g, x, y-1, direction,projectile );
                return true;
            case Sud:
                p = new Projectile(g, x, y+1, direction,projectile);
                return true;
            case Est:   
                p = new Projectile(g, x+1, y, direction,projectile);
                return true;
            case Ouest:
                p = new Projectile(g, x-1, y, direction,projectile);
                return true;
            default:
                return false;
        }
        
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
    public boolean do_turn(Entity e, DirRelative d) {
        switch (d) {
            case Droite:
                switch (direction) {
                    case Nord:
                        direction = Direction.Est;
                        this.do_move(e, d);
                        return true;
                    case Sud:
                        direction = Direction.Ouest;
                        this.do_move(e, d);;
                        return true;
                    case Est:
                        direction = Direction.Sud;
                        this.do_move(e, d);
                        return true;
                    case Ouest:
                        direction = Direction.Nord;
                        this.do_move(e, d);
                        return true;
                    default:
                        return false;
                }

            case Gauche:
                switch (direction) {
                    case Nord:
                        direction = Direction.Ouest;
                        this.do_move(e, d);
                        return true;
                    case Sud:
                        direction = Direction.Est;
                        this.do_move(e, d);
                        return true;
                    case Est:
                        direction = Direction.Nord;
                        this.do_move(e, d);
                        return true;
                    case Ouest:
                        direction = Direction.Sud;
                        this.do_move(e, d);
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }


    
}
