package m19;

public class CheckCopies extends Rule{

    @Override
    public  int canRequest(User user, Work work){
        if(work.stilHaveCopies())
            return 0;
        return 3;
    }

} 