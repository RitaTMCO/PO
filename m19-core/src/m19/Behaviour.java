package m19;


import java.io.Serializable;

public enum Behaviour implements Serializable{
    
    NORMAL(3, 3, 8, 15, false),
    CUMPRIDOR(5, 8, 15, 30,  true),
    FALTOSO(1, 2, 2, 2, false);
    
    private int _numberOfWorkRequested;
    private int _daysForWorkOneCopy;
    private int _daysForWorkEqualLessFiveCopies;
    private int _daysForWorkMoreFiveCopies;
    private boolean _allowedOverPrice;
    
    Behaviour(int numberOfWorkRequested, int daysForWorkOneCopy, int daysForWorkEqualLessFiveCopies, int daysForWorkMoreFiveCopies, boolean allowedOverPrice){
        _numberOfWorkRequested= numberOfWorkRequested;
        _daysForWorkOneCopy=daysForWorkOneCopy;
        _daysForWorkEqualLessFiveCopies=daysForWorkEqualLessFiveCopies;
        _daysForWorkMoreFiveCopies=daysForWorkMoreFiveCopies;
        _allowedOverPrice=allowedOverPrice;
    }
    
    public int getNumberOfWorkRequested() { return _numberOfWorkRequested;}
    public int getDaysForWorkOneCopy() {return _daysForWorkOneCopy;}
    public int getDaysForWorkEqualLessFiveCopies() { return _daysForWorkEqualLessFiveCopies;}
    public int getDaysForWorkMoreFiveCopies() {return _daysForWorkMoreFiveCopies;}
    public boolean getAllowedOverPrice() {return _allowedOverPrice;}
}
