package m19;


public class CheckRequestedNow extends Rule{

    @Override
    public  int canRequest(User user, Work work){
        if(user.hasReachedMaximumWorks())
            return 0;
        return 4;
    }

} 