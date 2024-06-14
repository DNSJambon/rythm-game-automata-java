package info3.game.controller.Conditions;

import info3.game.model.Entities.*;

public class Keyc implements Conditions {
    char c;
    int key_code;
   

    public Keyc (char c) {
        this.c = c;
    }

    public boolean eval (Entity e) {
        key_code=-1;
        if (!(e.getGrille().getTouche()==this.c)){
            return e.getGrille().getTouche2()==this.c;
        }
        else {
            return true;
        }
    }

}
