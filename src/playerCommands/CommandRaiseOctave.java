package playerCommands;

import music.CommandPlayer;
import music.MusicPlayer;

/**
 * {@link utilitaries.Command} to raise an octave in a {@link music.MusicPlayer}
 * 
 * @author nickyecen
 */
public class CommandRaiseOctave extends CommandPlayer {

	/**
	 * Constructs a {@link utilitaries.Command} that can raise an octave of a {@link music.MusicPlayer}
	 * 
	 * @param player the {@link music.MusicPlayer} that will have an octave raised 
	 */
	public CommandRaiseOctave(MusicPlayer player) {
		super(player);
	}

	/**
	 * Executes the octave raising in the {@link music.MusicPlayer}
	 */
	@Override
	public void execute() {
		int currentOctave = player.getOctave();
	
		// Robust attribution
		if(currentOctave < MusicPlayer.MAX_OCTAVE) {
			player.setOctave(currentOctave + 1);
		} else {
			// Log for debugging
			System.out.println("WARNING: Octave overflow @ CommandRaiseOctave.execute() - Restoring to 0");
			player.setOctave(0);
		}
	}

}
