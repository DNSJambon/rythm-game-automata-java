package info3.game.controller.Conditions;

import info3.game.controller.*;
import info3.game.model.Entities.*;

public class MyDir implements Conditions{

    DirRelative MyDir;

    public MyDir(DirRelative MyDir) {
        this.MyDir = MyDir;
    }

    public boolean eval (Entity e) {
        return e.eval_dir(MyDir);
    }

}
