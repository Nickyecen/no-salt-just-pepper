package stateMachine;

import utilitaries.Command;

/**
 * A {@link stateMachine.Transition} to a {@link stateMachine.State} with a {@link utilitaries.Command} output
 * 
 * @param <S> The symbol being used in the {@link stateMachine.StateMachine}
 * 
 * @author nickyecen
 */
public class MealyTransition<S> extends Transition<S> {

	private final Command output;

	/**
	 * Constructs the {@link stateMachine.MealyTransition}
	 * 
	 * @param incidentState the state it's transitioning to
	 * @param output the output of the transition
	 */
	protected MealyTransition(State<S> incidentState, Command output) {
		super(incidentState);
		this.output = output;
	}

	/**
	 * Static factory method to choose the destination of the {@link stateMachine.Transition}. Meant to be used as:
	 * MealyTransition.to(nextState);
	 * 
	 * @param <S> the symbol being used by the {@link stateMachine.StateMachine}
	 * @param incidentState the {@link stateMachine.State} its transitioning to
	 * @return the new {@link stateMachine.MealyTransition} with the new {@link @stateMachine.State} destination
	 */
	public static <S> MealyTransition<S> to(State<S> incidentState) {
		return new MealyTransition<S>(incidentState, Command.NOTHING);
	}

	/**
	 * Static factory method to choose the {@link utilitaries.Command} output of the {@link stateMachine.MealyTransition}. Meant to be used together with "to()" as:
	 * MealyTransition.to(nextState).doing(something);
	 * 
	 * @param output the {@link utilitaries.Command} output to be executed by the {@link stateMachine.MealyTransition}
	 * @return the new {@link stateMachine.MealyTransition} with the new output 
	 */
	public MealyTransition<S> doing(Command output) {
		return new MealyTransition<S>(super.getState(), output);
	}

	/**
	 * Executes the output {@link utilitaries.Command} and gets the next {@link stateMachine.State} 
	 */
	@Override
	public State<S> getState() {
		output.execute();
		return super.getState();
	}

}
