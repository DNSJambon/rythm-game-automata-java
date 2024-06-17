package info3.game.model.Entities;

import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public abstract class Ennemi extends Entity{
    int life;

    public Ennemi(IGrille g, int x, int y) {
        super(g);
        etat_courant = "0";
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);
    }

    @Override
    public cellType getType() {
        return cellType.Ennemi;
    }

    @Override
    public char getCategory() {
        return Category.E;
    }

    @Override
    public int getLife() {
        return this.life;
    }

    @Override
    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public boolean eval_got_power(Entity e,int i) {
        return e.getLife()>i;
    }

}
