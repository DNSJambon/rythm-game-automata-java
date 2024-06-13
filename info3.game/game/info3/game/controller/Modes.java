package info3.game.controller;

import java.util.List;

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

    public Transitions getTrans(){
        return null;
    }


}
