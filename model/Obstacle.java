package model;

import controller.Automaton;
import controller.DirRelative;
import controller.Direction;

public class Obstacle extends Entity {


    public Obstacle (IGrille g,Automaton a,int x,int y){
        super(g);
        etat_courant=0;
        this.a=a;
        this.x=x;
        this.y=y;

        g.getCell(x,y).setEntity(this);
    }

    @Override
    public cellType getType() {
        return cellType.obstacle;
    }

    @Override
    public boolean eval_cell(Entity e, DirRelative dir, cellType type) {
        if (dir==DirRelative.soi) {
            return type==this.getType();
        }
        switch (e.direction) {
            case Nord:
                switch (dir) {
                    case Droite:
                        return type==g.getCell(x+1%g.getRows(),y).getType();
                    case Gauche:
                        return type==g.getCell(x-1%g.getRows(),y).getType();
                    case Derriere:
                        return type==g.getCell(x,y+1%g.getCols()).getType();
                    case Devant:
                        return type==g.getCell(x,y-1%g.getCols()).getType();
                    default:
                        return false;
                }
            case Sud:
                switch (dir) {
                    case Droite:
                        return type==g.getCell(x-1%g.getRows(),y).getType();
                    case Gauche:
                        return type==g.getCell(x+1%g.getRows(),y).getType();
                    case Derriere:
                        return type==g.getCell(x,y-1%g.getCols()).getType();
                    case Devant:
                        return type==g.getCell(x,y+1%g.getCols()).getType();
                    default:
                        return false;
                }
            case Est:
                switch (dir) {
                    case Droite:
                        return type==g.getCell(x,y-1%g.getCols()).getType();
                    case Gauche:
                        return type==g.getCell(x,y+1%g.getCols()).getType();
                    case Derriere:
                        return type==g.getCell(x-1%g.getRows(),y).getType();
                    case Devant:
                        return type==g.getCell(x+1%g.getRows(),y).getType();
                    default:
                        return false;
                }
            case Ouest:
                switch (dir) {
                    case Droite:
                        return type==g.getCell(x,y+1%g.getCols()).getType();
                    case Gauche:
                        return type==g.getCell(x,y-1%g.getCols()).getType();
                    case Derriere:
                        return type==g.getCell(x+1%g.getRows(),y).getType();
                    case Devant:
                        return type==g.getCell(x-1%g.getRows(),y).getType();
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
    
    
    @Override
    public boolean do_move(Entity e) {
        int yclear=y;
        int xclear=x;
        switch (e.direction) {
            case Nord:
                e.y-=1;
                e.y=e.y%g.getCols();
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            case Sud:
                e.y+=1;
                e.y=e.y%g.getCols();
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            case Est:
                e.x+=1;
                e.x=e.x%g.getRows();
                g.getCell(xclear,yclear).reset();
                g.getCell(x,y).setEntity(this);
                return true;
            case Ouest:
                e.x-=1;
                e.x=e.x%g.getRows();
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
                e.y-=1;
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
                e.x-=1;
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
    public boolean do_turn(Entity e,DirRelative dir) {
        if (dir==DirRelative.soi || dir==DirRelative.Devant){
            return true;
        }
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
    
}
