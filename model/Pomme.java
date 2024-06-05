package model;

import controller.*;

public class Pomme extends Entity {
    
    public Pomme(Grille g, Automaton a) {
        super(g);
        this.a = a;
        etat_courant = 0;

        this.x = g.getCols() - 1;
        this.y = 1;
        g.getCell(x,y).setEntity(this);


    }

    @Override
    public cellType getType() {
        return cellType.Apple;
    }

    @Override
    public boolean eval_cell(Entity e, DirRelative dir, cellType type) {
        switch (dir) {
            case soi:
                return g.getCell(x, y).getType() == type;
            default:
                return false;
        }
    }

    @Override
    public boolean do_move(Entity e) {
        g.getCell(x, y).reset();
        x = (x + 1) % g.getRows();
        y = (y + 1) % g.getCols();
        g.getCell(x, y).setEntity(this);
        return true;
    }

    @Override
    public boolean do_egg(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");

    }

    @Override
    public boolean do_pick(Entity e) {
        cell c = ((Grille) g).randomCell_libre();
        x = c.getCol();
        y = c.getRow();
        g.getCell(x, y).setEntity(this);
        return true;
    }

    @Override
    public boolean do_turn(Entity e,DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }
    
}
