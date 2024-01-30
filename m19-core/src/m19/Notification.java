package m19;

import java.io.Serializable;

public class Notification implements Serializable{

    private Event _event;
    private String _descriptionWork;

    public Notification(Event event , String descriptionWork){
        _event=event;
        _descriptionWork=descriptionWork;
    }

    public Event getEvent(){
        return _event;
    }

    @Override
    public String toString(){
        return _event + ": " + _descriptionWork;
    }
}