package playerCommands;

import music.CommandPlayer;
import music.MusicPlayer;

/**
 * {@link utilitaries.Command} to lower an octave in a {@link music.MusicPlayer}
 * 
 * @author Dantdddd
 */
public class CommandLowerOctave extends CommandPlayer {

	/**
	 * Constructs a {@link utilitaries.Command} that can lower an octave of a {@link music.MusicPlayer}
	 * 
	 * @param player the {@link music.MusicPlayer} that will have an octave lowered
	 */
	public CommandLowerOctave(MusicPlayer player) {
		super(player);
	}

	/**
	 * Executes the octave lowering in the {@link music.MusicPlayer}
	 */
	@Override
	public void execute() {
		int currentOctave = player.getOctave();
	
		// Robust attribution
		if(currentOctave > MusicPlayer.MIN_OCTAVE) {
			player.setOctave(currentOctave - 1);
		} else {
			// Log for debugging
			System.out.println("WARNING: Octave overflow @ CommandLowerOctave.execute() - Restoring to 0");
			player.setOctave(0);
		}
	}

}
