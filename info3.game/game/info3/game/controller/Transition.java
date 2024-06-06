package info3.game.controller;

/*
 * Transition entre deux états.
 * Une transition est caractérisée par une action, une condition, un état initial et un état final.
 */
public class Transition {
    Action act;
    Condition cond;
    int init;
    int end;

    /*
     * Prend en paramètre une action, une condition, un état initial et un état final.
     */

    public Transition(Action a,Condition c, int i, int e){
        this.act=a;
        this.cond = c;
        this.init = i;
        this.end = e;
    }
}