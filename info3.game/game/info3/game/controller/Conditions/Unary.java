package info3.game.controller.Conditions;

import info3.game.model.Entities.Entity;

/**
 * Unary
 */
public class Unary implements Conditions {

   
    private boolean notOp = false;

    private Conditions m_condition;

    public Unary(Conditions condition) {
        m_condition = condition;
    }

    public Unary(Conditions condition, boolean notOp) {
        m_condition = condition;
        this.notOp = notOp;
    }

    @Override
    public boolean eval(Entity entity) {
        return notOp ? !m_condition.eval(entity) : m_condition.eval(entity);
    } 
}