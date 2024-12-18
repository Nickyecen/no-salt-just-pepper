package music;

import java.util.EventObject;

public class NoteEvent extends EventObject {
    private Note note;
    private int octave;

    public NoteEvent(Object source, Note note, int octave) {
        super(source);
        this.note = note;
        this.octave = octave;
    }

    public String getNote() {
        return note + Integer.toString(octave);
    }
}