package info3.game.controller.Conditions;

import info3.game.model.Entities.Entity;

public class Binary implements Conditions{

    Conditions left;
    Conditions right;
    String operator;

    public Binary(Conditions left, Conditions right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public boolean eval (Entity e) {
        if (operator.equals("&")) 
            return left.eval(e) && right.eval(e);
        else if (operator.equals("/")) 
            return left.eval(e) || right.eval(e);
        else 
            return false;
        
    }


    
}
