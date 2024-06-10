package info3.game.controller.Conditions;

import java.awt.event.KeyEvent;

import info3.game.controller.*;
import info3.game.model.Entities.*;

public class Key implements Condition {
    Touche t;
    int key_code;
   

    public Key (Touche t) {
        this.t = t;
    }

    

    public boolean eval (Entity e) {
        key_code=-1;
        

        
    }

}
