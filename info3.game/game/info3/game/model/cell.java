package info3.game.model;

public class cell implements Icell{

    Entity e;
    int vide;
    int x, y;   
    
    public cell(int x, int y) {
        this.x = x;
        this.y = y;
        vide = 1;
    }

    public cell(int x, int y, Entity e) {
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


}
