package music;

public class MusicPlayer {
	
	private double volume;
	private Instrument instrument;
	private int octave;
	
	private MusicPlayer(double volume, Instrument instrument, int octave) {
		this.volume = volume;
		this.instrument = instrument;
		this.octave = octave;
	}
	
	private class Builder {
		private double volume;
		private Instrument instrument;
		private int octave;
		
		public Builder() {
			this.volume = 100;
			this.instrument = Instrument.ACOUSTIC_GRAND_PIANO;
			this.octave = 1;
		}
		
		Builder volume(double volume) {
			this.volume = volume;
			return this;
		}
		
		Builder instrument(Instrument instrument) {
			this.instrument = instrument;
			return this;
		}
		
		Builder octave(int octave) {
			this.octave = octave;
			return this;
		}
		
		MusicPlayer build() {
			return new MusicPlayer(volume, instrument, octave);
		}
	}
	
	public void changeInstrumentTo(Instrument instrument) {
		
	}
	
	public void changeInstrumentTo(int midiCode) {
		
	}
	
	public void playNote(Note note) {
		
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public Instrument getInstrument() {
		return instrument;
	}
	
}
