package utilitaries;

import java.util.List;

/**
 * {@link Command} to add a character to a {@link java.lang.StringBuilder}
 * 
 * @author nickyecen
 */
public class CommandAddToList implements Command {

	private List<String> array;
	private String value;
	
	/**
	 * Constructs the {@link Command} that can add a character to a {@link java.lang.StringBuilder}
	 * 
	 * @param str the string that will be edited
	 * @param value the character that will be added to the string
	 */
	public CommandAddToList(List<String> array, String value) {
		this.array = array;
		this.value = value;
	}
	
	/**
	 * Executes the addition of the character to the string
	 */
	@Override
	public void execute() {
		array.add(value);
	}

}
