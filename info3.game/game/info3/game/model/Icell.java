package info3.game.model;

import info3.game.model.Entities.Entity;

public interface Icell {
    public cellType getType();

    public char getCategory();

    public void setEntity(Entity e);

    public void reset();

    public int getRow();

    public int getCol();


}
