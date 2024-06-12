package info3.game.controller.Conditions;

import info3.game.controller.*;
import info3.game.model.Category;
import info3.game.model.Entities.*;

public class Closest implements Conditions{
    Category c;
    DirRelative d;

    public Closest(Category c, DirRelative d) {
        this.c = c;
        this.d = d;
    }

    public boolean eval (Entity e) {
        return e.eval_closest(c, d);
    }
}
