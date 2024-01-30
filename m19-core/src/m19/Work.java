package m19;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class Work implements Serializable,Observable{

    private int _id;
    /**
    * Total number of copies
    */
    private int _copies;
    /**
    * Number of available copies
    */
    private int _available;
    private String _titule;
    private int _price;
    private Category _category;
    private List<Observer> _interestedInReturn = new ArrayList<Observer>();
    private List<Observer> _interestedInRequest = new ArrayList<Observer>();

        
    public Work(int id,int copies,String titule,int price, Category category){
        _id=id;
        _copies=copies;
        _available=copies;
        _titule=titule;
        _price=price;
        _category=category;   
    }
    
    public abstract String type();
    public abstract String additionalInformation();
    public abstract boolean accept(Search search);


    public int getId(){
        return _id;
    }
    
    public int getCopies(){
        return _copies;
    }
    
    public int getAvailable(){
        return _available;
    }
    
    public String getTitule(){
        return _titule;
    }
    
    public int getPrice(){
        return _price;
    }
    
    public Category getCategory(){
        return _category;
    }
    
    public String stringCategory(){
        return _category.getStringCategory();
    }
    
    public void setId(int id){
        _id=id;
    }
    
    public void setCopies(int copies){
        _copies=copies;
    }
    
    public void setAvailable(int available){
        _available=available;
    }
    
    public void setTittle(String titule){
        _titule=titule;
    }
    
    public void setPrice(int price){
        _price=price;
    }
    
    public void setCategory(Category category){
        _category=category;
    }

    @Override
    public void registerObserver(Observer o,Event e){
        if(e==Event.ENTREGA)
            _interestedInReturn.add(o);
        else if(e==Event.REQUISIÇÃO)
            _interestedInRequest.add(o);
    }

    @Override
    public void removeObserver(Observer o, Event e){
        if(e==Event.ENTREGA)
            _interestedInReturn.remove(o);
        else if(e==Event.REQUISIÇÃO)
            _interestedInRequest.remove(o);
    }

    @Override
    public void notifyObservers(Notification n){
        if(n.getEvent()==Event.ENTREGA && !_interestedInReturn.isEmpty()){
            for(Observer observer : _interestedInReturn){
                observer.update(n);
            }
        }
        if(n.getEvent()==Event.REQUISIÇÃO && !_interestedInRequest.isEmpty()){
            for(Observer observer : _interestedInRequest){
                observer.update(n);
            }
        }
    }

    public void removeAllObserver(Event e){
        if(e==Event.ENTREGA)
            _interestedInReturn.clear();
        else if(e==Event.REQUISIÇÃO)
            _interestedInRequest.clear();    
    }

    @Override
    public String toString(){
        return getId() + " - " + getAvailable() + " de " + getCopies() + " - " + type() + " - " + getTitule() + " - " + getPrice() + " - " + stringCategory() + " - " + additionalInformation();
    }
    

    public boolean canRequestCategory(){
        return _category.getCanBeRequested();
        
    }
    
    public boolean stilHaveCopies(){
        return _available > 0;
    }
    
} 
