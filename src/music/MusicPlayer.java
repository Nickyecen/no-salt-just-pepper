package music;

import javax.sound.midi.MidiUnavailableException;

import org.jfugue.realtime.RealtimePlayer;

/**
 * Class of a music player that can play notes using JFugue
 *
 * @author nickyecen
 */
public class MusicPlayer {

	public static final int MAX_VOLUME = 127;
	public static final int MIN_VOLUME = 0;
	public static final int DEFAULT_VOLUME = (MAX_VOLUME - MIN_VOLUME) / 3;

	public static final int MAX_OCTAVE = 10;
	public static final int MIN_OCTAVE = 0;
	public static final int DEFAULT_OCTAVE = 5;

	public static final Instrument DEFAULT_INSTRUMENT = Instrument.ACOUSTIC_GRAND_PIANO;

	private static final int NOTE_LENGTH_IN_MS = 50;

	private final RealtimePlayer player;

	private double previousVolume; // TODO: Make volume code robust, eg: I shouldn't be allowed to set the volume to -1
	private Instrument previousInstrument;
	private int previousOctave; // TODO: Make setter robust

	private int volume; // TODO: Make volume code robust, eg: I shouldn't be allowed to set the volume to -1
	private Instrument instrument;
	private int octave; // TODO: Make setter robust

	private NoteListener noteListener;
	private InstrumentListener instrumentListener;
	private VolumeListener volumeListener;

	public void setNoteListener(NoteListener listener) {
		this.noteListener = listener;
	}

	public void notifyNoteListener(Note note, int octave) {
		if (noteListener != null) {
			music.NoteEvent event = new music.NoteEvent(this, note, octave);
			noteListener.onNoteEvent(event);
		}
	}

	public void setInstrumentListener(InstrumentListener listener) {
		this.instrumentListener = listener;
	}

	public void notifyInstrumentListener(Instrument instrument) {
		if (instrumentListener != null) {
			InstrumentEvent event = new InstrumentEvent(this, instrument);
			instrumentListener.onInstrumentEvent(event);
		}
	}

	public void setVolumeListener(VolumeListener listener) {
		this.volumeListener = listener;
	}

	public void notifyVolumeListener(int volume) {
		if (volumeListener != null) {
			VolumeEvent event = new VolumeEvent(this, volume);
			volumeListener.onVolumeEvent(event);
		}
	}

	// TODO: Make code more robust
	/**
	 * Constructs a MusicPlayer with a defined volume, {@link music.Instrument} and octave
	 *
	 * @param volume the volume of how loud the {@link music.Note} will be
	 * @param instrument the {@link music.Instrument} that the {@link music.Note} will use
	 * @param octave the octave in which each {@link music.Note} will be played
	 * @throws MidiUnavailableException
	 */
	private MusicPlayer(int volume, Instrument instrument, int octave) throws MidiUnavailableException {
		this.player = new RealtimePlayer();
		this.setVolume(volume);
		this.instrument = instrument;
		this.setOctave(octave);
	}

	/**
	 * A builder class for {@link music.MusicPlayer} to be built for better readability
	 *
	 * @see music.MusicPlayer
	 */
	public static class Builder {
		private int volume;
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
		public Builder volume(int volume) {
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
		 * Builds the {@link music.MusicPlayer} created with the builder
		 *
		 * @return the {@link music.MusicPlayer} built
		 */
		public MusicPlayer build() {
			try {
				return new MusicPlayer(volume, instrument, octave);
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// TODO: Create method
	/**
	 * Changes the {@link music.MusicPlayer}'s {@link music.Instrument} to the provided {@link music.Instrument}
	 *
	 * @param instrument the {@link music.Instrument} to change the {@link music.MusicPlayer}'s {@link music.Instrument} to
	 */
	public void setInstrument(Instrument instrument) {
		this.previousInstrument = this.instrument;
		this.instrument = instrument;
		notifyInstrumentListener(instrument);
		player.changeInstrument(instrument.getMidiCode());
	}

	/**
	 * Changes the {@link music.MusicPlayer}'s {@link music.Instrument} to the {@link music.Instrument} of the provided midi code.
	 *
	 * @param midiCode the midi code of the {@link music.Instrument} to change the {@link music.MusicPlayer}'s {@link music.Instrument} to
	 */
	public void setInstrument(int midiCode) {
		setInstrument(Instrument.fromMidiCode((midiCode - 1) % 128 + 1));
	}

	// TODO: Create method
	/**
	 * Plays the note provided
	 *
	 * @param note the note to be played
	 */
	public void playNote(Note note) {
		notifyNoteListener(note, octave);
		String noteString = note.toString() + String.valueOf(octave);
		player.startNote(new org.jfugue.theory.Note(noteString));
		try {
			Thread.sleep(NOTE_LENGTH_IN_MS);
		} catch (InterruptedException e) {
			System.err.println("Music Player note sleep interrupted");
			e.printStackTrace();
		}
		player.stopNote(new org.jfugue.theory.Note(noteString));
	}

	/**
	 * Gets the volume of the {@link music.MusicPlayer}
	 *
	 * @return the volume of the {@link music.MusicPlayer}
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * Gets the maximum volume of {@link music.MusicPlayer}
	 *
	 * @return the maximum volume of {@link music.MusicPlayer}
	 */
	public static int getMaxVolume() {
		return MAX_VOLUME;
	}

	/**
	 * Gets the minimum volume of {@link music.MusicPlayer}
	 *
	 * @return the minimum volume of {@link music.MusicPlayer}
	 */
	public static int getMinVolume() {
		return MIN_VOLUME;
	}

	/**
	 * Gets the default volume of {@link music.MusicPlayer}
	 *
	 * @return the default volume of {@link music.MusicPlayer}
	 */
	public static int getDefaultVolume() {
		return DEFAULT_VOLUME;
	}

	/**
	 * Gets the maximum octave of the {@link music.MusicPlayer}
	 *
	 * @return the maximum octave of the {@link music.MusicPlayer}
	 */
	public static int getMaxOctave() {
		return MAX_OCTAVE;
	}

	/**
	 * Gets the minimum octave of the {@link music.MusicPlayer}
	 *
	 * @return the minimum octave of the {@link music.MusicPlayer}
	 */
	public static int getMinOctave() {
		return MIN_OCTAVE;
	}

	/**
	 * Gets the default octave of {@link music.MusicPlayer}
	 *
	 * @return the default octave of {@link music.MusicPlayer}
	 */
	public static int getDefaultOctave() {
		return DEFAULT_OCTAVE;
	}

	/**
	 * Sets the volume of the {@link music.MusicPlayer}
	 *
	 * @param volume the volume to be set
	 */
	public void setVolume(int volume) {
		if(volume > MAX_VOLUME) {
			this.volume = MAX_VOLUME;
		} else if(volume < MIN_VOLUME) {
			this.volume = MIN_VOLUME;
		} else {
			this.volume = volume;
		}

		notifyVolumeListener(this.volume);
		player.changeController((byte) 7, (byte) this.volume);
	}

	/**
	 * Gets the octave of the {@link music.MusicPlayer}
	 *
	 * @return the octave of the {@link music.MusicPlayer}
	 */
	public int getOctave() {
		return octave;
	}

	/**
	 * Sets the octave of the {@link music.MusicPlayer}
	 *
	 * @param octave the octave to be set
	 */
	public void setOctave(int octave) {
		if(octave > MAX_OCTAVE) {
			this.octave = MAX_OCTAVE;
		} else if(octave < MIN_OCTAVE) {
			this.octave = MIN_OCTAVE;
		} else {
			this.octave = octave;
		}

	}

	/**
	 * Gets the {@link music.Instrument} of the {@link music.MusicPlayer}
	 *
	 * @return the {@link music.Instrument} of the {@link music.MusicPlayer}
	 */
	public Instrument getInstrument() {
		return instrument;
	}

	/**
	 * Gets the previous volume of the {@link music.MusicPlayer}
	 *
	 * @return the previous volume of the {@link music.MusicPlayer}
	 */
	public double getPreviousVolume() {
		return previousVolume;
	}

	/**
	 * Gets the previous {@link music.Instrument} of the {@link music.MusicPlayer}
	 *
	 * @return the previous {@link music.Instrument} of the {@link music.MusicPlayer}
	 */
	public Instrument getPreviousInstrument() {
		return previousInstrument;
	}

	/**
	 * Gets the previous octave of the {@link music.MusicPlayer}
	 *
	 * @return the previous octave of the {@link music.MusicPlayer}
	 */
	public int getPreviousOctave() {
		return previousOctave;
	}

}
