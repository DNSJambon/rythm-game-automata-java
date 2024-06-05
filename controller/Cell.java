package controller;

import model.Entity;
import model.cellType;

public class Cell implements Condition{

    DirRelative dir;
    cellType cat;

    public Cell(DirRelative dir, cellType cat) {
        this.dir = dir;
        this.cat = cat;
    }

    public boolean eval(Entity e) {
        return e.eval_cell(e, dir, cat);
    }
    
}
