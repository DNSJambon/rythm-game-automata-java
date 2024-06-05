package controller;

import model.Entity;

public class Pick implements Action {
    
        public boolean exec(Entity e) {
            e.do_pick(e);
            return true;
        }
    
}
