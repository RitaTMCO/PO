package m19;

public interface Observable{
    public void registerObserver(Observer o,Event e);
    public void removeObserver(Observer o, Event e);
    public void notifyObservers(Notification n);
}
