package stateMachine;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class State<S> {
	private boolean finalState = false;
	
	private HashMap<S, Transition<S>> transitions = new HashMap<S, Transition<S>>();
	private Transition<S> elseTransition = null;
	
	public State(boolean isFinal) {
		this.finalState = isFinal;
	}
	
	public State(HashMap<S, Transition<S>> transitions, boolean isFinal) {
		this.finalState = isFinal;
		this.transitions = transitions;
	}
	
	public void addTransition(S symbol, Transition<S> transition) {
		transitions.put(symbol, transition);
	}
	
	public void setElseTransition(Transition<S> transition) {
		this.elseTransition = transition;
	}

	public Optional<State<S>> transitionWith(S symbol) {
		Transition<S> nextStateTransition = transitions.get(symbol);
		
		if(Objects.isNull(nextStateTransition))
			nextStateTransition = elseTransition;
		if(Objects.isNull(nextStateTransition))
			return Optional.empty();
		
		return Optional.ofNullable(nextStateTransition.getState());
	}

	public boolean isFinal() {
		return finalState;
	}
	
}
