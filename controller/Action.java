package controller;
import model.Entity;

public interface Action {

    public boolean exec (Entity e,DirRelative dir);
    
}