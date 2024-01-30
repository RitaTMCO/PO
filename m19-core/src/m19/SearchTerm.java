package m19;

public class SearchTerm implements Search{
    private String _term;

    public SearchTerm(String term){
        _term=term;
    }

    public boolean containsTerm(String fields){
        fields=fields.toLowerCase();
        return fields.contains(_term.toLowerCase());
    }

    @Override
    public boolean visitBook(Book book){
        String fields= book.getTitule() + " " + book.getWriter();
        return containsTerm(fields);
    }

    @Override
    public boolean visitDVD(DVD dvd){
        String fields= dvd.getTitule() + " " + dvd.getDirector();
        return containsTerm(fields);
    }
} 
