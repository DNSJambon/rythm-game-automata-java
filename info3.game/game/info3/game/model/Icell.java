package info3.game.model;

import info3.game.model.Entities.Entity;

public interface Icell {
    public cellType getType();

    public char getCategory();

    public Entity GetEntity();

    public void setEntity(Entity e);

    public void setTrap(Entity e);

    public void resetall();

    public void resetEntity();

    public void resetP2();

    public void resetTrap();

    public int getRow();

    public int getCol();

    public void setP2(Entity e);


}
