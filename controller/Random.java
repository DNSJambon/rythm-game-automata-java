package controller;

public class Random implements Condition {
    
    int 

    @Override
    public boolean eval(Entity e) {
        return Math.random() > 0.5;
    }
    
}
