package info3.game.model;

import java.awt.image.BufferedImage;

import info3.game.model.Entities.Entity;

public interface IGrille {
    
    public Icell getCell(int col, int row);

    public int getRows();

    public int getCols();

    public char getTouche();

    public char getTouche2();

    public Entity getMainEntity();

    public void addEntity(Entity entity);

    public void removeEntity(Entity entity);
}
