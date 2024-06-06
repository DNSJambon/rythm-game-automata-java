package controller;
import model.Entity;

public abstract class Action {

    public Entity e_or;
    public Entity e_dest;
    
    public abstract boolean exec (Entity e);
    
}