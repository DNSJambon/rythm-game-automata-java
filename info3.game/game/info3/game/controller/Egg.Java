package info3.game.controller;

import info3.game.model.Entity;

public class Egg extends Action {

    public boolean exec(Entity e) {
        e.do_egg(e);
        return true;
    }
}
