package controller;

import model.Entity;

public class Move extends Action {

    public boolean exec(Entity e) {
        return e.do_move(e);
    }
}
