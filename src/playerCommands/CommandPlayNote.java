package playerCommands;

import music.CommandPlayer;
import music.MusicPlayer;
import music.Note;

/**
 * {@link utilitaries.Command} to play a {@link music.Note} in a {@link music.MusicPlayer}
 * 
 * @author nickyecen
 */
public class CommandPlayNote extends CommandPlayer {

	private Note note;
	
	/**
	 * Constructs a {@link utilitaries.Command} that can play a {@link music.Note} in a {@link music.MusicPlayer}
	 * 
	 * @param player the {@link music.MusicPlayer} that will play the {@link music.Note} 
	 */
	public CommandPlayNote(MusicPlayer player, Note note) {
		super(player);
		this.note = note;
	}

	/**
	 * Executes the playing of a {@link music.Note} from the {@link music.MusicPlayer}
	 */
	@Override
	public void execute() {
		player.playNote(note);
	}

}
