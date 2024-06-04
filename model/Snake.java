package model;

import java.util.LinkedList;

import controller.Automaton;
import controller.Direction;
public class Snake extends Entity{
    private class coordonnees{
        int x;
        int y;

        coordonnees(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    java.util.LinkedList<coordonnees> snake;
    int size;
    coordonnees head;
    

    Snake(Automaton a,IGrille g){
        super(g);
        super.a=a;
        size=1;
        direction= Direction.Est;
        snake=new LinkedList<coordonnees>();
        coordonnees c=new coordonnees(1,1);
        head=c;
        snake.addFirst(c);
        g.getCell(1,1).setEntity(this);
    }


    @Override
    public boolean eval_cell(Entity e, Direction dir, cellType t) {
        if (dir==Direction.Nord && g.getCell(x,y-1)!=null){
            return g.getCell(x, y-1).getType()==t;
        }
        else if (dir==Direction.Sud && g.getCell(x, y + 1) != null){
            return g.getCell(x, y + 1).getType() == t;
        }
        else if (dir==Direction.Est && g.getCell(x+1, y ) != null){
             return g.getCell(x+1, y).getType() == t;
        }
        else if (dir==Direction.Ouest && g.getCell(x-1, y) != null){
            return g.getCell(x-1,y).getType() == t;
        }
        return false;
    }

    @Override
    public boolean do_move(Entity e,Direction dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_egg(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_egg'");
    }



    @Override
    public cellType getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }


    @Override
    public boolean do_pick(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_pick'");
    }


    @Override
    public boolean do_turn(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }
}