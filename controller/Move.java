package controller;

import model.Entity;

public class Move implements Action {

    public boolean exec(Entity e,Direction dir) {
        e.do_move(e,dir);
        return true;
    }
}
