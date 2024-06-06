package info3.game.model;

public interface Icell {
    public cellType getType();

    public void setEntity(Entity e);

    public void reset();

    public int getRow();

    public int getCol();


}
