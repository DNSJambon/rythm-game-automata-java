package info3.game.controller.Conditions;

import java.util.LinkedList;

import info3.game.model.Entities.Entity;

public class Et implements Condition {
    
    LinkedList<Condition> conditions;
    
    public Et(Condition... conditions) {
        this.conditions = new LinkedList<Condition>();
        for (Condition c : conditions) {
            this.conditions.add(c);
        }
    }

    @Override
    public boolean eval(Entity e) {
        for (Condition c : conditions) {
            if (!c.eval(e)) {
                return false;
            }
        }
        return true;
    }
    
}
