package info3.game.model.Entities;

import info3.game.controller.Direction;
import info3.game.model.Grille;

public abstract class Ennemi extends Entity{

    public Ennemi(Grille g, int x, int y) {
        super(g);
        etat_courant = 0;
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);
    }

}
