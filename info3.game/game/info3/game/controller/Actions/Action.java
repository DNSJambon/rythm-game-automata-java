package info3.game.controller.Actions;
import info3.game.model.Entities.Entity;

public interface Action {
    
    /*
     * Prend en paramètre une entité et renvoie un booléen pour savoir si l'action a été effectuée sans bug
     * Chaque Action "act" appel do_act sur l'entité "e" pour effectuer l'action.
     * Les "do_act" sont propres a chaque entité et implementés par ces dernières.
     */
    public boolean exec (Entity e);
    
}