package controller;

import model.Entity;

public class Turn extends Action {

    DirRelative dir;
    
    public Turn(DirRelative dir) {
        this.dir = dir;
    }

    @Override
    public boolean exec(Entity e) {
        return e.do_turn(e, dir);
    }
    
}
