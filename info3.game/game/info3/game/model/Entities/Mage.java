package info3.game.model.Entities;

import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
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
                        this.direction = Direction.Est;
                        
                        return true;
                    case Sud:
                        this.direction = Direction.Ouest;
                        
                        return true;
                    case Est:
                        this.direction = Direction.Sud;
                        
                        return true;
                    case Ouest:
                        this.direction = Direction.Nord;
                       
                        return true;
                    default:
                        return false;
                }

            case Gauche:
                switch (direction) {
                    case Nord:
                        direction = Direction.Ouest;
                        
                        return true;
                    case Sud:
                        direction = Direction.Est;
                       
                        return true;
                    case Est:
                        direction = Direction.Nord;
                        
                        return true;
                    case Ouest:
                        direction = Direction.Sud;
                        
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        switch (direction) {
            case Nord:
                this.y--;
                g.getCell(this.x, this.y + 1).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                return true;
            case Sud:
                this.y++;
                g.getCell(this.x, this.y - 1).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                return true;
            case Est:
                this.x++;
                g.getCell(this.x - 1, this.y).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                return true;
            case Ouest:
                this.x--;
                g.getCell(this.x + 1, this.y).resetEntity();
                g.getCell(this.x, this.y).setEntity(this);
                return true;
            default:
                return false;

            
        }
    }


    
}
