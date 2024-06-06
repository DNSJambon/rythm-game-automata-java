package info3.game.controller;

import info3.game.model.Entity;

public class Pick implements Action {
    
        public boolean exec(Entity e) {
            e.do_pick(e);
            return true;
        }
    
}
