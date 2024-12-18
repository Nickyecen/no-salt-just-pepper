package music.songOrchestrator;

import java.util.Random;

import music.MusicPlayer;
import music.Note;

public class WriteControl {
		final private static int RANDOM_BPM_MIN = 60;
		final private static int RANDOM_BPM_MAX = 120;
		final private static int STARTING_BPM = 60;
		private static final int BPM_INCREASE_RATE = 80;
		
		private MusicPlayer player;
		
		private int BPM;
		private StringBuilder Pattern = new StringBuilder("T" + Integer.toString(STARTING_BPM) + " ");
		
		public WriteControl() {
			MusicPlayer.Builder builder = new MusicPlayer.Builder();
			this.player = builder.build();
			this.BPM = STARTING_BPM;
		}
		
		public int getBPM() {
			return BPM;
		}
		
		public void setBPM(int newBPM) {
			this.BPM = newBPM;
		}
		
		public String getPattern() {
			return Pattern.toString();
		}
		
		public void appendPattern(String str) {
			Pattern.append(str);
		}
		
		public void commandDoNothing() {}

		public void raiseBPM() {
			setBPM(getBPM() + 80);
			Pattern.append("T" + String.valueOf(getBPM()) + " ");
		}
		
		public void randomBPM() {
			setBPM(new Random().nextInt(RANDOM_BPM_MIN, RANDOM_BPM_MAX+1));
			Pattern.append("T" + String.valueOf(getBPM()) + " ");
		}
		
		public void raiseOctave() {
			player.setOctave(player.getOctave() + 1);
		}
		
		public void lowerOctave() {
			player.setOctave(player.getOctave() - 1);
		}	
		
		public void setInstrumentTelephone() {
			player.setInstrument(124);
			Pattern.append(new StringBuilder("I" + String.valueOf(player.getInstrument().ordinal()) + " "));
		}
		
		public void goToNextInstrument(StringBuilder str) {
			player.setInstrument(player.getInstrument().getMidiCode() + 1);
			Pattern.append(new StringBuilder("I" + String.valueOf(player.getInstrument().ordinal()) + " "));
		}
		
		public void returnToPreviousInstrument(StringBuilder str) {
			player.setInstrument(player.getPreviousInstrument());
			Pattern.append(new StringBuilder("I" + String.valueOf(player.getInstrument().ordinal()) + " "));
		}
		
		public void randomNote() {
			Note randomNote = Note.values()[new Random().nextInt(Note.values().length)];
			Pattern.append(randomNote.name() +  String.valueOf(player.getOctave()) + " ");
		}
		
		public void rest() {
			Pattern.append("R ");
		}
		
		public void playA() {
			Pattern.append("A" + player.getOctave() + " ");
		}
		
		public void playB() {
			Pattern.append("B" + player.getOctave() + " ");
		}
		
		public void playC() {
			Pattern.append("C" + player.getOctave() + " ");
		}
		
		public void playD() {
			Pattern.append("D" + player.getOctave() + " ");
		}
		
		public void playE() {
			Pattern.append("E" + player.getOctave() + " ");
		}
		
		public void playF() {
			Pattern.append("F" + player.getOctave() + " ");
		}
		
		public void playG() {
			Pattern.append("G" + player.getOctave() + " ");
		}
		
		public void runCommand(int commandNumber) {
			switch(commandNumber) {
				case 0:
					break;
				case 1:
					player.setOctave(player.getOctave() + 1);
					break;
				case 2:
					player.setOctave(player.getOctave() - 1);
					break;
				case 3:
					setBPM(getBPM() + BPM_INCREASE_RATE);
					Pattern.append("T" + String.valueOf(getBPM()) + " ");
					break;
				
			}
		}
		
}
