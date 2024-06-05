package controller;

import model.Entity;

public class True implements Condition {
    


    @Override
    public boolean eval(Entity e) {
        return true;
    }
    
}
