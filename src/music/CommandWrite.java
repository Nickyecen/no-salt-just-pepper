package music;

import music.songOrchestrator.WriteControl;
import utilitaries.Command;

/**
 * Abstract class parent to all classes the will execute a command on a {@link music.Interpreter.Control}
 * 
 * @author Dantdddd
 */
public abstract class CommandWrite implements Command {

	// Variables
	protected WriteControl writeControl;

	/**
	 * Abstract constructor that declares the need for a {@link music.songOrchestrator.Control}
	 * 
	 * @param control the {@link music.songOrchestrator.Control} the command will be executed in
	 */
	protected CommandWrite(WriteControl writeControl) {
		this.writeControl = writeControl;
	}

}
