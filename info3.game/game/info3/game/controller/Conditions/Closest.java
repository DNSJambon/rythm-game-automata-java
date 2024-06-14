package info3.game.controller.Conditions;

import info3.game.controller.*;
import info3.game.model.Category;
import info3.game.model.Entities.*;

public class Closest implements Conditions{
    char c;
    Direction d;

    public Closest(char c, Direction d) {
        this.c = c;
        this.d = d;
    }

    public boolean eval (Entity e) {
        return e.eval_closest(c, d);
    }
}
