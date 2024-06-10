package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.*;
import info3.game.model.Category;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public abstract class Entity {
    IGrille g;
    public int etat_courant;
    Automaton a;
    public Direction direction;

    int x, y;

    public Entity(IGrille g) {
        this.g = g;
        direction = Direction.Est;
    }

    public abstract cellType getType();

    public abstract char getCategory();


    public void step(BufferAction buff) {
        a.step_A(this, buff);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IGrille getGrille() {
        return g;
    }
    
    public Direction RelativeToAbsolute(DirRelative d){
        Direction[] dirs = { Direction.Nord, Direction.Est, Direction.Sud, Direction.Ouest };
        int i = 0;
        while (dirs[i] != this.direction)
            i++;

        switch (d) {
            case Droite:
                i = (i + 1) % 4;
                break;
            case Gauche:
                i = (i + 3) % 4;
                break;
            case Derriere:
                i = (i + 2) % 4;
                break;
            default:
                break;
        }
        
        return dirs[i];
    }
    
    
    //abstract boolean eval(...);
    public boolean eval_cell(Entity e, DirRelative dir, char t) {
        if (dir == DirRelative.soi) {
            return g.getCell(this.x, this.y).getCategory() == t;
        }
        
        Direction d = RelativeToAbsolute(dir);

        switch (d) {
            case Nord:
                if (this.y == 0)
                    //On considère que les bords de la grille sont des obstacles
                    return Category.O == t;
                return g.getCell(this.x, this.y-1).getCategory() == t;

            case Est:
                if (this.x == g.getCols()-1)
                    return Category.O == t;
                return g.getCell(this.x + 1, this.y).getCategory() == t;
            
            case Sud:
                if (this.y == g.getRows()-1)
                    return Category.O == t;
                return g.getCell(this.x, this.y + 1).getCategory() == t;
            
            case Ouest:
                if (this.x == 0)
                    return Category.O == t;
                return g.getCell(this.x - 1, this.y).getCategory() == t;
        }
        
        return false;

    }

    public boolean eval_dir(DirRelative dir) {
        return this.direction == RelativeToAbsolute(dir);
    }

    public boolean eval_closest(Category c, DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval_closest'");
    }

    //abstract boolean do(...);
    public abstract boolean do_move(Entity e, DirRelative dir);
    public abstract boolean do_egg(Entity e);
    public abstract boolean do_pick(Entity e);

    public abstract boolean do_turn(Entity e, DirRelative dir);

    public abstract boolean do_wait(Entity e);
    
    
    public abstract void paint(Graphics graphics, int x, int y, int width, int height);
    public abstract void tick(long elapsed);

}
