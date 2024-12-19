package music.songOrchestrator;

import java.util.Random;

import music.MusicPlayer;
import music.Note;

/**
 *  Used to write the JFugue pattern and
 * has the functions {@link controlCommands.CommandMakeWriterDo}
 * needs to do it
 */
public class WriteControl {
		final private static int RANDOM_BPM_MIN = 60;
		final private static int RANDOM_BPM_MAX = 120;
		private static final int BPM_INCREASE_RATE = 80;
		
		private MusicPlayer player;
		
		private int BPM;
		private StringBuilder Pattern = new StringBuilder();
		
		/**
		 * Constructor for the class {@link music.songOrchestrator.WriteControl}
		 *
		 * @param inicialBPM sets the {@link BPM}
		 */
		public WriteControl(int inicialBPM) {
			MusicPlayer.Builder builder = new MusicPlayer.Builder();
			this.player = builder.build();
			player.setVolume(MusicPlayer.DEFAULT_VOLUME);
			player.setOctave(MusicPlayer.DEFAULT_OCTAVE);
			player.setInstrument(MusicPlayer.DEFAULT_INSTRUMENT);
			this.BPM = inicialBPM;
			Pattern.append("T" + Integer.toString(inicialBPM) + " ");
		}
		
		/**
		 * Gets the {@link BPM} of the {@link music.songOrchestrator.WriteControl}
		 *
		 * @return the current {@link music.songOrchestrator.WriteControl} {@link BPM}
		 */
		public int getBPM() {
			return BPM;
		}
		
		/**
		 * Sets the {@link BPM} of the {@link music.songOrchestrator.WriteControl}
		 *
		 * @param BPM the new {@link BPM}
		 */
		public void setBPM(int newBPM) {
			this.BPM = newBPM;
		}
		
		/**
		 * Gets the {@link Pattern} of the {@link music.songOrchestrator.WriteControl}
		 *
		 * @return the current {@link music.songOrchestrator.WriteControl} {@link Pattern}
		 */
		public String getPattern() {
			return Pattern.toString();
		}
		
		/**
		 * Appends String to {@link Pattern} of the {@link music.songOrchestrator.WriteControl}
		 *
		 * @param str the string that will be appended to 
		 * {@link music.songOrchestrator.WriteControl} {@link Pattern}
		 */
		public void appendPattern(String str) {
			Pattern.append(str);
		}
		
		/**
		 * Does nothing
		 */
		public void commandDoNothing() {}

    /**
		 * Raises the {@link BPM} of {@link music.songOrchestrator.WriteControl} 
		 * by 80 and appends it to the {@link Pattern}
		 */
		public void raiseBPM() {
			setBPM(getBPM() + 80);
			Pattern.append("T" + String.valueOf(getBPM()) + " ");
		}
		
		/**
		 * Sets the {@link BPM} of {@link music.songOrchestrator.WriteControl} 
		 * to a random value and appends it to the {@link Pattern}
		 */
		public void randomBPM() {
			setBPM(new Random().nextInt(RANDOM_BPM_MIN, RANDOM_BPM_MAX+1));
			Pattern.append("T" + String.valueOf(getBPM()) + " ");
		}
		
		/**
		 * Raises the {@link octave} of {@link music.MusicPlayer} by one
		 */
		public void raiseOctave() {
			player.setOctave(player.getOctave() + 1);
		}
		
		/**
		 * Lowers the {@link octave} of {@link music.MusicPlayer} by one
		 */
		public void lowerOctave() {
			player.setOctave(player.getOctave() - 1);
		}	
		
		/**
		 * Sets the {@link instrument} of {@link music.MusicPlayer} by to the value
		 * of the instrument Telephone
		 */
		public void setInstrumentTelephone() {
			player.setInstrument(124);
			Pattern.append(new StringBuilder("I" + String.valueOf(player.getInstrument().ordinal() + 1) + " "));
		}
		
		/**
		 * Goes to next {@link instrument} of {@link music.MusicPlayer}
		 */
		public void goToNextInstrument() {
			player.setInstrument(player.getInstrument().getMidiCode() + 1);
			Pattern.append(new StringBuilder("I" + String.valueOf(player.getInstrument().ordinal() + 1) + " "));
		}
		
		/**
		 * Returns to {@link previousInstrument} of {@link music.MusicPlayer}
		 */
		public void returnToPreviousInstrument() {
			player.setInstrument(player.getPreviousInstrument());
			Pattern.append(new StringBuilder("I" + String.valueOf(player.getInstrument().ordinal() + 1) + " "));
		}
		
		/**
		 * Appends a random note to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void randomNote() {
			Note randomNote = Note.values()[new Random().nextInt(Note.values().length)];
			Pattern.append(randomNote.name() +  String.valueOf(player.getOctave()) + " ");
		}
		
		/**
		 * Appends a rest character to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void rest() {
			Pattern.append("R ");
		}
		
		/**
		 * Appends the note A to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playA() {
			Pattern.append("A" + player.getOctave() + " ");
		}
		
		/**
		 * Appends the note B to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playB() {
			Pattern.append("B" + player.getOctave() + " ");
		}
		
		/**
		 * Appends the note C to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playC() {
			Pattern.append("C" + player.getOctave() + " ");
		}
		
		/**
		 * Appends the note D to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playD() {
			Pattern.append("D" + player.getOctave() + " ");
		}
		
		/**
		 * Appends the note E to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playE() {
			Pattern.append("E" + player.getOctave() + " ");
		}
		
		/**
		 * Appends the note F to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playF() {
			Pattern.append("F" + player.getOctave() + " ");
		}
		
		/**
		 * Appends the note G to {@link music.songOrchestrator.WriteControl}
		 * {@link Pattern}
		 */
		public void playG() {
			Pattern.append("G" + player.getOctave() + " ");
		}
}