package m19;

import java.io.Serializable;

public class DVD extends Work implements Serializable{ 
    
    private String _director;
    private String _IGAC;
    
    public DVD(int id,int copies,String titule,int price, Category category,String director,String IGAC){
        super(id,copies,titule,price,category);
        _director=director;
        _IGAC=IGAC;
    }

    @Override
    public String type(){
        return "DVD";
    }

    @Override
    public String additionalInformation(){
        return getDirector() + " - " + getIGAC();
    }

    public String getDirector(){
        return _director;
    }
    
    public String getIGAC(){
        return _IGAC;
    }
    
    public void setDirector(String director){
        _director=director;
    }
    
    public void setIGAC(String IGAC){
        _IGAC=IGAC;
    }

    @Override
    public boolean accept(Search search){
        return search.visitDVD(this);
    }
    
} 
