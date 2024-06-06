package controller;

import model.Entity;

public class Egg extends Action {

    public boolean exec(Entity e) {
        return e.do_egg(e);
    }
}
