package model;

import java.util.LinkedList;

import controller.Automaton;
import controller.DirRelative;
import controller.Direction;

public class Snake extends Entity {
    private class coordonnees {
        int x;
        int y;

        coordonnees(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    java.util.LinkedList<coordonnees> snake;
    int size;
    coordonnees head;

    public Snake(Automaton a, IGrille g) {
        super(g);
        super.a = a;
        size = 1;
        direction = Direction.Est;
        snake = new LinkedList<coordonnees>();
        coordonnees c = new coordonnees(1, 1);
        head = c;
        g.getCell(1, 1).setEntity(this);
    }

    @Override
    public boolean eval_cell(Entity e, DirRelative dir, cellType t) {
        if (dir==DirRelative.soi){
            return g.getCell(head.x, head.y).getType() == t;
        }
        switch (direction) {
            case Nord:
                switch (dir) {
                    case Devant:
                        return g.getCell(head.x,(head.y-1)%g.getRows()).getType()==t;   
                    case Derriere:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                    case Droite:
                        return g.getCell((head.x+1)%g.getCols(), head.y).getType() == t;
                    case Gauche:
                        return g.getCell((head.x -1) % g.getCols(), head.y).getType() == t;
                    default:
                        return false;
                }
                
            case Sud:
                switch (dir) {
                    case Devant:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                        
                    case Derriere: 
                        return g.getCell(head.x, (head.y - 1) % g.getRows()).getType() == t;
                        
                        
                    case Droite:
                        return g.getCell((head.x - 1) % g.getCols(), head.y).getType() == t;
                        
                    case Gauche:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getType() == t;
                        
                    default:
                        return false;
                }
              
            case Est:
                switch (dir) {
                    case Devant:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getType() == t;
                        
                    case Derriere:
                        return g.getCell((head.x - 1) % g.getCols(), head.y).getType() == t;
                        
                    case Droite:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                        
                    case Gauche:
                        return g.getCell(head.x, (head.y - 1) % g.getRows()).getType() == t;
                        
                    default:
                        return false;
                }
              
            case Ouest:
                switch (dir) {
                    case Devant:

                        return true;
                    case Derriere:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getType() == t;
                        
                    case Droite:

                        return g.getCell(head.x, (head.y - 1) % g.getRows()).getType() == t;
                    case Gauche:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                        
                    default:
                        return false;
                }
                
            default:
                return false;
        }


    }

    @Override
    public boolean do_move(Entity e) {

        switch (this.direction) {

            case Nord:
                g.getCell(head.x, head.y).reset();
                g.getCell(head.x, (head.y - 1) % g.getRows()).setEntity(this);
                head.y=(head.y - 1) % g.getRows();
                return true;
            case Sud:
                g.getCell(head.x, head.y).reset();
                g.getCell(head.x, (head.y + 1) % g.getRows()).setEntity(this);
                head.y = (head.y + 1) % g.getRows();
                return true;
            case Est:
                g.getCell(head.x, head.y).reset();
                g.getCell((head.x + 1) % g.getCols(), head.y).setEntity(this);
                head.x=(head.x + 1) % g.getCols();
                return true;
            case Ouest:
                g.getCell(head.x, head.y).reset();
                g.getCell((head.x - 1) % g.getCols(), head.y).setEntity(this);
                head.x=(head.x -1 ) % g.getCols();
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
    public cellType getType() {
        return cellType.Snake;
    }

    @Override
    public boolean do_pick(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_pick'");
    }

    @Override
    public boolean do_turn(Entity e, DirRelative d) {
        switch (d) {
            case Devant:
            return true;
            case Derriere:

                return true;
            case Droite:
                switch (direction) {
                    case Nord:
                        direction=Direction.Est;
                        return true;
                    case Sud:
                        direction=Direction.Ouest;
                        return true;
                    case Est:
                        direction=Direction.Sud;
                        return true;
                    case Ouest:
                        direction=Direction.Nord;
                        return true;
                    default:
                        return false;
                }

            case Gauche:
                switch (direction) {
                    case Nord:
                        direction=Direction.Ouest;
                        return true;
                    case Sud:
                        direction=Direction.Est;
                        return true;
                    case Est:
                        direction=Direction.Sud;
                        return true;
                    case Ouest:
                        direction=Direction.Nord;
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
}