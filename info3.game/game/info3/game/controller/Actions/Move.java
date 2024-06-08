package info3.game.controller.Actions;

import info3.game.controller.DirRelative;
import info3.game.model.Entities.Entity;

/*
 * Action de déplacement, toujours dans la direction de l'entité.
 */

public class Move extends Action {

    DirRelative dir;

    public Move(DirRelative dir) {
        this.dir = dir;
    }

    public boolean exec(Entity e) {
        return e.do_move(e, dir);
    }
}
