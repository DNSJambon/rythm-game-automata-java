package info3.game.controller;
import info3.game.model.Entity;

public abstract class Action {

    public Entity e_or;
    public Entity e_dest;
    
    public abstract boolean exec (Entity e);
    
}