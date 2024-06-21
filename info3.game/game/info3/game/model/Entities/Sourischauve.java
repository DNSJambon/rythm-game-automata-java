package info3.game.model.Entities;

import java.io.IOException;

import info3.game.controller.*;
import info3.game.model.*;

public class Sourischauve extends Ennemi {

    public Sourischauve(Grille grille, int col, int row, Automate a) {
        super(grille,col,row);
        this.a = a;
        etat_courant = a.getState();
        life = 2;
        direction = Direction.Est;
        this.x = col;
        this.y = row;
        grille.getCell(col, row).setEntity(this);
        
        try {
            m_images = Grille.loadSprite("resources/sourichauve.png", 2, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public cellType getType() {
        return cellType.Sourischauve;
    }

   

    


}
