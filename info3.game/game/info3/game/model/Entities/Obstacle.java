package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.*;
import info3.game.controller.Actions.Wait;
import info3.game.controller.Conditions.True;
import info3.game.model.*;

public class Obstacle extends Entity {

    public Obstacle(IGrille g, Automaton a, int x, int y) {
        super(g);
        etat_courant = 0;
        this.a = a;
        this.x = x;
        this.y = y;
        this.direction = Direction.Sud;

        g.getCell(x, y).setEntity(this);
    }
    
    //Obstacle avec automate par défaut (automate vide)
    public Obstacle(IGrille g, int x, int y) {
        super(g);
        etat_courant = 0;
        this.a = new Automaton(0, new Transition[0]);
        this.x = x;
        this.y = y;
        this.direction = Direction.Sud;

        g.getCell(x, y).setEntity(this);


        Transition[] T = new Transition[1];
        True t = new True();
        Wait w = new Wait();
        T[0] = new Transition(w, t, 0, 0);
        w.e_or = this;
        this.a = new Automaton(0, T);
    }

    @Override
    public cellType getType() {
        return cellType.Obstacle;
    }


    @Override
    public boolean eval_cell(Entity e, DirRelative dir, cellType type) {
        if (dir==DirRelative.soi) {
            return type==this.getType();
        }
        switch (e.direction) {
            case Nord:
                switch (dir) {
                    case Devant:
                        return g.getCell(x,(y + g.getRows() -1)%g.getRows()).getType()==type;   
                    case Derriere:
                        return g.getCell(x, (y + 1) % g.getRows()).getType() ==type;
                    case Droite:
                        return g.getCell((x+1)%g.getCols(), y).getType() ==type;
                    case Gauche:
                        return g.getCell((x + g.getCols() -1) % g.getCols(), y).getType() ==type;
                    default:
                        return false;
                }
            case Sud:
                switch (dir) {
                    case Devant:
                        return g.getCell(x, (y + 1) % g.getRows()).getType() ==type;
                    case Derriere: 
                        return g.getCell(x, (y  + g.getRows() - 1) % g.getRows()).getType() ==type;
                    case Droite:
                        return g.getCell((x+g.getCols() - 1) % g.getCols(), y).getType() ==type;
                    case Gauche:
                        return g.getCell((x + 1) % g.getCols(), y).getType() ==type;
                    default:
                        return false;
                }
            case Est:
                switch (dir) {
                    case Devant:
                        return g.getCell((x + 1) % g.getCols(), y).getType() ==type;
                    case Derriere:
                        return g.getCell((x +g.getCols()- 1) % g.getCols(), y).getType() ==type;
                    case Droite:
                        return g.getCell(x, (y + 1) % g.getRows()).getType() ==type;
                    case Gauche:
                        return g.getCell(x, (y + g.getRows()  - 1) % g.getRows()).getType() ==type;
                    default:
                        return false;
                }
            case Ouest:
                switch (dir) {
                    case Devant:
                        return g.getCell((x +g.getCols()- 1) % g.getCols(), y).getType() ==type;
                    case Derriere:
                        return g.getCell((x + 1) % g.getCols(), y).getType() ==type;
                    case Droite:
                        return g.getCell(x, (y + g.getRows() - 1) % g.getRows()).getType() ==type;
                    case Gauche:
                        return g.getCell(x, (y + 1) % g.getRows()).getType() ==type;
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
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            case Sud:
                e.y+=1;
                e.y=e.y%g.getRows();
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            case Est:
                e.x+=1;
                e.x=e.x%g.getCols();
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            case Ouest:
                e.x+=(g.getRows()-1);
                e.x=e.x%g.getCols();
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean do_egg(Entity e) {
        switch (e.direction) {
            case Nord:
                e.y+=(g.getCols()-1);
                e.y=e.y%g.getRows();
                g.getCell(x,y).setEntity(new Obstacle(g, a, x, y));
                return true;
            case Sud:
                e.y+=1;
                e.y=e.y%g.getRows();
                g.getCell(x,y).setEntity(new Obstacle(g, a, x, y));
                return true;
            case Est:
                e.x+=1;
                e.x=e.x%g.getCols();
                g.getCell(x,y).setEntity(new Obstacle(g, a, x, y));
                return true;
            case Ouest:
                e.x+=(g.getRows()-1);
                e.x=e.x%g.getCols();
                g.getCell(x,y).setEntity(new Obstacle(g, a, x, y));
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
    public boolean do_wait(Entity e) {
        return true;
    }

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(g.getImage(361), x, y, width, height, null);
    }

    @Override
    public void tick(long elapsed) {
        
    }

    
}
