package controlCommands;

import music.CommandControl;
import music.MusicPlayer;
import music.Note;
import music.songOrchestrator.Control;

/**
 * {@link utilitaries.Command} to play a {@link music.Note} through a {@link music.songOrchestrator.Control}
 * 
 * @author nickyecen
 */
public class CommandPlayNote extends CommandControl {

	private Note note;
	
	/**
	 * Constructs a {@link utilitaries.Command} that can play a {@link music.Note} with a {@link music.songOrchestrator.Control}
	 * 
	 * @param control the {@link music.songOrchestrator.Control} that will make the {@link music.Note} play
	 */
	public CommandPlayNote(Control control, Note note) {
		super(control);
		this.note = note;
	}

	/**
	 * Executes the playing of a {@link music.Note} with the {@link music.songOrchestrator.Control}
	 */
	@Override
	public void execute() {
		control.playNote(note);
	}

}
