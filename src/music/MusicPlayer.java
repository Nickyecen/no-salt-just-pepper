package music;

/**
 * Class of a music player that can play notes using JFugue
 * 
 * @author 
 * @author nickyecen
 */
public class MusicPlayer {

	public static final double MAX_VOLUME = 100; // TODO: Replace with actual max value
	public static final double MIN_VOLUME = 0; // TODO: Replace with actual min value
	public static final int MAX_OCTAVE = 9;
	public static final int MIN_OCTAVE = 0;
	
	private double volume; // TODO: Make volume code robust, eg: I shouldn't be allowed to set the volume to -1
	private Instrument instrument;
	private int octave; // TODO: Make setter robust

	// TODO: Make code more robust
	/**
	 * Constructs a MusicPlayer with a defined volume, {@link music.Instrument} and octave
	 * 
	 * @param volume the volume of how loud the {@link music.Note} will be
	 * @param instrument the {@link music.Instrument} that the {@link music.Note} will use
	 * @param octave the octave in which each {@link music.Note} will be played
	 */
	private MusicPlayer(double volume, Instrument instrument, int octave) {
		this.setVolume(volume);
		this.instrument = instrument;
		this.setOctave(octave);
	}

	/**
	 * A builder class for {@link music.MusicPlayer} to be built for better readability
	 * 
	 * @see music.MusicPlayer
	 */
	public class Builder {
		private double volume;
		private Instrument instrument;
		private int octave;
	
		/**
		 * Constructs the builder with default values for the {@link music.MusicPlayer}
		 */
		public Builder() {
			this.volume = MusicPlayer.MAX_VOLUME;
			this.instrument = Instrument.ACOUSTIC_GRAND_PIANO;
			this.octave = MIN_OCTAVE;
		}
	
		// TODO: Make function robust
		/**
		 * Sets the volume of the {@link music.MusicPlayer} that will be built
		 * 
		 * @param volume the new volume value
		 * @return the builder with the updated value
		 */
		public Builder volume(double volume) {
			this.volume = volume; // Make attribution more robust (private setter?)
			return this;
		}

		/**
		 * Sets the {@link music.Instrument} of the {@link music.MusicPlayer} that will be built
		 * 
		 * @param instrument the new {@link music.Instrument} that will be used
		 * @return the builder with the updated {@link music.Instrument}
		 */
		public Builder instrument(Instrument instrument) {
			this.instrument = instrument;
			return this;
		}
		
		// TODO: Make function robust
		/**
		 * Sets the octave of the {@link music.MusicPlayer} that will be built
		 * 
		 * @param octave the new octave that will be used
		 * @return the builder with the updated octave 
		 */
		public Builder octave(int octave) {
			this.octave = octave; // Make attribution more robust (private setter?)
			return this;
		}
		
		/**
		 * Builds the MusicPlayer created with the builder
		 * 
		 * @return the MusicPlayer built
		 */
		MusicPlayer build() {
			return new MusicPlayer(volume, instrument, octave);
		}
	}

	// TODO: Create method
	/**
	 * Changes the MusicPlayer's {@link music.Instrument} to the provided {@link music.Instrument}
	 * 
	 * @param instrument the {@link music.Instrument} to change the MusicPlayer's {@link music.Instrument} to
	 */
	public void changeInstrumentTo(Instrument instrument) {
		// TODO
	}

	/**
	 * Changes the MusicPlayer's {@link music.Instrument} to the {@link music.Instrument} of the provided midi code.
	 * 
	 * @param midiCode the midi code of the {@link music.Instrument} to change the MusicPlayer's {@link music.Instrument} to
	 */
	public void changeInstrumentTo(int midiCode) {
		changeInstrumentTo(Instrument.fromMidiCode(midiCode));
	}

	// TODO: Create method
	/**
	 * Plays the note provided
	 * 
	 * @param note the note to be played
	 */
	public void playNote(Note note) {
		// TODO
	}

	/**
	 * Gets the volume of the MusicPlayer
	 * 
	 * @return the volume of the MusicPlayer
	 */
	public double getVolume() {
		return volume;
	}

	// TODO: Make function robust
	/**
	 * Sets the volume of the MusicPlayer
	 * 
	 * @param volume the volume to be set
	 */
	public void setVolume(double volume) {
		this.volume = volume; // Make attribution more robust
	}

	/**
	 * Gets the octave of the MusicPlayer
	 * 
	 * @return the octave of the MusicPlayer
	 */
	public int getOctave() {
		return octave;
	}

	// TODO: Make function robust
	/**
	 * Sets the octave of the MusicPlayer
	 * 
	 * @param octave the octave to be set
	 */
	public void setOctave(int octave) {
		this.octave = octave; // Make attribution more robust
	}

	/**
	 * Gets the {@link music.Instrument} of the MusicPlayer
	 * 
	 * @return the {@link music.Instrument} of the MusicPlayer
	 */
	public Instrument getInstrument() {
		return instrument;
	}
	
}
