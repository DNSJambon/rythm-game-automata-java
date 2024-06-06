package info3.game.controller;
import info3.game.model.Entity;

public class Automaton {

    int status;
    Transition[] Trans;

    public Automaton(int s,Transition[] T){
        this.Trans=T;
        this.status=s;
    }
    
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
