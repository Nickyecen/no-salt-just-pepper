package music.songOrchestrator;

import java.io.File;
import java.util.ArrayList;

import music.Interpreter;
import stateMachine.StateMachine;

/**
 * Class that orchestrates the song the user has put in
 * 
 * @author nickyecen
 */
public class Orchestrator {
	
	private final String DECODER_MACHINE_PATH = "src/res/decoderMachine.json";
	private final String SAVING_ERROR_MESSAGE = "You have to pause the music to save the MIDI file!";
	
	private String songRequest;
	private final Control CONTROL;
	private final Interpreter INTERPRETER;

	/**
	 * Constructs the music.songOrchestrator.Orchestrator with the composition it will guide
	 * 
	 * @param composition
	 */
	public Orchestrator() {
		this.CONTROL = new Control();
		this.INTERPRETER = Interpreter.loadFrom(DECODER_MACHINE_PATH);
	}

	/**
	 * Starts playing the composition
	 */
	public void play() {
		if(CONTROL.getStatus() == Status.STOPPED) {
			INTERPRETER.interpretToDecoder(songRequest);
			CONTROL.getComposition().setWord(INTERPRETER.getInterpretation());
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
		if(CONTROL.getStatus() != Status.PAUSED) {	
			this.songRequest = songRequest;
			INTERPRETER.interpretToDecoder(songRequest);
			CONTROL.getComposition().setWord(INTERPRETER.getInterpretation());
		}
	}

	public void setSaveRequest(String songRequest, File filePath, int inicialBPM) {
		this.songRequest = songRequest;
		if(CONTROL.getStatus() == Status.STOPPED) {
			INTERPRETER.interpretToDecoder(songRequest);
			INTERPRETER.interpretToMIDI(filePath, inicialBPM);
		} else {
			System.out.println(SAVING_ERROR_MESSAGE);
		}
	}
	
	/**
	 * Gets the {@link music.songOrchestrator.Orchestrator} being used
	 * 
	 * @return the {@link music.songOrchestrator.Orchestrator}
	 */
	public Control getControl() {
		return CONTROL;
	}

	/**
	 * Sets the {@link music.songOrchestrator.Control}'s BPM 
	 */
	public void setBpm(int number) {
		CONTROL.setResetBpm(number);		
	}

}
