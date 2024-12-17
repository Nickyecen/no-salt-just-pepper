package controlCommands;

import java.lang.reflect.Method;

import music.CommandControl;
import music.MusicPlayer;
import music.songOrchestrator.Control;

/**
 * {@link utilitaries.Command} to execute any method from a {@link music.songOrchestrator.Control}
 * 
 * @author nickyecen 
 */
public class CommandMakeControlDo extends CommandControl {

	String method;
	
	/**
	 * Constructs a {@link utilitaries.Command} that can execute a method of a {@link music.songOrchestrator.Control}
	 * 
	 * @param control the {@link music.songOrchestrator.Control} that will execute the method
	 * @param method the name of the {@link music.songOrchestrator.Control} that will be executed 
	 */
	public CommandMakeControlDo(Control control, String method) {
		super(control);
		this.method = method;
	}

	/**
	 * Executes the given method of {@link music.songOrchestrator.Control}
	 */
	@Override
	public void execute() {
		try {
			Method method = control.getClass().getMethod(this.method);
			method.invoke(control);
		} catch(Exception e) {
			System.out.println("Error while calling Control method by Command:" + e.getMessage());
		}
	}

}
