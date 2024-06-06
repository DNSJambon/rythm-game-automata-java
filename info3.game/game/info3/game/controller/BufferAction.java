package info3.game.controller;

public class BufferAction {
    /* ici on construit un buffer d'action pour permettre au moteur de resoudre toutes les actions en meme temps 
    (et donc garder le "rythme" ou la "synchroniqation") */
    Action action[];
    int size;
    int index;

    /* Le buffer doit avoir autant d'action que d'entité qui existe sur le moteur */

    public BufferAction(int size) {
        this.size = size;
        this.action = new Action[size];
        this.index = 0;
    }

    /* Ajoute une action au buffer */
    public void addAction(Action action) {
        if (index < size) {
            this.action[index] = action;
            index++;
        }
    }

    /* Resout toutes les actions d'un coup */
    public void resolve() {
        for (int i = 0; i < index; i++) {
            action[i].exec(action[i].e_or);
        }
        index = 0;
    }
}