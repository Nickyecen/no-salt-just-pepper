package stateMachine;

/**
 * A {@link stateMachine.Transition} to be used inside a {@link stateMachine.StateMachine} to move between {@link stateMachine.State}
 * @param <S> the symbol being used by the {@link stateMachine.StateMachine}
 */
public class Transition<S> {
	private final State<S> incidentState;
	
	/**
	 * Constructs the {@link stateMachine.Transition} to the next {@link stateMachine.State}
	 * 
	 * @param incidentState the next {@link stateMachine.State}
	 */
	protected Transition(State<S> incidentState) {
		this.incidentState = incidentState;
	}
	
	/**
	 * Static factory method to choose the destination of the {@link stateMachine.Transition}. Meant to be used as:
	 * Transition.to(nextState);
	 * 
	 * @param <S> the symbol being used by the {@link stateMachine.StateMachine}
	 * @param incidentState the {@link stateMachine.State} its transitioning to
	 * @return the new {@link stateMachine.Transition} with the new {@link @stateMachine.State} destination
	 */
	public static <S> Transition<S> to(State<S> incidentState) {
		return new Transition<S>(incidentState);
	}

	/**
	 * Gets the destination {@link stateMachine.State} of the {@link stateMachine.Transition}
	 * 
	 * @return the destination {@link stateMachine.State} 
	 */
	public State<S> getState() {
		return incidentState;
	}
}
