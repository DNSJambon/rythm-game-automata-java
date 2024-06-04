package controller;

import model.Entity;

public class Turn implements Action{

    @Override
    public boolean exec(Entity e,Direction dir) {
        e.do_pick(e);
        return true;
    }
    
}
