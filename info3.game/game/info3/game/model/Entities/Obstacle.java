package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.*;
import info3.game.controller.Actions.Wait;
import info3.game.controller.Conditions.True;
import info3.game.model.*;

public class Obstacle extends Entity {

    public Obstacle(IGrille g, int x, int y, Automate automate) {
        super(g);
        etat_courant = automate.getState();
        this.a = automate;
        this.x = x;
        this.y = y;
        this.direction = Direction.Sud;

        g.getCell(x, y).setEntity(this);
    }
    
    

    



    @Override
    public cellType getType() {
        return cellType.Obstacle;
    }

    public char getCategory() {
        return Category.O;
    }


    @Override
    public boolean eval_cell(Entity e, DirRelative dir, char type) {
        if (dir==DirRelative.soi) {
            return type==this.getCategory();
        }
        switch (e.direction) {
            case Nord:
                switch (dir) {
                    case Devant:
                        return g.getCell(x,(y + g.getRows() -1)%g.getRows()).getCategory()==type;   
                    case Derriere:
                        return g.getCell(x, (y + 1) % g.getRows()).getCategory() ==type;
                    case Droite:
                        return g.getCell((x+1)%g.getCols(), y).getCategory() ==type;
                    case Gauche:
                        return g.getCell((x + g.getCols() -1) % g.getCols(), y).getCategory() ==type;
                    default:
                        return false;
                }
            case Sud:
                switch (dir) {
                    case Devant:
                        return g.getCell(x, (y + 1) % g.getRows()).getCategory() ==type;
                    case Derriere: 
                        return g.getCell(x, (y  + g.getRows() - 1) % g.getRows()).getCategory() ==type;
                    case Droite:
                        return g.getCell((x+g.getCols() - 1) % g.getCols(), y).getCategory() ==type;
                    case Gauche:
                        return g.getCell((x + 1) % g.getCols(), y).getCategory() ==type;
                    default:
                        return false;
                }
            case Est:
                switch (dir) {
                    case Devant:
                        return g.getCell((x + 1) % g.getCols(), y).getCategory() ==type;
                    case Derriere:
                        return g.getCell((x +g.getCols()- 1) % g.getCols(), y).getCategory() ==type;
                    case Droite:
                        return g.getCell(x, (y + 1) % g.getRows()).getCategory() ==type;
                    case Gauche:
                        return g.getCell(x, (y + g.getRows()  - 1) % g.getRows()).getCategory() ==type;
                    default:
                        return false;
                }
            case Ouest:
                switch (dir) {
                    case Devant:
                        return g.getCell((x +g.getCols()- 1) % g.getCols(), y).getCategory() ==type;
                    case Derriere:
                        return g.getCell((x + 1) % g.getCols(), y).getCategory() ==type;
                    case Droite:
                        return g.getCell(x, (y + g.getRows() - 1) % g.getRows()).getCategory() ==type;
                    case Gauche:
                        return g.getCell(x, (y + 1) % g.getRows()).getCategory() ==type;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
    
    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        int yclear=y;
        int xclear=x;
        switch (e.direction) {
            case Nord:
                e.y+=(g.getCols()-1);
                e.y=e.y%g.getRows();
                g.getCell(xclear,yclear).resetEntity();
                g.getCell(x,y).setEntity(this);
                return true;
            case Sud:
                e.y+=1;
                e.y=e.y%g.getRows();
                g.getCell(xclear,yclear).resetEntity();
                g.getCell(x,y).setEntity(this);
                return true;
            case Est:
                e.x+=1;
                e.x=e.x%g.getCols();
                g.getCell(xclear,yclear).resetEntity();
                g.getCell(x,y).setEntity(this);
                return true;
            case Ouest:
                e.x+=(g.getRows()-1);
                e.x=e.x%g.getCols();
                g.getCell(xclear,yclear).resetEntity();
                g.getCell(x,y).setEntity(this);
                return true;
            default:
                return false;
        }
    }



    @Override
    public boolean do_pick(Entity e) {
        return true;
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
        switch (e.direction) {
            case Nord:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Est;
                    case Gauche:
                        e.direction=Direction.Ouest;
                    case Derriere:
                        e.direction=Direction.Sud;
                    default:
                        return false;
                }
            case Sud:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Ouest;
                    case Gauche:
                        e.direction=Direction.Est;
                    case Derriere:
                        e.direction=Direction.Nord;
                    default:
                        return false;
                }
            case Est:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Sud;
                    case Gauche:
                        e.direction=Direction.Nord;
                    case Derriere:
                        e.direction=Direction.Ouest;
                    default:
                        return false;
                }
            case Ouest:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Nord;
                    case Gauche:
                        e.direction=Direction.Sud;
                    case Derriere:
                        e.direction=Direction.Est;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }


    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(((Grille)g).getImage(361), x, y, width, height, null);
    }

    @Override
    public void tick(long elapsed) {
        
    }







    @Override
    public boolean do_egg(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_egg'");
    }

    
}
