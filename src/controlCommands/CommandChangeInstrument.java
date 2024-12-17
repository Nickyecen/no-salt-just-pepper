package controlCommands;

import music.songOrchestrator.Control;

import java.util.Objects;

import music.CommandControl;
import music.Instrument;

/**
 * {@link utilitaries.Command} to change the {@link music.Instrument} trough {@link music.songOrchestrator.Control}
 *  
 * @author nickyecen
 */
public class CommandChangeInstrument extends CommandControl {

	private Instrument instrument = null;
	private int instrumentIncrement = 0;

	/**
	 * Constructs a {@link utilitaries.Command} that can change the {@link music.Instrument} with a {@link music.songOrchestrator.Control} to a specified {@link music.Instrument} 
	 * 
	 * @param control the {@link music.songOrchestrator.Control} that will call the {@link music.Instrument} change
	 * @param instrument the {@link music.Instrument} that will be changed to
	 */
	public CommandChangeInstrument(Control control, Instrument instrument) {
		super(control);
		this.instrument = instrument;
	}
	
	/**
	 * Constructs a {@link utilitaries.Command} that can change the {@link music.Instrument} with a {@link music.songOrchestrator.Control} to a different {@link music.Instrument} 
	 * 
	 * @param control the {@link music.songOrchestrator.Control} that will call the {@link music.Instrument} change
	 * @param instrumentIncrement the number that the current {@link music.Instrument} will have added to to get the new instrument
	 */
	public CommandChangeInstrument(Control control, int instrumentIncrement) {
		super(control);
		this.instrumentIncrement = instrumentIncrement;
	}

	/**
	 * Executes the {@link music.Instrument} with the {@link music.songOrchestrator.Control} 
	 */
	@Override
	public void execute() {
		if(Objects.isNull(instrument)) control.changeInstrumentTo(instrumentIncrement);
		else control.changeInstrumentTo(instrument);
	}	

}
