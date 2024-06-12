package info3.game.controller;

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

public class Ast2Automaton implements IVisitor{


    Integer current_source_state;

	/**
	 * /!\ States appear as source and target of transitions.
	 * 
	 * A naive implementation would create distinct copies of the same state: - one
	 * when it is a source, - one when it is a target resulting into disconnected
	 * automaton with floating transitions.
	 * 
	 * SOLUTION We need to build a mapping from State name -->
	 * DoState(id,name,options). Thus, when encountering a state that has already
	 * been stored in the mapping we can ask the mapping what is the id we must use
	 * for that state.
	 */

	private Map<String, State> state_map;

	Integer state_id(State state) {
		State stored_state = state_map.get(state.name);
		if (stored_state != null) {
			return stored_state.id;
		} else {
			state_map.put(state.name, state);
			return state.id;
		}
	}


    List<Automate> automates;

    public Ast2Automaton(List<Automate> automates){
        this.automates = automates;
    }

    // REQUIRED BY INTERFACE IVisitor

	public Object visit(Category cat) {
		return null;
	}

	public Object visit(Direction dir) {
		return null;
	}

	public Object visit(Key key) {
		return null;
	}

	public Object visit(Value v) {
		return null;
	}

	public Object visit(Underscore u) {
		return null;
	}

	// FUNCALL

	public void enter(FunCall funcall) {
	}

	public void visit(FunCall funcall) {
	}

	public void exit(FunCall funcall) {
	}

	public Object build(FunCall funcall, List<Object> params) {
		return null;
	}


    @Override
    public void enter(BinaryOp binop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void visit(BinaryOp binop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void exit(BinaryOp binop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(BinaryOp binop, Object left, Object right) {
        

    }

    @Override
    public void enter(UnaryOp unop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void exit(UnaryOp unop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(UnaryOp unop, Object expression) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public Object visit(State state) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void enter(Mode mode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void visit(Mode mode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void exit(Mode mode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(Mode mode, Object source_state, Object behaviour) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public Object visit(Behaviour behaviour, List<Object> transitions) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void enter(Condition condition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void exit(Condition condition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(Condition condition, Object expression) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public void enter(Actions action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void visit(Actions action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void exit(Actions action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(Actions action, String operator, List<Object> funcalls) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public void enter(Transition transition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void exit(Transition transition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(Transition transition, Object condition, Object action, Object target_state) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public void enter(Automaton automaton) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void exit(Automaton automaton) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(Automaton automaton, Object initial_state, List<Object> modes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public void enter(AST ast) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void exit(AST ast) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public Object build(AST ast, List<Object> automata) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }
    
}
