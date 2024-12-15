package music.songOrchestrator;

import java.util.ArrayList;

import music.Interpreter;
import stateMachine.StateMachine;

/**
 * Class that orchestrates the {@link utilitaries.MealyMachine} that will play the midi
 * 
 * @author nickyecen
 */
public class Orchestrator {
	
	private final String DECODER_MACHINE_PATH = "src/res/decoderMachine.json";
	
	private String songRequest;
	private final Control CONTROL;
	private final Interpreter INTERPRETER;

	/**
	 * Constructs the Orchestrator with the composition it will guide
	 * 
	 * @param composition
	 */
	public Orchestrator() {
		this.CONTROL = new Control(this.loadComposition());
		this.INTERPRETER = Interpreter.loadFrom(DECODER_MACHINE_PATH);
	}

	private StateMachine<ArrayList<String>, String> loadComposition() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Starts playing the composition
	 */
	public void play() {
		if(CONTROL.getStatus() == Status.STOPPED) {
			INTERPRETER.interpret(songRequest);
			CONTROL.getComposition().setWord(INTERPRETER.getInterpretation());
			System.out.println(INTERPRETER.getInterpretation());
		}
		CONTROL.start();
	}
	
	/**
	 * Pauses the playing of the composition
	 */
	public void pause() {
		if(CONTROL.getStatus() != Status.STOPPED) CONTROL.setStatus(Status.PAUSED);
	}
	
	/**
	 * Stops the playing of the composition
	 */
	public void stop() {
		CONTROL.setStatus(Status.STOPPED);
	}
	
	/**
	 * Gets the midi song being orchestrated
	 * 
	 * @return the midi song being orchestrated
	 */
	public String getSongRequest() {
		return songRequest;
	}

	/**
	 * Sets the midi song being orchestrated
	 * 
	 * @param midiSong the new midi song
	 */
	public void setSongRequest(String songRequest) {
		this.songRequest = songRequest;
		INTERPRETER.interpret(songRequest);
		System.out.println(INTERPRETER.getInterpretation());
		CONTROL.getComposition().setWord(INTERPRETER.getInterpretation());
	}

	/**
	 * Gets the control being used
	 * 
	 * @return the control
	 */
	public Control getControl() {
		return CONTROL;
	}

	public void setBpm(int number) {
		// TODO Auto-generated method stub
		
	}

}
