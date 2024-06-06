package info3.game.controller;

import info3.game.model.Entity;

public class True implements Condition {
    


    @Override
    public boolean eval(Entity e) {
        return true;
    }
    
}
