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

	private Instrument instrument;

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
	}

	/**
	 * Executes the {@link music.Instrument} change of the {@link music.MusicPlayer} 
	 * 
	 * @author nickyecen
	 */
	@Override
	public void execute() {
		player.changeInstrumentTo(instrument);
	}	

}
