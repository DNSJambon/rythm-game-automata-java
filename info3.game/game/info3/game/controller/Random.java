package info3.game.controller;
import info3.game.model.Entity;   

public class Random implements Condition {
    
    int pourcentage;

    public Random(int pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public boolean eval(Entity e) {
        return Math.random()*100<pourcentage;
    }
    
}
