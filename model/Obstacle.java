package model;

import controller.*;

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
        return cellType.Obstacle;
    }

    @Override
    public boolean eval_cell(Entity e, DirRelative dir, cellType type) {
        return true;
    }
    
    @Override
    public boolean do_move(Entity e) {
        int yclear=y;
        int xclear=x;
        switch (e.direction) {
            case Nord:
                e.y-=1;
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
                e.x-=1;
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
    public boolean do_turn(Entity e, DirRelative dir) {
        switch (e.direction) {
            case Nord:
                e.direction=Direction.Est;
                return true;
            case Sud:
                e.direction=Direction.Est;
                return true;
            case Est:
                e.direction=Direction.Est;
                return true;
            case Ouest:
                e.direction=Direction.Est;
                return true;
            default:
                return false;
        }
    }
    
}
