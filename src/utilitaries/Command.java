package utilitaries;

/**
 * Generic interface command for generic function execution
 * 
 * @author nickyecen
 */
public interface Command {
	public final Command NOTHING = new NothingCommand();
	/**
	 * Generic function execution
	 */
	public void execute();

	/**
	 * Class for a command that does nothing
	 */
	public class NothingCommand implements Command {
		@Override
		public void execute() {}
	
		private NothingCommand() {}
		
	}
}
