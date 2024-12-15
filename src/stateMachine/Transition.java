package stateMachine;

public class Transition<S> {
	private final State<S> incidentState;

	protected Transition(State<S> incidentState) {
		this.incidentState = incidentState;
	}
	
	public static <S> Transition<S> to(State<S> incidentState) {
		return new Transition<S>(incidentState);
	}
	
	public State<S> getState() {
		return incidentState;
	}
}
