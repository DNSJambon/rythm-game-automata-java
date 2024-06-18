package info3.game.controller.Actions;

import info3.game.model.Entities.Entity;

public class Got extends Action {
    Entity e;
    char cat;

    public Got(char cat) {
        this.cat = cat;
    }
    public Got(Entity e,char cat) {
        this.e=e;
        this.cat = cat;
    }

    public boolean exec(Entity e) {
        return e.do_got(e,cat);
    }
    
}
