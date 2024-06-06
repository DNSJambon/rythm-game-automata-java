package info3.game.controller;

import info3.game.model.Entity;

public class Move extends Action {

    public boolean exec(Entity e) {
        return e.do_move(e);
    }
}
