package info3.game.controller.Conditions;

import info3.game.model.Entities.*;

public class Key implements Condition {
    char c;
    int key_code;
   

    public Key (char c) {
        this.c = c;
    }

    public boolean eval (Entity e) {
        key_code=-1;
        return e.getGrille().getTouche()==this.c;
    }

}
