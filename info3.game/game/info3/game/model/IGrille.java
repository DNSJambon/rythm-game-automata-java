package info3.game.model;

import java.awt.image.BufferedImage;

public interface IGrille {
    
    public Icell getCell(int col, int row);

    public int getRows();

    public int getCols();

    public char getTouche();

    public BufferedImage getImage(int index);
}
