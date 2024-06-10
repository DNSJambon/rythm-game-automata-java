package info3.game.controller.Conditions;

import info3.game.controller.*;
import info3.game.model.Category;
import info3.game.model.Entities.*;

public class Closest implements Condition{
    Category c;
    DirRelative d;

    public boolean eval (Entity e) {
        return e.eval_closest(c, d);
    }
}
