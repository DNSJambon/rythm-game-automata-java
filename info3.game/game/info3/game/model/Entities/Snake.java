package info3.game.model.Entities;

import java.awt.Graphics;
import java.util.LinkedList;

import info3.game.controller.*;
import info3.game.controller.Actions.Egg;
import info3.game.controller.Actions.Move;
import info3.game.controller.Actions.Turn;
import info3.game.controller.Conditions.Cell;
import info3.game.controller.Conditions.Random;
import info3.game.controller.Conditions.True;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.cellType;

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
    public int size;
    public coordonnees head;
    public coordonnees last;
    public coordonnees egg;

    public Snake(Automate a, Grille g) {
        super(g);
        super.a = a;
        size = 1;
        direction = Direction.Est;
        snake = new LinkedList<coordonnees>();
          
        coordonnees c = new coordonnees(1, 1);
        coordonnees c2 = new coordonnees(1, 1);
        coordonnees c3 = new coordonnees(1, 1);
        head = c;
        snake.addFirst(head);   
        last=c2;
        egg=c3;
        g.getCell(1, 1).setEntity(this);
    }
    
    //Constructeur avec automate par défaut
    public Snake(Grille g) {
        super(g);
        size = 1;
        direction = Direction.Est;
        snake = new LinkedList<coordonnees>();

        coordonnees c = new coordonnees(1, 1);
        coordonnees c2 = new coordonnees(1, 1);
        coordonnees c3 = new coordonnees(1, 1);
        head = c;
        snake.addFirst(head);
        last = c2;
        egg = c3;
        g.getCell(1, 1).setEntity(this);

        // Snake:
        Transition[] T2 = new Transition[7];
        Cell cond_apple_devant = new Cell(DirRelative.Devant, Category.P);
        Cell cond_apple_droite = new Cell(DirRelative.Droite, Category.P);
        Cell cond_apple_gauche = new Cell(DirRelative.Gauche, Category.P);
        Cell cond_obsatcle = new Cell(DirRelative.Devant, Category.T);
        True cond_true = new True();
        Random r = new Random(20);

        Egg queue = new Egg();
        Turn gauche = new Turn(DirRelative.Gauche);
        Turn droite = new Turn(DirRelative.Droite);
        Move m = new Move(DirRelative.Devant);

        T2[0] = new Transition(gauche, cond_obsatcle, 0, 0);
        T2[1] = new Transition(m, cond_apple_devant, 0, 1);
        T2[2] = new Transition(droite, cond_apple_droite, 0, 1);
        T2[3] = new Transition(gauche, cond_apple_gauche, 0, 1);
        T2[4] = new Transition(droite, r, 0, 0);
        T2[5] = new Transition(m, cond_true, 0, 0);
        T2[6] = new Transition(queue, cond_true, 1, 0);

        Automate a2 = new Automate(0, T2);
        super.a = a2;
        m.e_or = this;
        gauche.e_or = this;
        droite.e_or = this;
        queue.e_or = this;
    }

    
     @Override
    public cellType getType() {
        return cellType.Snake;
    }
    
    @Override
    public char getCategory() {
        return Category.T;
    }




    @Override
    public boolean eval_cell(Entity e, DirRelative dir, char t) {
        if (dir==DirRelative.soi){
            return g.getCell(head.x, head.y).getCategory() == t;
        }
        switch (direction) {
            case Nord:
                switch (dir) {
                    case Devant:
                        return g.getCell(head.x,(head.y + g.getRows() -1)%g.getRows()).getCategory()==t;   
                    case Derriere:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getCategory() == t;
                    case Droite:
                        return g.getCell((head.x+1)%g.getCols(), head.y).getCategory() == t;
                    case Gauche:
                        return g.getCell((head.x + g.getCols() -1) % g.getCols(), head.y).getCategory() == t;
                    default:
                        return false;
                }
                
            case Sud:
                switch (dir) {
                    case Devant:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getCategory() == t;
                        
                    case Derriere: 
                        return g.getCell(head.x, (head.y  + g.getRows() - 1) % g.getRows()).getCategory() == t;
                        
                        
                    case Droite:
                        return g.getCell((head.x+g.getCols() - 1) % g.getCols(), head.y).getCategory() == t;
                        
                    case Gauche:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getCategory() == t;
                        
                    default:
                        return false;
                }
              
            case Est:
                switch (dir) {
                    case Devant:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getCategory() == t;
                        
                    case Derriere:
                        return g.getCell((head.x +g.getCols()- 1) % g.getCols(), head.y).getCategory() == t;
                        
                    case Droite:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getCategory() == t;
                        
                    case Gauche:
                        return g.getCell(head.x, (head.y + g.getRows()  - 1) % g.getRows()).getCategory() == t;
                        
                    default:
                        return false;
                }
              
            case Ouest:
                switch (dir) {
                    case Devant:

                        return g.getCell((head.x +g.getCols()- 1) % g.getCols(), head.y).getCategory() == t;
                    case Derriere:
                        return g.getCell((head.x + 1) % g.getCols(), head.y).getCategory() == t;
                        
                    case Droite:

                        return g.getCell(head.x, (head.y + g.getRows() - 1) % g.getRows()).getCategory() == t;
                    case Gauche:
                        return g.getCell(head.x, (head.y + 1) % g.getRows()).getCategory() == t;
                        
                    default:
                        return false;
                }
                
            default:
                return false;
        }


    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {

        switch (this.direction) {
            
            case Nord:
                
                    last.x = head.x;
                    last.y = head.y;
                    egg.x=snake.get(size - 1).x;
                    egg.y=snake.get(size - 1).y;
                    g.getCell(head.x, head.y).resetEntity();
                    head.y=(head.y + g.getRows() - 1) % g.getRows();
                    g.getCell(head.x, head.y ).setEntity(this);
                    if (size>1){

                    
                        g.getCell(snake.get(size - 1).x, snake.get(size - 1).y).resetEntity();
                        g.getCell(last.x, last.y).setEntity(this);
                        snake.get(size - 1).x = last.x;
                        snake.get(size - 1).y = last.y;
                        snake.add(1, snake.remove(size - 1));
                    }
                
                
                
                return true;
            case Sud:

                last.x = head.x;
                last.y = head.y;
                egg.x = snake.get(size - 1).x;
                egg.y = snake.get(size - 1).y;
                g.getCell(head.x, head.y).resetEntity();
                head.y = (head.y + 1) % g.getRows();
                g.getCell(head.x, head.y).setEntity(this);
               
                
                if (size>1) {
                    g.getCell(snake.get(size - 1).x, snake.get(size - 1).y).resetEntity();                    
                    g.getCell(last.x, last.y).setEntity(this);
                    snake.get(size - 1).x=last.x;
                    snake.get(size - 1).y=last.y;
                    snake.add(1, snake.remove(size - 1));
                }

               
                
                return true;
            case Est:
                
                last.x = head.x;
                last.y = head.y;
                egg.x = snake.get(size - 1).x;
                egg.y = snake.get(size - 1).y;
                g.getCell(head.x, head.y).resetEntity();
                head.x = (head.x + 1) % g.getCols();
                g.getCell(head.x, head.y).setEntity(this);
                if (size>1){

                    g.getCell(snake.get(size - 1).x, snake.get(size - 1).y).resetEntity();
                    g.getCell(last.x, last.y).setEntity(this);
                    snake.get(size - 1).x = last.x;
                    snake.get(size - 1).y = last.y;
                    snake.add(1, snake.remove(size - 1));
                }
                
                return true;
            case Ouest:

                last.x = head.x;
                last.y = head.y;
                egg.x = snake.get(size - 1).x;
                egg.y = snake.get(size - 1).y;
                g.getCell(head.x, head.y).resetEntity();
                head.x = (head.x + g.getCols() - 1) % g.getCols();
                g.getCell(head.x, head.y).setEntity(this);

                if (size>1){
                    g.getCell(snake.get(size - 1).x, snake.get(size - 1).y).resetEntity();
                    g.getCell(last.x, last.y).setEntity(this);
                    snake.get(size - 1).x = last.x;
                    snake.get(size - 1).y = last.y;
                    snake.add(1, snake.remove(size - 1));
                }

                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean do_egg(Entity e) {
        coordonnees c=new coordonnees(egg.x, egg.y);
        snake.addLast(c);
        g.getCell(c.x, c.y).setEntity(this);
        size++;
        return true;

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
    public boolean do_turn(Entity e, DirRelative d) {
        switch (d) {
            case Devant:
                this.do_move(e, d);
                return true;
            case Derriere:
                this.do_move(e, d);
                return true;
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

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(g.getImage(359), x, y, width, height, null);
    }

    @Override
    public void tick(long elapsed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }


    
}