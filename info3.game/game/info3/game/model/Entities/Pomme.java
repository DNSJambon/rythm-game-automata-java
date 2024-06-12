package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.*;
import info3.game.controller.Actions.Pick;
import info3.game.controller.Conditions.Cell;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.cell;
import info3.game.model.cellType;

public class Pomme extends Entity {
    
    public Pomme(Grille g, Automate a) {
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

        Cell c = new Cell(DirRelative.soi, Category.T); 

        Pick p = new Pick();

        T[0] = new Transition(p, c, 0, 0);

        Automate a = new Automate(0, T);
        this.a = a;
        p.e_or=this;
    }

    @Override
    public cellType getType() {
        return cellType.Apple;
    }
    public char getCategory() {
        return Category.P;
    }

   
    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        g.getCell(x, y).resetEntity();
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
    public boolean do_turn(Entity e,DirRelative dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(g.getImage(360), x, y, width, height, null);
    }

    @Override
    public void tick(long elapsed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }
    
}
