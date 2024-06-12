package info3.game.controller.Conditions;

import java.util.LinkedList;

import info3.game.model.Entities.Entity;

public class Et implements Conditions {
    
    LinkedList<Conditions> conditions;
    
    public Et(Conditions... conditions) {
        this.conditions = new LinkedList<Conditions>();
        for (Conditions c : conditions) {
            this.conditions.add(c);
        }
    }

    @Override
    public boolean eval(Entity e) {
        for (Conditions c : conditions) {
            if (!c.eval(e)) {
                return false;
            }
        }
        return true;
    }
    
}
