package music;

public enum Instrument {
	ACOUSTIC_GRAND_PIANO(0);
	
	private final int midiCode;
	
	Instrument(int midiCode) {
		this.midiCode = midiCode;
	}
	
	public static Instrument fromMidiCode(int code) {
		for(Instrument instrument : Instrument.values()) {
			if(instrument.getMidiCode() == code) {
				return instrument;
			}
		}
		
		return null;
	}
	
	public int getMidiCode() {
		return midiCode;
	}
	
}
