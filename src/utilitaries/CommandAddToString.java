package utilitaries;

/**
 * {@link Command} to add a character to a {@link java.lang.StringBuilder}
 * 
 * @author nickyecen
 */
public class CommandAddToString implements Command {

	private StringBuilder str;
	private char value;
	
	/**
	 * Constructs the {@link Command} that can add a character to a {@link java.lang.StringBuilder}
	 * 
	 * @param str the string that will be edited
	 * @param value the character that will be added to the string
	 */
	public CommandAddToString(StringBuilder str, char value) {
		this.str = str;
		this.value = value;
	}
	
	/**
	 * Executes the addition of the character to the string
	 */
	@Override
	public void execute() {
		str.append(value);
	}

}
