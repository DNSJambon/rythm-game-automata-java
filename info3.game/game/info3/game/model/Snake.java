package info3.game.model;

import java.util.LinkedList;

import info3.game.controller.*;

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
    coordonnees last;
    coordonnees egg;

    public Snake(Automaton a, IGrille g) {
        super(g);
        super.a = a;
        size = 1;
        direction = Direction.Est;
        snake = new LinkedList<coordonnees>();
        coordonnees c = new coordonnees(1, 1);
        coordonnees c2 = new coordonnees(1, 1);
        coordonnees c3 = new coordonnees(1, 1);
        head = c;
        last = c2;
        egg = c3;
        g.getCell(1, 1).setEntity(this);
    }
    
    public Snake(IGrille g) {
        super(g);
        size = 1;
        direction = Direction.Est;
        snake = new LinkedList<coordonnees>();
        coordonnees c = new coordonnees(x, y);
        coordonnees c2 = new coordonnees(x, y);
        coordonnees c3 = new coordonnees(x, y);
        head = c;
        last = c2;
        egg = c3;
        g.getCell(x, y).setEntity(this);

        //Snake:
        Transition[] T2 = new Transition[5];
        Cell cond_apple= new Cell(DirRelative.Devant,cellType.Apple);
        Cell cond_obsatcle = new Cell(DirRelative.Devant, cellType.Obstacle);
        True cond_true = new True();
        Random r = new Random(20);
        
        Egg queue= new Egg();
        Turn gauche = new Turn(DirRelative.Gauche);
        Turn droite = new Turn(DirRelative.Droite);
        Move m = new Move();

        T2[3] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[1] = new Transition(droite, r, 0, 0);
        T2[2] = new Transition(m, cond_true, 0, 0);
        T2[0]= new Transition(m, cond_apple, 0, 1);
        T2[4]=new Transition(queue, cond_true, 1, 0);
        

        Automaton a2 = new Automaton(0, T2);
        m.e_or=this;
        gauche.e_or = this;
        droite.e_or = this;
        queue.e_or=this;
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
                        return g.getCell(head.x,(head.y + g.getRows() -1)%g.getRows()).getType()==t;   
                    case Derriere:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                    case Droite:
                        return g.getCell((head.x+1)%g.getCols(), head.y).getType() == t;
                    case Gauche:
                        return g.getCell((head.x + g.getCols() -1) % g.getCols(), head.y).getType() == t;
                    default:
                        return false;
                }
                
            case Sud:
                switch (dir) {
                    case Devant:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                        
                    case Derriere: 
                        return g.getCell(head.x, (head.y  + g.getRows() - 1) % g.getRows()).getType() == t;
                        
                        
                    case Droite:
                        return g.getCell((head.x+g.getCols() - 1) % g.getCols(), head.y).getType() == t;
                        
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
                        return g.getCell((head.x +g.getCols()- 1) % g.getCols(), head.y).getType() == t;
                        
                    case Droite:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getType() == t;
                        
                    case Gauche:
                        return g.getCell(head.x, (head.y + g.getRows()  - 1) % g.getRows()).getType() == t;
                        
                    default:
                        return false;
                }
              
            case Ouest:
                switch (dir) {
                    case Devant:

                        return g.getCell((head.x +g.getCols()- 1) % g.getCols(), head.y).getType() == t;
                    case Derriere:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getType() == t;
                        
                    case Droite:

                        return g.getCell(head.x, (head.y + g.getRows() - 1) % g.getRows()).getType() == t;
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
                
                last.x = head.x;
                last.y = head.y;
                g.getCell(head.x, head.y).reset();
                head.y=(head.y + g.getRows() - 1) % g.getRows();
                g.getCell(head.x, head.y ).setEntity(this);
                if (size > 1) {
                    g.getCell(snake.get(size - 2).x, snake.get(size - 2).y).reset();
                    egg.x=snake.get(size - 2).x;
                    egg.y=snake.get(size - 2).y;
                    snake.get(size - 2).x = last.x;
                    snake.get(size - 2).y = last.y;
                    g.getCell(last.x, last.y).setEntity(this);
                }
                
                
                return true;
            case Sud:
                last.x = head.x;
                last.y = head.y;
                g.getCell(head.x, head.y).reset();
                head.y = (head.y + 1) % g.getRows();
                g.getCell(head.x, head.y ).setEntity(this);

                if (size > 1) {
                    g.getCell(snake.get(size - 2).x, snake.get(size - 2).y).reset();
                    snake.get(size - 2).x = last.x;
                    snake.get(size - 2).y = last.y;
                    g.getCell(last.x, last.y).setEntity(this);
                }
               
                
                return true;
            case Est:
            
                last.x=head.x;
                last.y=head.y;
                g.getCell(head.x, head.y).reset();
                head.x=(head.x + 1) % g.getCols();
                g.getCell(head.x, head.y).setEntity(this);
                if (size>1){
                    g.getCell(snake.get(size-2).x, snake.get(size-2).y).reset();
                    snake.get(size-2).x=last.x;
                    snake.get(size-2).y=last.y;
                    g.getCell(last.x,last.y).setEntity(this); 
                }
                
                return true;
            case Ouest:
                last.x = head.x;
                last.y = head.y;
                g.getCell(head.x, head.y).reset();
                head.x=(head.x + g.getCols() -1) % g.getCols();
                g.getCell(head.x, head.y).setEntity(this);
                if (size > 1) {
                    g.getCell(snake.get(size - 2).x, snake.get(size - 2).y).reset();
                    snake.get(size - 2).x = last.x;
                    snake.get(size - 2).y = last.y;
                    g.getCell(last.x, last.y).setEntity(this);
                }

                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean do_egg(Entity e) {
        if (size==1){
            coordonnees c = new coordonnees(last.x, last.y);
            snake.addFirst(c);
            g.getCell(c.x, c.y).setEntity(this);
            size++;
            return true;
        }
        coordonnees c=new coordonnees(egg.x, egg.y);
        snake.addFirst(c);
        g.getCell(c.x, c.y).setEntity(this);
        size++;
        return true;

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
                this.do_move(e);
                return true;
            case Derriere:
                this.do_move(e);
                return true;
            case Droite:
                switch (direction) {
                    case Nord:
                        direction = Direction.Est;
                        this.do_move(e);
                        return true;
                    case Sud:
                        direction = Direction.Ouest;
                        this.do_move(e);
                        return true;
                    case Est:
                        direction = Direction.Sud;
                        this.do_move(e);
                        return true;
                    case Ouest:
                        direction = Direction.Nord;
                        this.do_move(e);
                        return true;
                    default:
                        return false;
                }

            case Gauche:
                switch (direction) {
                    case Nord:
                        direction = Direction.Ouest;
                        this.do_move(e);
                        return true;
                    case Sud:
                        direction = Direction.Est;
                        this.do_move(e);
                        return true;
                    case Est:
                        direction = Direction.Nord;
                        this.do_move(e);
                        return true;
                    case Ouest:
                        direction = Direction.Sud;
                        this.do_move(e);
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
}