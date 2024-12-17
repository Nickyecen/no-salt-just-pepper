package music;

import music.songOrchestrator.Control;
import utilitaries.Command;

/**
 * Abstract class parent to all classes the will execute a command on a {@link music.songOrchestrator.Control}
 * 
 * @author nickyecen
 */
public abstract class CommandControl implements Command {

	// Variables
	protected Control control;

	/**
	 * Abstract constructor that declares the need for a {@link music.songOrchestrator.Control}
	 * 
	 * @param control the {@link music.songOrchestrator.Control} the command will be executed in
	 */
	protected CommandControl(Control control) {
		this.control = control;
	}

}
