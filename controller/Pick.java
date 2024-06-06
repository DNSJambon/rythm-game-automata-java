package controller;

import model.Entity;

public class Pick extends Action {
    
    public boolean exec(Entity e) {
        return e.do_pick(e);
    }
}
