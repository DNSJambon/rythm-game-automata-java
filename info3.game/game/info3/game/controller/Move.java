package info3.game.controller;

import info3.game.model.Entity;

public class Move implements Action {

    public boolean exec(Entity e) {
        e.do_move(e);
        return true;
    }
}
