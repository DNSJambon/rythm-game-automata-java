package controller;

import model.Entity;

public class Turn implements Action {

    DirRelative dir;

    public Turn(DirRelative dir) {
        this.dir = dir;
    }

    @Override
    public boolean exec(Entity e) {
        e.do_turn(e, dir);
        return true;
    }
    
}
