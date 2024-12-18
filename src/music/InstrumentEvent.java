package music;

import java.util.EventObject;

public class InstrumentEvent extends EventObject {
    private Instrument instrument;

    public InstrumentEvent(Object source, Instrument instrument) {
        super(source);
        this.instrument = instrument;
    }

    public String getInstrument() {
        return instrument.toString();
    }
}