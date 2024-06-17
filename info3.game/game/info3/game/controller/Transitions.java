package info3.game.controller;

import info3.game.controller.Actions.Action;
import info3.game.controller.Conditions.Conditions;

/*
 * Transition entre deux états.
 * Une transition est caractérisée par une action, une condition, un état initial et un état final.
 */
public class Transitions {
    Action act;
    Conditions cond;
    String init;
    String end;

    /*
     * Prend en paramètre une action, une condition, un état initial et un état final.
     */

    public Transitions(Action a,Conditions c, String i, String e){
        this.act=a;
        this.cond = c;
        this.init = i;
        this.end = e;
    }

    public Transitions(Action a,Conditions c, int i, int e){
        this.act=a;
        this.cond = c;
        this.init = Integer.toString(i);
        this.end = Integer.toString(e);
    }

    public Action GetAct() {
        return this.act;
    }

    public String GetInit() {
        return this.init;
    }

    public String GetEnd() {
        return this.end;
    }

    public Conditions GetCond() {
        return this.cond;
    }
}