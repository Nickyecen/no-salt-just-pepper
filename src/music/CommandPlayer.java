package music;

import utilitaries.Command;

/**
 * Abstract class parent to all classes the will execute a command on a {@link music.MusicPlayer}
 * 
 * @author nickyecen
 */
public abstract class CommandPlayer implements Command {

	// Variables
	protected MusicPlayer player;

	/**
	 * Abstract constructor that declares the need for a {@link music.MusicPlayer}
	 * 
	 * @param player the one the command will be executed in
	 * 
	 * @author nickyecen
	 */
	protected CommandPlayer(MusicPlayer player) {
		this.player = player;
	}

}
