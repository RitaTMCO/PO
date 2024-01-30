package m19;

import java.io.Serializable;

public class WorkRequest implements Serializable{
    private int _days;
    private Work _work;

    public WorkRequest(int days, Work work){
        _days = days;
        _work = work;
    }

    public int getDays(){
        return _days;
    }

    public Work getWork(){
        return _work;
    }

    public void setDays(int days){
        _days=days;
    }
}
