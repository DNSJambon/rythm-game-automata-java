package info3.game.controller.Conditions;

import info3.game.model.Entities.Entity;

public class True implements Condition {
    
    /*
     * Condition toujours vraie.
     */

    @Override
    public boolean eval(Entity e) {
        return true;
    }
    
}
