package info3.game.controller;
import java.util.ArrayList;
import java.util.List;

import info3.game.model.Entities.Entity;

public class Automate {

    int status;
    List<Transition> Trans;

    public Automate(int s, List<Transition> T){
        this.Trans=T;
        this.status=s;
    }

    public Automate(int s, Transition[] T){
        this.status=s;
        this.Trans = new ArrayList<Transition>();
        for (int i=0;i<T.length;i++){
            Trans.add(T[i]);
        }
    }

    public Transition getLastTrans(){
        return Trans.get(Trans.size()-1);

    }
    
    /* 
     * Prend en paramètre une entité et un buffer d'action
     * Pour chaque transition (Attention, l'odre dans la liste de transition compte), regarde si l'etat courant de l'entité correspond à l'etat initial de la transition
     * Si c'est le cas, regarde si la condition de la transition est vérifiée
     * Si c'est le cas, change l'etat de l'entité et ajoute l'action de la transition au buffer
     */
    public boolean step_A(Entity e,BufferAction buff){
        int i=0;
        while (Trans.get(i)!=null) {
            if (e.etat_courant==Trans.get(i).init) {
                if (Trans.get(i).cond.eval(e)) {
                    e.etat_courant = Trans.get(i).end;
                    buff.addAction(Trans.get(i).act);
                    return true;
                }
            }
            i++;
            if (i== Trans.size()) {
                return false;
            }
        }
        return true;
        
    }
}
