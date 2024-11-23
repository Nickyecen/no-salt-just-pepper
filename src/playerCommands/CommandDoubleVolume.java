package playerCommands;

import music.CommandPlayer;
import music.MusicPlayer;

/**
 * {@link utilitaries.Command} to double the volume of a {@link music.MusicPlayer}
 * 
 * @author nickyecen
 */
public class CommandDoubleVolume extends CommandPlayer {
	
	/**
	 * Constructs a {@link utilitaries.Command} that can double the volume of a {@link music.MusicPlayer}
	 * 
	 * @param player the {@link music.MusicPlayer} that will have its volume doubled
	 */
	public CommandDoubleVolume(MusicPlayer player) {
		super(player);
	}

	/**
	 * Executes the volume doubling of the {@link music.MusicPlayer} 
	 */
	@Override
	public void execute() {
		double currentVolume = player.getVolume();
	
		// Robust attribution of the sound not to overflow it
		if(currentVolume < MusicPlayer.MAX_VOLUME) {
			if(currentVolume * 2 > MusicPlayer.MAX_VOLUME) {
				player.setVolume(MusicPlayer.MAX_VOLUME);
			} else {	
				player.setVolume(currentVolume*2);
			}
		} else {
			// Log for debugging
			System.out.println("WARNING: Volume overflow @ CommandDoubleVolume.execute() - Restoring to 1");
			player.setVolume(1);
		}
	}

}
