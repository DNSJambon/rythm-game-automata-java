package info3.game.controller.Actions;
import info3.game.model.Entities.Entity;

public class Wait implements Action {
    
    public boolean exec(Entity e) {
        return e.do_wait(e);
    }
    
}
