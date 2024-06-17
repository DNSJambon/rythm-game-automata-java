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

public class Key extends Entity {

    boolean picked;

    public Key(IGrille g, int x, int y, Automate a) {
        super(g);
        this.x = x;
        this.y = y;
        this.a = a;
        g.getCell(x, y).setEntity(this);
        picked = false;

        try {
            m_images = Grille.loadSprite("resources/clef.png", 1, 6);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public cellType getType() {
        return cellType.Key;
    }

    @Override
    public char getCategory() {
        return Category.P;
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
        picked=true;
        return true;//tout c'est bien passé .
    }

    @Override
    public boolean do_pop(Entity e) {
        throw new UnsupportedOperationException("Unimplemented method 'do_pop'");
    }

    @Override
    public boolean do_wizz(Entity e) {
        throw new UnsupportedOperationException("Unimplemented method 'do_wizz'");
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        throw new UnsupportedOperationException("Unimplemented method 'do_turn'");
    }

   @Override
    public void tick(long elapsed) {
        animation_elapsed += elapsed;
        if (animation_elapsed > 200) {
            image_index = (image_index + 1) % 6;
            animation_elapsed = 0;
        }
        
    }


}
