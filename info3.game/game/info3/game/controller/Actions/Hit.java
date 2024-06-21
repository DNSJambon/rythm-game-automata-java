package info3.game.controller.Actions;

import info3.game.controller.DirRelative;
import info3.game.model.Entities.Entity;

/*
 * Hit action, always in the direction of the entity.
 */

public class Hit implements Action {

    DirRelative dir;

    public Hit(DirRelative dir) {
        this.dir = dir;
    }

    public boolean exec(Entity e) {
        return e.do_hit(e, dir);
    }
}