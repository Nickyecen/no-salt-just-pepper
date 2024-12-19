package stateMachine;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link stateMachine.StateMachine} that will read through a sequence of {@link stateMachine.State} through its {@link stateMachine.Transition}
 * 
 * @author nickyecen
 *
 * @param <W> the {@link Iterable} word that will be analyzed
 * @param <S> the symbols inside the word
 */
public class StateMachine<W extends Iterable<S>, S> {

	private State<S> initialState;
	private State<S> currentState;
	private W word = null;
	private S currentSymbol;
	private Iterator<S> wordIterator;


	/**
	 * Constructs a {@link stateMachine.StateMachine} based on the first {@link stateMachine.State} 
	 * 
	 * @param initialState the first {@link stateMachine.State}
	 */
	public StateMachine(State<S> initialState) {
		this.initialState = initialState;
		this.currentState = initialState;
	}

	/**
	 * Resets the machine to the beginning
	 */
	public void reset() {
		this.currentState = this.initialState;
		if(Objects.nonNull(word)) {
			this.wordIterator = word.iterator();
			if(wordIterator.hasNext())
				this.currentSymbol = this.wordIterator.next();	
		}
	}

	/**
	 * Runs the machine
	 * 
	 * @return true if the word was accepted
	 */
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

	/**
	 * Runs the machine for a given word
	 * 
	 * @param word the word to be used
	 * @return true if the word is accepted
	 */
	public boolean run(W word) {
		this.setWord(word);
		return this.run();
	}

	/**
	 * Goes to the next {@link stateMachine.State} from the one the machine is currently at
	 * 
	 * @return true if there was a next {@link stateMachine.State}
	 */
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

	/**
	 * Sets the word being read
	 * 
	 * @param word the word to be read
	 */
	public void setWord(W word) {
		this.word = word;
		this.reset();
	}
	
}
