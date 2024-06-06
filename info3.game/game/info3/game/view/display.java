package info3.game.view;

import info3.game.model.*;

public class display {
    
    IGrille grille;

    public display(IGrille grille) {
        this.grille = grille;
    }

    public void afficher_grille() {
        //reset console
        System.out.print("\033[H\033[2J");
        for (int i = 0; i < grille.getRows(); i++) {
            for (int j = 0; j < grille.getCols(); j++) {
                switch (grille.getCell(j, i).getType()) {

                    case Apple:
                        System.out.print("A");
                        break;
                    
                    case Snake:
                        System.out.print("S");
                        break;
                    
                    case Obstacle:
                        System.out.print("O");
                        break;
                    
                    default:
                        System.out.print(".");
                        break;            
                    
                }
                System.out.print(" ");
            }

            System.out.println();
        }
        System.out.println();
    }
    
}
