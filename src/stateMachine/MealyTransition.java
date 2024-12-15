package stateMachine;

import utilitaries.Command;

public class MealyTransition<S> extends Transition<S> {

	private final Command output;
	
	protected MealyTransition(State<S> incidentState, Command output) {
		super(incidentState);
		this.output = output;
	}

	public static <S> MealyTransition<S> to(State<S> incidentState) {
		return new MealyTransition<S>(incidentState, Command.NOTHING);
	}
	
	public MealyTransition<S> doing(Command output) {
		return new MealyTransition<S>(super.getState(), output);
	}
	
	@Override
	public State<S> getState() {
		output.execute();
		return super.getState();
	}

}
