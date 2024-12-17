package controlCommands;

import music.CommandControl;
import music.songOrchestrator.Control;

/**
 * {@link utilitaries.Command} to raise the BPM of a {@link music.songOrchestrator.Control}
 * 
 * @author nickyecen
 */
public class CommandRaiseBPM extends CommandControl {

	int amount = 60;
	
	/**
	 * Constructs a {@link utilitaries.Command} that can raise the BPM of a {@link music.songOrchestrator.Control}
	 * 
	 * @param control the {@link music.songOrchestrator.Control} that will have its BPM raised
	 * @param amount the amount that the BPM will be risen by 
	 */
	public CommandRaiseBPM(Control control, int amount) {
		super(control);
		this.amount = amount;
	}

	/**
	 * Executes the BPM raising of the {@link music.songOrchestrator.Control} 
	 */
	@Override
	public void execute() {
		control.raiseBPMBy(amount);
	}

}
