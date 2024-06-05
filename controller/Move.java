package controller;

import model.Entity;

public class Move implements Action {

    public boolean exec(Entity e,DirRelative dir) {
        e.do_move(e);
        return true;
    }
}
