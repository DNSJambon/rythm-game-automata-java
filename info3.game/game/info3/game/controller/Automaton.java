package info3.game.controller;
import info3.game.model.Entities.Entity;

public class Automaton {

    int status;
    Transition[] Trans;

    public Automaton(int s,Transition[] T){
        this.Trans=T;
        this.status=s;
    }
    
    /* 
     * Prend en paramètre une entité et un buffer d'action
     * Pour chaque transition (Attention, l'odre dans la liste de transition compte), regarde si l'etat courant de l'entité correspond à l'etat initial de la transition
     * Si c'est le cas, regarde si la condition de la transition est vérifiée
     * Si c'est le cas, change l'etat de l'entité et ajoute l'action de la transition au buffer
     */
    public boolean step_A(Entity e,BufferAction buff){
        int i=0;
        while (this.Trans[i]!=null) {
            if (e.etat_courant==this.Trans[i].init) {
                if (this.Trans[i].cond.eval(e)) {
                    e.etat_courant = Trans[i].end;
                    buff.addAction(this.Trans[i].act);
                    return true;
                }
            }
            i++;
            if (i== Trans.length) {
                return false;
            }
        }
        return true;
        
    }
}
