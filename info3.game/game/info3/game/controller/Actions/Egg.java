package info3.game.controller.Actions;

import info3.game.model.Entities.Entity;

public class Egg implements Action {

    public boolean exec(Entity e) {
        e.do_egg(e);
        return true;
    }
}