package info3.game.controller;
import java.util.ArrayList;
import java.util.List;

import info3.game.model.Entities.Entity;

public class Automate {

    public String name;
    String status;
    List<Modes> modes;


    public Automate(String name ,String s, List<Modes> m){
        this.name=name;
        this.modes=m;
        this.status=s;
    }

    public String getState(){
        return status;
    }

    public Modes getLastMode(){
        return modes.get(modes.size()-1);

    }
    
    /* 
     * Prend en paramètre une entité et un buffer d'action
     * Pour chaque transition (Attention, l'odre dans la liste de transition compte), regarde si l'etat courant de l'entité correspond à l'etat initial de la transition
     * Si c'est le cas, regarde si la condition de la transition est vérifiée
     * Si c'est le cas, change l'etat de l'entité et ajoute l'action de la transition au buffer
     */
    public boolean step_A(Entity e,BufferAction buff){
        int i=0;
        while (i<modes.size()){
            if (modes.get(i).etat.equals(status)){
                buff.addAct(e,modes.get(i));
                return true;
            }
            i++;
        }
        return false;
    }
}
