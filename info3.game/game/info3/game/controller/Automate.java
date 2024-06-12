package info3.game.controller;
import java.util.ArrayList;
import java.util.List;

import info3.game.model.Entities.Entity;

public class Automate {

    String name;
    public String status;
    List<Transitions> Trans;


    public Automate(String name ,String s, List<Transitions> T){
        this.name=name;
        this.Trans=T;
        this.status=s;
    }

    public Automate(String s, Transitions[] T){
        this.name="Automate";
        this.status=s;
        this.Trans = new ArrayList<Transitions>();
        for (int i=0;i<T.length;i++){
            Trans.add(T[i]);
        }
    }

    public Transitions getLastTrans(){
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
            if (e.etat_courant.equals(Trans.get(i).init)) {
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
