package info3.game.model.Entities;

import java.awt.Graphics;

import info3.game.controller.DirRelative;
import info3.game.controller.Direction;
import info3.game.model.Category;
import info3.game.model.Grille;
import info3.game.model.cellType;

public class Player2 extends Entity {

    public Player2(Grille g, int x, int y) {
        super(g);
        etat_courant = 0;
        direction = Direction.Est;
        this.x = x;
        this.y = y;
        g.getCell(x, y).setP2(this);
    }

    @Override
    public cellType getType() {
        return cellType.Player2;
    }

    @Override
    public char getCategory() {
        return Category.C;
    }

    public boolean do_move(Entity e, DirRelative dir) {
        switch (dir) {
            case Devant:
                this.y--;
                g.getCell(this.x,this.y+1).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Nord;
                return true;
            case Derriere:
                this.y++;
                g.getCell(this.x,this.y-1).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Sud;
                return true;
            case Droite:
                this.x++;
                g.getCell(this.x-1,this.y).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Est;
                return true;
            case Gauche:
                this.x--;
                g.getCell(this.x+1,this.y).resetEntity();
                g.getCell(this.x,this.y).setEntity(this);
                direction = Direction.Ouest;
                return true;
            default:
                return false;
        }
    }


    @Override
    public boolean do_egg(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_egg'");
    }

    @Override
    public boolean do_pick(Entity e) {
        return true;
    }

    @Override
    public boolean do_pop(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_wizz(Entity e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'do_move'");
    }

    @Override
    public boolean do_turn(Entity e, DirRelative dir) {
        switch (e.direction) {
            case Nord:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Est;
                    case Gauche:
                        e.direction=Direction.Ouest;
                    case Derriere:
                        e.direction=Direction.Sud;
                    default:
                        return false;
                }
            case Sud:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Ouest;
                    case Gauche:
                        e.direction=Direction.Est;
                    case Derriere:
                        e.direction=Direction.Nord;
                    default:
                        return false;
                }
            case Est:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Sud;
                    case Gauche:
                        e.direction=Direction.Nord;
                    case Derriere:
                        e.direction=Direction.Ouest;
                    default:
                        return false;
                }
            case Ouest:
                switch (dir) {
                    case Droite:
                        e.direction=Direction.Nord;
                    case Gauche:
                        e.direction=Direction.Sud;
                    case Derriere:
                        e.direction=Direction.Est;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    @Override
    public void paint(Graphics graphics, int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paint'");
    }

    @Override
    public void tick(long elapsed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }
    

}
