package info3.game.model;

import java.awt.image.BufferedImage;

import info3.game.model.Entities.Entity;

public interface IGrille {
    
    public Icell getCell(int col, int row);

    public int getRows();

    public int getCols();

    public char getTouche();

    public Entity getMainEntity();

    public BufferedImage getImage(int index);
}
