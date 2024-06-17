package info3.game.controller.Conditions;

import info3.game.model.Category;
import info3.game.model.Entities.Entity;

public class Got implements Conditions {
    int i;
    String str;
    Category cat;

    public Got(int a) {
        this.i=a;
    }

    public boolean eval (Entity e) {
        if (this.str=="Power") {
            return e.eval_got_power(e,i);
        }
        else {
            return e.eval_got(e,cat);
        }
    }

}
