package controller;

import model.Entity;

public class Turn implements Action{

    @Override
    public boolean exec(Entity e,DirRelative dir) {
        e.do_turn(e, dir);
        return true;
    }
    
}
