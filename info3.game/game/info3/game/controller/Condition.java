package info3.game.controller;

import info3.game.model.Entity;


public interface Condition {


    /*
     * Prend en paramètre une entité et renvoie un booléen une fois le test effectué.
     * Chaque Condition "cond" appel eval_cond sur l'entité "e" pour effectuer le test.
     * Les "eval_cond" sont propres a chaque entité et implementés par ces dernières.
     */
    public boolean eval (Entity e);
    
}