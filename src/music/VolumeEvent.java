package music;

import java.util.EventObject;

public class VolumeEvent extends EventObject {
    private int volume;

    public VolumeEvent(Object source, int volume) {
        super(source);
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }
}