package stateMachine;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class State<S> {
	private boolean finalState = false;
	
	private HashMap<S, Transition<S>> transitions = new HashMap<S, Transition<S>>();
	private Transition<S> elseTransition = null;

	/**
	 * Constructs the {@link stateMachine.State}
	 * 
	 * @param isFinal if the {@link stateMachine.State} is final
	 */
	public State(boolean isFinal) {
		this.finalState = isFinal;
	}
	
	/**
	 * Constructs the {@link stateMachine.State}
	 * 
	 * @param transitions the {@link stateMachine.Transition} to the next {@link stateMachine.State}
	 * @param isFinal if the {@link stateMachine.State} is final
	 */
	public State(HashMap<S, Transition<S>> transitions, boolean isFinal) {
		this.finalState = isFinal;
		this.transitions = transitions;
	}

	/**
	 * Adds a {@link stateMachine.Transition} for a given symbol 
	 * @param symbol the symbol the {@link stateMachine.Transition} will happen on
	 * @param transition the {@link stateMachine.Transition} to be added to the transitions map
	 */
	public void addTransition(S symbol, Transition<S> transition) {
		transitions.put(symbol, transition);
	}
	
	/**
	 * Adds an else {@link stateMachine.Transition} that will if no transition for a given symbol was found 
	 * 
	 * @param transition the {@link stateMachine.Transition} to be added as the else {@link stateMachine.Transition}
	 */
	public void setElseTransition(Transition<S> transition) {
		this.elseTransition = transition;
	}

	/**
	 * Does the {@link stateMachine.Transition} to the next {@link stateMachine.State}
	 * @param symbol the symbol to be used
	 * @return the next {@link stateMachine.State} if there is one
	 */
	public Optional<State<S>> transitionWith(S symbol) {
		Transition<S> nextStateTransition = transitions.get(symbol);
		
		if(Objects.isNull(nextStateTransition))
			nextStateTransition = elseTransition;
		if(Objects.isNull(nextStateTransition))
			return Optional.empty();
		
		return Optional.ofNullable(nextStateTransition.getState());
	}

	/**
	 * Checks if the {@link stateMachine.State} is final
	 * 
	 * @return true if its final
	 */
	public boolean isFinal() {
		return finalState;
	}
	
}
