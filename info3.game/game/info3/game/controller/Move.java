package info3.game.controller;

import info3.game.model.Entity;

/*
 * Action de déplacement, toujours dans la direction de l'entité.
 */

public class Move extends Action {

    public boolean exec(Entity e) {
        return e.do_move(e);
    }
}
