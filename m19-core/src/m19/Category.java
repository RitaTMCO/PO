package m19;

import java.io.Serializable;

public enum Category implements Serializable{

    REFERENCE(false, "Referência"),
    FICTION(true, "Ficção"),
    SCITECH(true, "Técnica e Científica");
        
    private boolean _canBeRequested;
    private String _stringCategory;
        
    Category(boolean canBeRequested,String stringCategory){
        _canBeRequested=canBeRequested;
        _stringCategory=stringCategory;
    }
        
    public boolean getCanBeRequested(){ return _canBeRequested;}
    public String getStringCategory() {return _stringCategory;}
}
