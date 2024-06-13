package info3.game.controller;

import info3.game.model.Entities.Entity;
import info3.game.controller.Modes;

public class Act {
    Entity owner;
    Modes mode;

    public Act(Entity owner, Modes mode) {
        this.owner = owner;
        this.mode = mode;
    }

    public void resolve() {
        if (mode != null) {
            if (mode.getTrans(owner) != null) {
                mode.getTrans(owner).GetAct().exec(owner);
            }
        }
    }


}
