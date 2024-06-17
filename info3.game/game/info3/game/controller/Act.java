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
                Transitions Trans=mode.getTrans(owner);
                mode.getTrans(owner).GetAct().exec(owner);
                owner.etat_courant=Trans.GetEnd();
                
            }
        }
    }


}
