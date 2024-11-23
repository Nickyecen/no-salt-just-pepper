package playerCommands;

import music.CommandPlayer;
import music.Instrument;
import music.MusicPlayer;

/**
 * {@link utilitaries.Command} to change the {@link music.Instrument} of a {@link music.MusicPlayer}
 *  
 * @author nickyecen
 */
public class CommandChangeInstrument extends CommandPlayer {

	private Instrument instrument = null;
	private int instrumentIncrement = 0;

	/**
	 * Constructs a {@link utilitaries.Command} that can change the {@link music.Instrument} of a {@link music.MusicPlayer} to a specified {@link music.Instrument} 
	 * 
	 * @param player the {@link music.MusicPlayer} that will have its {@link music.Instrument} changed
	 * @param instrument the {@link music.Instrument} that will be changed to
	 * 
	 * @author nickyecen
	 */
	public CommandChangeInstrument(MusicPlayer player, Instrument instrument) {
		super(player);
		this.instrument = instrument;
	}
	
	/**
	 * Constructs a {@link utilitaries.Command} that can change the {@link music.Instrument} of a {@link music.MusicPlayer} to a different {@link music.Instrument} 
	 * 
	 * @param player the {@link music.MusicPlayer} that will have its {@link music.Instrument} changed
	 * @param nextInstrument the number that the current {@link music.Instrument} will have added to to get the new instrument
	 * 
	 * @author nickyecen
	 */
	public CommandChangeInstrument(MusicPlayer player, int instrumentIncrement) {
		super(player);
		this.instrumentIncrement = instrumentIncrement;
	}

	/**
	 * Executes the {@link music.Instrument} change of the {@link music.MusicPlayer} 
	 * 
	 * @author nickyecen
	 */
	@Override
	public void execute() {
		if(instrument == null) {
			int previousInstrumentCode = player.getInstrument().getMidiCode();
			Instrument newInstrument = Instrument.fromMidiCode(previousInstrumentCode + instrumentIncrement);
			
			player.changeInstrumentTo(newInstrument);
		} else {
			player.changeInstrumentTo(instrument);
		}
	}	

}
