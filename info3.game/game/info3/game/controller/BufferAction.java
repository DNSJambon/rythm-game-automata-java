package info3.game.controller;

public class BufferAction {
    Action action[];
    int size;
    int index;

    public BufferAction(int size) {
        this.size = size;
        this.action = new Action[size];
        this.index = 0;
    }

    public void addAction(Action action) {
        if (index < size) {
            this.action[index] = action;
            index++;
        }
    }

    public void resolve() {
        for (int i = 0; i < index; i++) {
            action[i].exec(action[i].e_or);
        }
        index = 0;
    }
}