package playerCommands;

import music.CommandPlayer;
import music.MusicPlayer;

/**
 * {@link utilitaries.Command} to reset to default the volume of a {@link music.MusicPlayer}
 * 
 * @author Dantdddd
 */
public class CommandResetVolume extends CommandPlayer {
	
	/**
	 * Constructs a {@link utilitaries.Command} that can reset the volume of a {@link music.MusicPlayer}
	 * 
	 * @param player the {@link music.MusicPlayer} that will have its volume reset
	 */
	public CommandResetVolume(MusicPlayer player) {
		super(player);
	}

	/**
	 * Executes the volume reseting of the {@link music.MusicPlayer} 
	 */
	@Override
	public void execute() {
		double currentVolume = player.getVolume();
	
		// Robust attribution of the sound not to overflow it
		if(currentVolume < MusicPlayer.MAX_VOLUME) {
			player.setVolume(MusicPlayer.DEFAULT_VOLUME);
		} else {
			// Log for debugging
			System.out.println("WARNING: Volume overflow @ CommandResetVolume.execute() - Restoring to 1");
			player.setVolume(1);
		}
	}

}