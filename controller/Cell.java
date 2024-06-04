package controller;

import model.Entity;
import model.cellType;

public class Cell implements Condition{

    Direction dir;
    cellType cat;

    public Cell(Direction dir, cellType cat) {
        this.dir = dir;
        this.cat = cat;
    }

    public boolean eval(Entity e) {
        return e.eval_cell(e, dir, cat);
    }
    
}
