package info3.game.controller.Conditions;

import info3.game.controller.DirRelative;
import info3.game.model.Entities.Entity;

/* Fait un test sur une cellule particulière du modèle pour savoir si une entité particulière s'y trouve.
 */

public class Cell implements Conditions{

    DirRelative dir;
    char cat;

    public Cell(DirRelative dir, char cat) {
        this.dir = dir;
        this.cat = cat;
    }

    public boolean eval(Entity e) {
        return e.eval_cell(e, dir, cat);
    }
    
}
