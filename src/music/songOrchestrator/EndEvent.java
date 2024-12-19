package music.songOrchestrator;

import java.util.EventObject;

public class EndEvent extends EventObject {

    public EndEvent(Object source) {
        super(source);
    }
}