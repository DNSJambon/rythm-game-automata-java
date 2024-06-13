package info3.game.controller;

import info3.game.model.Entities.Entity;
import info3.game.controller.Mode;

public class BufferAction {
    /* ici on construit un buffer d'action pour permettre au moteur de resoudre toutes les actions en meme temps 
     * (et donc garder le "rythme" ou la "synchronisation").
    */
    Act act[];
    int size;
    int index;

    /* Prend en paramètre la taille du buffer a construire.
     * Le buffer doit avoir autant de mode que d'entité qui existe sur le moteur ou qu'il y a a faire.
     * Il est possible de faire un buffer plus grand que le nombre d'entité, mais pas plus petit car des entités perdarit alors leurs actions.
    */

    public BufferAction(int size) {
        this.size = size;
        this.act = new Act[size];
        this.index = 0;
    }

    /* Ajoute un mode au buffer.
     * Prend en paramètre un mode.
     * Si mode=null, buffer inchangé.
     * Si buffer plein, action non ajoutée.
    */
    public void addAct(Entity e, Mode mode) {
        if (mode != null) {
            if (index < size) {
                this.act[index] = new Act(e, mode);
                index++;
            }
        }
    }

    /* Resout toutes les actions du buffer.
     * pour chaque mode enregistré, On resout l'action.
     * 
    */

    public void resolve() {
        for (int i = 0; i < index; i++) {
            if (this.act!=null) {
                this.act[i].resolve();
            }
        }
        index = 0;
    }
}