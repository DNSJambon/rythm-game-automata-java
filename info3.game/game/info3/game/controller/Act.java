package info3.game.controller;

import info3.game.model.Entities.Entity;
import info3.game.controller.Mode;

public class Act {
    Entity owner;
    Mode mode;

    public Act(Entity owner, Mode mode) {
        this.owner = owner;
        this.mode = mode;
    }

    public void resolve() {
        if (mode != null) {
            while (mode..get(i)!=null) {
                if (e.etat_courant.equals(Trans.get(i).init)) {
                    if (Trans.get(i).cond.eval(e)) {
                        e.etat_courant = Trans.get(i).end;
                        buff.addAction(Trans.get(i).act);
                        return true;
                    }
                }
                i++;
                if (i== Trans.size()) {
                    return false;
                }
            }
            return true;
        }
    }


}
