package info3.game.model.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.IGrille;
import info3.game.model.cellType;

public class Door extends Entity {
    Key k;
    boolean opened;
    BufferedImage closedImage;
    BufferedImage openedImage;

    public Door(IGrille g, Automate a, int x, int y, Key k) {
        super(g);
        this.x = x;
        this.y = y;
        this.a = a;
        this.k = k;
        g.getCell(x, y).setEntity(this);
        opened = false;
        try {
            BufferedImage[] images = Grille.loadSprite("resources/door.png", 1, 2);
            if (images != null && images.length == 2) {
                closedImage = images[1];  // Right image is closed
                openedImage = images[0];  // Left image is open
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Door;
    }

    @Override
    public char getCategory() {
        return Category.D;
    }

    @Override
    public boolean do_move(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_egg(Entity e) {
        throw new UnsupportedOperationException("Unimplemented method 'do_egg'");
    }

    @Override
    public boolean do_pick(Entity e) {
        open(); // Check if the key is picked and open the door
        return opened;
    }

    @Override
    public boolean eval_closest(char c, Direction dir) {
        for (int i = 0; i < g.getRows(); i++) {
            for (int j = 0; j < g.getCols(); j++) {
                Entity entity = g.getCell(i, j).GetEntity();
                if (entity != null && entity.getCategory() == c) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean do_pop(Entity e) {
        throw new UnsupportedOperationException("Unimplemented method 'do_pop'");
    }

    @Override
    public boolean do_wizz(Entity e) {
        if (opened) {
            g.getCell(x, y).resetEntity();
            return true;
        }
        return false;
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }

    @Override
    public boolean do_hit(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_hit'");
    }

    public void open() {
        if (k.isPicked()) {
            opened = true;
        }
    }

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        if (opened) {
            if (openedImage != null) {
                graphics.drawImage(openedImage, x, y, width, height, null);
            }
        } else {
            if (closedImage != null) {
                graphics.drawImage(closedImage, x, y, width, height, null);
            }
        }
    }

    @Override
    public void tick(long elapsed) {
        // Doors do not animate, so this method can be left empty or minimal
    }
}
