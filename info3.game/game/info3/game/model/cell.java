package info3.game.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcraft.jogg.Buffer;

import info3.game.model.Entities.Entity;

public class cell implements Icell {

    Grille grid;
    
    Entity e;
    int vide;
    int x, y;

    int[] sols = { 0, 21 };
    int index_sol = 0;
    
    
    
    public cell(Grille g, int x, int y) throws IOException {
        grid = g;
        this.x = x;
        this.y = y;
        vide = 1;
    }

    public cell(Grille g, int x, int y, Entity e) {
        this.x = x;
        this.y = y;
        this.e = e;
        vide = 0;
    }

    public cellType getType() {
        if (vide == 1) {
            return cellType.Vide;
        } else {
            return e.getType();
        }
    }

    public void setEntity(Entity e) {
        this.e = e;
        vide = 0;
    }

    public void reset() {
        vide = 1;
    }

    public int getRow() {
        return y;
    }

    public int getCol() {
        return x;
    }

    
  
    public void paint(Graphics g, int width, int height) {
        if (vide == 0) {
            e.paint(g, x * width, y * height, width, height);
        }
    }


}
