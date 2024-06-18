package info3.game.model.Entities;

import java.awt.Graphics;
import java.io.IOException;

import info3.game.controller.Automate;
import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.IGrille;
import info3.game.model.cellType;
import java.awt.image.BufferedImage;

public class Door extends Entity {
    Direction DirClosest;
    Key k;
    boolean opened;

    public Door(IGrille g, Automate a, int x, int y, Key k) {
        super(g);
        this.x = x;
        this.y = y;
        this.a = a;
        this.k = k;
        g.getCell(x, y).setEntity(this);
        opened = false;
        try {
            m_images = Grille.loadSprite("resources/door.png", 1, 1);
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
        if (k.isPicked()) {
            opened = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean eval_closest(char c, Direction dir) {
        int xverif = (this.g.getMainEntity().getX()) - this.x;
        int yverif = this.g.getMainEntity().getY() - this.y;
        if (xverif == 0) {
            xverif = g.getCols() + 1;
        } else if (yverif == 0) {
            yverif = g.getRows() + 1;
        }
        if (Math.abs(xverif) <= Math.abs(yverif)) {
            if (xverif > 0) {
                DirClosest = Direction.Est;
            } else {
                DirClosest = Direction.Ouest;
            }
        } else if (Math.abs(xverif) > Math.abs(yverif)) {
            if (yverif > 0) {
                DirClosest = Direction.Sud;
            } else {
                DirClosest = Direction.Nord;
            }
        }
        return DirClosest == dir;
    }

    @Override
    public boolean do_pop(Entity e) {
        throw new UnsupportedOperationException("Unimplemented method 'do_pop'");
    }

    @Override
    public boolean do_wizz(Entity e) {
        if (opened) {
            // g.getCell(x, y).resetEntity();
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
    // public void paint(Graphics g) {
    //     if (opened) {
    //         if (m_images[1] != null) {
    //             g.drawImage(m_images[1], x, y, null);
    //         }
    //     } else {
    //         if (m_images[0] != null) {
    //             g.drawImage(m_images[0], x, y, null);
    //         }
    //     }
    // }
}
