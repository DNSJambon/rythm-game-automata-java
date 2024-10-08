package info3.game.model;

import java.awt.Graphics;
import java.io.IOException;
import info3.game.model.Entities.Entity;
import info3.game.model.Entities.Obstacle;


public class cell implements Icell {

    Grille grid;
    Entity[] e;
    int vide;
    int x, y;

    int[] sols = { 0, 21 };
    int index_sol = 0;
    
    
    
    public cell(Grille g, int x, int y) throws IOException {
        grid = g;
        this.x = x;
        this.y = y;
        vide = 1;
        this.e = new Entity[3];
    }

    public cell(Grille g, int x, int y, Entity e) {
        this.x = x;
        this.y = y;
        this.e = new Entity[3];
        this.e[1] = e;
        vide = 0;
    }

    public cellType getType() {
        if (vide == 1) {
            return cellType.Vide;
        } else {
            return e[1].getType();
        }
    }
    public Entity GetEntity() {
        return e[1];
    }

    public char getCategory() {
        if (vide == 1) { 
            if (e[0] != null) 
                return e[0].getCategory();         
            return Category.V;
        } 
        else {
            return e[1].getCategory();
        }
    }

    public void setTrap(Entity e) {
        this.e[0] = e;
        vide = 1;
    }

    public void setEntity(Entity e) {
        this.e[1] = e;
        vide = 0;
    }

    public void setP2(Entity e) {
        this.e[2] = e;
    }

    public void resetall() {
        vide = 1;
    }

    public void resetP2() {
        e[2] = null;

    }

    public void resetEntity() {
        e[1] = null;
        vide=1;
    }

    public void resetTrap() {
        e[0] = null;
    }

    public int getRow() {
        return y;
    }

    public int getCol() {
        return x;
    }
  
    public void paint(Graphics g, int x, int y, int width, int height) {
        if (true) {
            for (int i = 0; i < 3; i++) {
                if (e[i] != null) {
                    e[i].paint(g, x, y, width, height);
                }
            }
        }
    }

    public boolean pas_obstacle() {
        return !(e[1] instanceof Obstacle); // La cellule est libre si elle ne contient pas un obstacle
    }



}
