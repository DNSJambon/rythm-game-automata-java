package info3.game.controller.Conditions;


import info3.game.model.Entities.Entity;

public class Got implements Conditions {
    int i;
    String str;
    char cat;

    public Got(String s,int a) {
        this.i=a;
        this.str=s;
    }

    public Got(String s) {
        this.i=0;
        this.str=s;
    }

    public Got(char c) {
        this.cat=c;
    }

    public boolean eval (Entity e) {
        if (this.str.equals("Power")) {
            return e.eval_got_power(e,i);
        }
        else {
            return e.eval_got(e,cat);
        }
    }

}
