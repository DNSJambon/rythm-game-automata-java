package info3.game.controller;

import info3.game.model.Entity;

/*
 * Action qui change la direction absolue de l'entité.
 */

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
