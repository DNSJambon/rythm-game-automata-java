package info3.game.model.Entities;

import info3.game.controller.*;
import info3.game.controller.Actions.Pick;
import info3.game.controller.Conditions.Cell;
import info3.game.model.Grille;
import info3.game.model.cell;
import info3.game.model.cellType;

public class Pomme extends Entity {
    
    public Pomme(Grille g, Automaton a) {
        super(g);
        this.a = a;
        etat_courant = 0;

        this.x = g.getCols() - 1;
        this.y = 1;
        g.getCell(x, y).setEntity(this);
    }
    
    //Constructeur avec automate par defaut
    public Pomme(Grille g){
        super(g);
        etat_courant = 0;

        this.x = g.getCols() - 1;
        this.y = 1;
        g.getCell(x, y).setEntity(this);

        Transition[] T = new Transition[1];

        Cell c = new Cell(DirRelative.soi, cellType.Snake); 

        Pick p = new Pick();

        T[0] = new Transition(p, c, 0, 0);

        Automaton a = new Automaton(0, T);
        this.a = a;
        p.e_or=this;
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
    public boolean do_move(Entity e, DirRelative dir) {
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

    @Override
    public boolean do_wait(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_wait'");
    }
    
}
