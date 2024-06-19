package info3.game.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import gal.ast.AST;
import gal.ast.Actions;
import gal.ast.Automaton;
import gal.ast.Behaviour;
import gal.ast.BinaryOp;
import gal.ast.Category;
import gal.ast.Condition;
import gal.ast.Direction;
import gal.ast.FunCall;
import gal.ast.IVisitor;
import gal.ast.Key;
import gal.ast.Mode;
import gal.ast.State;
import gal.ast.Transition;
import gal.ast.UnaryOp;
import gal.ast.Underscore;
import gal.ast.Value;
import info3.game.controller.Actions.*;
import info3.game.controller.Conditions.*;
import info3.game.controller.Conditions.Got;

public class Ast2Automaton implements IVisitor{


    String current_state;
    Automate current_automate;



    List<Automate> automates;

    public Ast2Automaton(List<Automate> automates){
        this.automates = automates;
    }

    // REQUIRED BY INTERFACE IVisitor

	public Object visit(Category cat) {
		return cat.terminal.content;
	}

	public Object visit(Direction dir) {
		return dir.terminal.content;
	}

	public String visit(Key key) {
		return key.terminal.content;
	}

	public Object visit(Value v) {
		return v.value;
	}

	public Object visit(Underscore u) {
		return '_';
	}

	// FUNCALL

	public void enter(FunCall funcall) {
	}

	public void visit(FunCall funcall) {
	}

	public void exit(FunCall funcall) {
	}

	public Object build(FunCall funcall, List<Object> params) {
		switch (funcall.name) {
            case "Closest":
                switch ((String) params.get(1)) {
                    case "N":
                        return new Closest(((String) params.get(0)).charAt(0), info3.game.controller.Direction.Nord);
                    case "S":
                        return new Closest(((String) params.get(0)).charAt(0), info3.game.controller.Direction.Sud);
                    case "E":
                        return new Closest(((String) params.get(0)).charAt(0), info3.game.controller.Direction.Est);
                    case "W":
                        return new Closest(((String) params.get(0)).charAt(0), info3.game.controller.Direction.Ouest);
                }
            case "Key":
                return new Keyc(((String) params.get(0)).charAt(0));
            case "Move":
                switch ((String) params.get(0)) {
                    case "R":
                        return new Move(DirRelative.Droite);
                    case "L":
                        return new Move(DirRelative.Gauche);
                    case "F":
                        return new Move(DirRelative.Devant);
                    case "B":    
                        return new Move(DirRelative.Derriere);
                }
            case "Cell":
                switch ((String) params.get(0)) {
                    case "R":
                        return new Cell(DirRelative.Droite, ((String) params.get(1)).charAt(0));
                    case "L":
                        return new Cell(DirRelative.Gauche, ((String) params.get(1)).charAt(0));
                    case "F":
                        return new Cell(DirRelative.Devant, ((String) params.get(1)).charAt(0));
                    case "B":
                        return new Cell(DirRelative.Derriere, ((String) params.get(1)).charAt(0));
                    case "H":
                        return new Cell(DirRelative.soi, ((String) params.get(1)).charAt(0));
                }

            case "True":
                return new True();
            case "Rest": //repos eternel
                return new Die();
            case "Wait":
                return new Wait();
            case "Egg":
                return new Egg();
            case "Pop":
                return new Pop();
            case "Wizz":
                return new Wizz();
            case "Hit":
                switch ((String) params.get(0)) {
                case "R":
                    return new Hit(DirRelative.Droite);
                case "L":
                    return new Hit(DirRelative.Gauche);
                case "F":
                    return new Hit(DirRelative.Devant);
                case "B":
                    return new Hit(DirRelative.Derriere);
                case "H":
                    return new Hit(DirRelative.soi);
                }
            case "Got" :
                switch ((String) params.get(0)) {
                    case "Power":
                        return new Got((String) params.get(0));
                    case "K":
                        return new Got((char) params.get(0));

                    default :
                        return new Got((char) params.get(0));
                }
            case "Turn":
                switch ((String) params.get(0)) {
                    case "R":
                        return new Turn(DirRelative.Droite);
                    case "L":
                        return new Turn(DirRelative.Gauche);
                    case "B":
                        return new Turn(DirRelative.Derriere);
                    case "F":
                        return new Turn(DirRelative.Devant);
                }
            case "Pick" :
                return new Pick();
            //TODO: Add more cases
        }
        return null;
            
	}


    @Override
    public void enter(BinaryOp binop) {
    }

    @Override
    public void visit(BinaryOp binop) {
    }

    @Override
    public void exit(BinaryOp binop) {
    }

    @Override
    public Binary build(BinaryOp binop, Object left, Object right) {
        Conditions l = (Conditions) left;
        Conditions r = (Conditions) right;
        return new Binary(l, r, binop.operator);
    }


    @Override
    public void enter(UnaryOp unop) {
    }

    @Override
    public void exit(UnaryOp unop) {
    }

    @Override
    public Unary build(UnaryOp unop, Object expression) {
        Conditions expr = (Conditions) expression;
        return new Unary(expr, unop.operator == "!");
    }

    @Override
    public Object visit(State state) {
        current_state = state.name;
        return state.name;
    }

    @Override
    public void enter(Mode mode) {
    }

    @Override
    public void visit(Mode mode) {
        
    }

    @Override
    public void exit(Mode mode) {
    }

    @Override
    public Object build(Mode mode, Object source_state, Object behaviour) {
        return new Modes((String) source_state, (List<Transitions>) behaviour);
    }



    @Override
    public Object visit(Behaviour behaviour, List<Object> transitions) {
        return transitions;
    }


    @Override
    public void enter(Condition condition) {
    }

    @Override
    public void exit(Condition condition) {
    }

    @Override
    public Conditions build(Condition condition, Object expression) {
        return (Conditions) expression;
    }

    @Override
    public void enter(Actions action) {
    }

    @Override
    public void visit(Actions action) {
    }

    @Override
    public void exit(Actions action) {
    }

    @Override
    public Object build(Actions action, String operator, List<Object> funcalls) {
        return funcalls.get(0);
        //TODO: Add more actions
    }

    @Override
    public void enter(Transition transition) {
    }

    @Override
    public void exit(Transition transition) {
    }

    
	@Override
	public Transitions build(Transition transition, Object condition, Object action, Object target_state) {
        Conditions cond = (Conditions) condition;
        Action act = (Action) action;
        String target = (String) target_state;
        return new Transitions(act, cond, current_state, target);
	}

    @Override
    public void enter(Automaton automaton) {
    }

    @Override
    public void exit(Automaton automaton) {
    }

    @Override
    public Object build(Automaton automaton, Object initial_state, List<Object> modes) {
        return new Automate(automaton.name, (String) initial_state, (List<Modes>) (List<?>) modes);
        //TODO: Add more modes
    }

    @Override
    public void enter(AST ast) {
    }

    @Override
    public void exit(AST ast) {
    }

    @Override
    public Object build(AST ast, List<Object> automata) {
        return automata;
    }
    
}
