package info3.game.model.Entities;
import info3.game.controller.Automate;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.cellType;
import java.awt.Graphics;

public class Wall_Breakable extends Entity {

    public Wall_Breakable(Grille g, int x, int y, Automate a) {
        super(g);
        etat_courant = a.getState();
        life = 1;
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setEntity(this);
        this.a = a;
        image_index=0;
        try {
            m_images = Grille.loadSprite("resources/mur_cassable.png", 1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Wall_Breakable;
    }

    @Override
    public char getCategory() {
        return Category.E;
    }


    @Override
    public void tick(long elapsed) {

    }
    
    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(m_images[0], x, y-(height/2), width, height*3/2, null);
    }
}