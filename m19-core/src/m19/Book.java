package m19;

import java.io.Serializable;

public class Book extends Work implements Serializable{

    private String _writer;
    private String _ISBN;
    
    public Book(int id,int copies,String titule,int price, Category category,String writer,String ISBN){
        super(id,copies,titule,price,category);
        _writer=writer;
        _ISBN=ISBN;
    }

    @Override
    public String type(){
        return "Livro";
    }
    
    @Override
    public String additionalInformation(){
        return getWriter() + " - " + getISBN();
    }
    
    public String getWriter(){
        return _writer;
    }
    
    public String getISBN(){
        return _ISBN;
    }
    
    public void setWriter(String writer){
        _writer=writer;
    }
    
    public void setISBN(String ISBN){
        _ISBN=ISBN;
    }
    @Override
    public boolean accept(Search search){
        return search.visitBook(this);
    }
} 
