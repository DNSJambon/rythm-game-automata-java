package info3.game.controller;

import java.util.LinkedList;

import info3.game.model.*;



public class Control {

    LinkedList<Entity> entities;
    BufferAction buffer;

    public Control() {
        entities = new LinkedList<Entity>();
        buffer = new BufferAction(3);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void step() {
        for (Entity e : entities) {
            e.step(buffer);
        }
        buffer.resolve();
    }





    


    
}
