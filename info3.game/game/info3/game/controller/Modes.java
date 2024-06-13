package info3.game.controller;

import java.util.List;
import info3.game.model.Entities.Entity;

public class Modes {

    String etat;
    List<Transitions> Trans;

    public Modes(String s, List<Transitions> T){
        this.etat=s;
        this.Trans=T;
    }

    public String getEtat(){
        return etat;
    }

    public Transitions getTrans(Entity e){
        for (int i=0;i<=Trans.size();i++){
            if (Trans.get(i).GetCond().eval(e)){
                return Trans.get(i);
            }
        }
        return null;
    }


}
