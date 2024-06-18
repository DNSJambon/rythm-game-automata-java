package info3.game.model.Entities;

import java.awt.Graphics;
import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public class Projectile extends Ennemi{

    public Projectile(IGrille g, int x, int y,Direction dir,Automate a) {
        super(g, x, y);
        this.a=a;
        etat_courant = a.getState();
        
        direction = dir;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);
        try {
            m_images = Grille.loadSprite("resources/projectile.png", 1, 4);
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Projectile;
    }

    @Override
    public char getCategory() {
        return Category.T;
    }

    @Override
    public boolean eval_cell(Entity e, DirRelative dir, char t) {
        if (dir==DirRelative.soi){
            return g.getCell(this.x, this.y).getCategory() == t;
        }
        switch (direction) {
            case Nord:
                switch (dir) {
                    case Devant:
                        return g.getCell(this.x,(this.y + g.getRows() -1)%g.getRows()).getCategory()==t;   
                    case Derriere:
                        return g.getCell(this.x, (this.y + 1) % g.getRows()).getCategory() == t;
                    case Droite:
                        return g.getCell((this.x+1)%g.getCols(), this.y).getCategory() == t;
                    case Gauche:
                        return g.getCell((this.x + g.getCols() -1) % g.getCols(), this.y).getCategory() == t;
                    default:
                        return false;
                }
                
            case Sud:
                switch (dir) {
                    case Devant:
                        return g.getCell(this.x, (this.y + 1) % g.getRows()).getCategory() == t;
                        
                    case Derriere: 
                        return g.getCell(this.x, (this.y  + g.getRows() - 1) % g.getRows()).getCategory() == t;
                        
                        
                    case Droite:
                        return g.getCell((this.x+g.getCols() - 1) % g.getCols(), this.y).getCategory() == t;
                        
                    case Gauche:
                        return g.getCell((this.x + 1) % g.getCols(), this.y).getCategory() == t;
                        
                    default:
                        return false;
                }
              
            case Est:
                switch (dir) {
                    case Devant:
                        return g.getCell((this.x + 1) % g.getCols(), this.y).getCategory() == t;
                        
                    case Derriere:
                        return g.getCell((this.x +g.getCols()- 1) % g.getCols(), this.y).getCategory() == t;
                        
                    case Droite:
                        return g.getCell(this.x, (this.y + 1) % g.getRows()).getCategory() == t;
                        
                    case Gauche:
                        return g.getCell(this.x, (this.y + g.getRows()  - 1) % g.getRows()).getCategory() == t;
                        
                    default:
                        return false;
                }
              
            case Ouest:
                switch (dir) {
                    case Devant:

                        return g.getCell((this.x +g.getCols()- 1) % g.getCols(), this.y).getCategory() == t;
                    case Derriere:
                        return g.getCell((this.x + 1) % g.getCols(), this.y).getCategory() == t;
                        
                    case Droite:

                        return g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).getCategory() == t;
                    case Gauche:
                        return g.getCell(this.x, (this.y + 1) % g.getRows()).getCategory() == t;
                        
                    default:
                        return false;
                }
                
            default:
                return false;
        }
    }

   @Override
    public boolean do_move(Entity e, DirRelative dir) {
        in_movement = nb_frame_move;
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
    public boolean do_hit(Entity e, DirRelative dir) {
        
        switch (direction) {
            case Nord:
                switch (dir) {
                    case Devant:
                        if (g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().setLife(
                                    g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().getLife()- 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Derriere:
                        if (g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity()
                                    .setLife(g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Droite:
                        if (g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity()
                                    .setLife(g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Gauche:
                        if (g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().setLife(
                                    g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            case Sud:
                switch (dir) {
                    case Devant:
                        if (g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity()
                                    .setLife(g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Derriere:
                        if (g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().setLife(
                                    g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Droite:
                        if (g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().setLife(
                                    g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Gauche:
                        if (g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity()
                                    .setLife(g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            case Est:
                switch (dir) {
                    case Devant:
                        if (g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity()
                                    .setLife(g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Derriere:
                        if (g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().setLife(
                                    g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Droite:
                        if (g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity()
                                    .setLife(g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Gauche:
                        if (g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().setLife(
                                    g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            case Ouest:
                switch (dir) {
                    case Devant:
                        if (g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().setLife(
                                    g.getCell((this.x + g.getCols() - 1) % g.getCols(), this.y).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Derriere:
                        if (g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity() != null) {
                            g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity()
                                    .setLife(g.getCell((this.x + 1) % g.getCols(), this.y).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Droite:
                        if (g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().setLife(
                                    g.getCell(this.x, (this.y + g.getRows() - 1) % g.getRows()).GetEntity().getLife()
                                            - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    case Gauche:
                        if (g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity() != null) {
                            g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity()
                                    .setLife(g.getCell(this.x, (this.y + 1) % g.getRows()).GetEntity().getLife() - 1);
                            do_die(this);
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
}
