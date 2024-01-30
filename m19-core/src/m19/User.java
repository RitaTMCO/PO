package m19;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class User implements Serializable,Observer{
    
    private int _id;
    private String _name;
    private String _email;
    private int _fine;
    /**
    * Number of consecutive devolutions before the deadline
    */
    private int _inDueTime;
    /**
    * Number of consecutive requests after the deadline
    */
    private int _outDueTime;
    private boolean _ative;
    private Behaviour _behaviour;
    private List<Notification> _notifications = new ArrayList<Notification>();
    private Map<Integer,WorkRequest> _workRequests = new HashMap <Integer,WorkRequest>(); 


    public User(int id,String name,String email){
        _id=id;
        _name=name;
        _email=email;
        _fine=0;
        _inDueTime=0;
        _outDueTime=0;
        _ative=true;
        _behaviour=Behaviour.NORMAL;
    }
    
    public int getId(){
        return _id;
    }
    
    public String getName(){
        return _name;
    }
    
    public String getEmail(){
        return _email;
    }    

    public int getFine(){
        return _fine;
    }

    public int getInDueTime(){
        return _inDueTime;
    }
    
    public int getOutDueTime(){
        return _outDueTime;
    }
    
    public boolean isAtive(){
        return _ative;
    }
    
    public Behaviour getBehaviour(){
        return _behaviour;
    }

    public List<Notification> getNotifications(){
        return _notifications;
    }
    public Collection<WorkRequest> getWorkRequests(){ 
        return Collections.unmodifiableCollection(_workRequests.values());
    }

    public void cleanNotifications(){
        _notifications.clear();
    }


  public WorkRequest getWorkRequest(int workId) throws WorkNotWithUserException{
    if(_workRequests.get(workId) == null)
        throw new WorkNotWithUserException();
    return _workRequests.get(workId);   
  }

    public int sizeOfWorkRequets(){
        return _workRequests.size();
    }

    public void setId(int id){
        _id=id;
    }
    
    public void setName(String name){
        _name=name;
    }
    
    public void setEmail(String email){
        _email=email;
    }    

    public void setFine(int fine){
        _fine=fine;
    }

    @Override
    public void update(Notification notification){
        _notifications.add(notification);
    }

    public void increaseInDueTime(){
        _inDueTime= _inDueTime + 1;
        if(_inDueTime==1)
            _outDueTime=0;       
    }
    
    public void increaseOutDueTime(){
        _outDueTime=_outDueTime + 1;
        if(_outDueTime==1)
            _inDueTime=0;      
    }

    public void chargeAtive(boolean ative){
        _ative=ative;
    }
    public void chargeBehaviour(Behaviour behaviour){
        _behaviour=behaviour;
    }

    public int maximumRequests(){
        return _behaviour.getNumberOfWorkRequested();
    }

    public boolean alreadyHasIt(Work work){
        for(WorkRequest _requests : getWorkRequests()){
            if(_requests.getWork() == work)
                return true;
        }
        return false;
    }

    public boolean hasReachedMaximumWorks(){
        return maximumRequests() > sizeOfWorkRequets();
    }

    public void addWorkRequest(Work work){
        _workRequests.put(work.getId(), new WorkRequest(dayRequests(work), work));
    }

    public void removeWorkRequest(Work work) throws WorkNotWithUserException, NotExistWorkException{ 
        _workRequests.remove(work.getId());
    }
    
    public int dayRequests(Work work){
        int copies=work.getCopies();
        
        if(copies==1) 
            return _behaviour.getDaysForWorkOneCopy();
            
        else if(copies<=5)
            return _behaviour.getDaysForWorkEqualLessFiveCopies();
        
        return _behaviour.getDaysForWorkMoreFiveCopies();
    }
     
    public boolean allowedOverPrice(){
        return _behaviour.getAllowedOverPrice();
    }

    public boolean noWorksToReturn(){
        for(WorkRequest _requests : getWorkRequests()){
            if(_requests.getDays() <0)
                return false;
        }
        return true;
    }
    public void advanceDateWorkRequest(int days){
        for(WorkRequest workRequest: getWorkRequests()){
            workRequest.setDays(workRequest.getDays()-days);
            if(workRequest.getDays()<0 && isAtive())
                _ative=false;
        }
    }

    @Override
    public String toString() {
        String s= _id + " - " + _name + " - " + _email + " - " + _behaviour + " - ";
        if(!isAtive()){
            s= s + "SUSPENSO " + "- " + "EUR " + _fine;
        }
        else
            s=s+"ACTIVO";
        return s;     
    }
}

