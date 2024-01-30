package m19;

public class CheckIfReference extends Rule{

    @Override
    public  int canRequest(User user, Work work){
        if(work.canRequestCategory())
            return 0;
        return 5;
    }

} 