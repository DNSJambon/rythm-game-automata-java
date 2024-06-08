package info3.game.controller.Actions;
import info3.game.model.Entities.Entity;

public abstract class Action {

    public Entity e_or;  /* Ce champs contient l'entité qui fait l'action pour permettre son appel dans le buffer */
    public Entity e_dest;   /* Ce champs contient l'entité qui est la cible de l'action */
    
    /*
     * Prend en paramètre une entité et renvoie un booléen pour savoir si l'action a été effectuée sans bug
     * Chaque Action "act" appel do_act sur l'entité "e" pour effectuer l'action.
     * Les "do_act" sont propres a chaque entité et implementés par ces dernières.
     */
    public abstract boolean exec (Entity e);
    
}