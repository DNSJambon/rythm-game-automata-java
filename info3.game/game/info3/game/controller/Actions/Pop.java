package info3.game.controller.Actions;

import info3.game.model.Entities.Entity;

public class Pop extends Action{

    public boolean exec(Entity e) {
        return e.do_wizz(e);
    }

}
