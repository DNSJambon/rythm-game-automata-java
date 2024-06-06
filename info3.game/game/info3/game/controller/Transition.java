package info3.game.controller;

public class Transition {
    Action act;
    Condition cond;
    int init;
    int end;

    public Transition(Action a,Condition c, int i, int e){
        this.act=a;
        this.cond = c;
        this.init = i;
        this.end = e;
    }
}