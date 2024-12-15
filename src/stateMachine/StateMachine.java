package stateMachine;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A Mealy state machine that will read through a state machine and execute outputs in its transitions
 * 
 * @author nickyecen
 */
public class StateMachine<W extends Iterable<S>, S> {

	private State<S> initialState;
	private State<S> currentState;
	private W word = null;
	private S currentSymbol;
	private Iterator<S> wordIterator;

	
	public StateMachine(State<S> initialState) {
		this.initialState = initialState;
		this.currentState = initialState;
	}
	
	public void reset() {
		this.currentState = this.initialState;
		if(Objects.nonNull(word)) {
			this.wordIterator = word.iterator();
			this.currentSymbol = this.wordIterator.next();	
		}
	}
	
	public boolean run() {
		this.reset();
		
		if(Objects.isNull(word)) return false;
		
		for(S symbol : word) {
			try {
				currentState = currentState.transitionWith(symbol).get();	
			} catch(NoSuchElementException e) {
				return false;
			}
		}
		
		return currentState.isFinal();
	}	
	
	public boolean run(W word) {
		this.setWord(word);
		return this.run();
	}
	
	public boolean nextState() {
		boolean hasNextSymbol = wordIterator.hasNext();
	
		Optional<State<S>> transitionReturn = currentState.transitionWith(currentSymbol);
		if(transitionReturn.isEmpty()) {
			throw new IllegalArgumentException("Invalid word input for state machine");
		}
		
		currentState = transitionReturn.get();
		
		if(hasNextSymbol) {
			currentSymbol = wordIterator.next();
		}
		
		return hasNextSymbol;
	}
	
	public void setWord(W word) {
		this.word = word;
		this.reset();
	}
	
}
