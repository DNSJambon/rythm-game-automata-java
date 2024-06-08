package info3.game.controller.Conditions;

import info3.game.controller.DirRelative;
import info3.game.model.cellType;
import info3.game.model.Entities.Entity;

/* Fait un test sur une cellule particulière du modèle pour savoir si une entité particulière s'y trouve.
 */

public class Cell implements Condition{

    DirRelative dir;
    cellType cat;

    public Cell(DirRelative dir, cellType cat) {
        this.dir = dir;
        this.cat = cat;
    }

    public boolean eval(Entity e) {
        return e.eval_cell(e, dir, cat);
    }
    
}
