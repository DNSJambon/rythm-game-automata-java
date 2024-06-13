package info3.game.controller;

import info3.game.model.Entities.Entity;
import info3.game.controller.Actions.Action;
import info3.game.controller.Conditions.Conditions;

public class Act {
    Entity owner;
    Mode mode;

    public Act(Entity owner, Action action, Conditions cond) {
        this.owner = owner;
        this.action = action;
        this.cond = cond;
    }

}
