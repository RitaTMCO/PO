package m19;

public class CheckIfAlreadyHasIt extends Rule{

    @Override
    public  int canRequest(User user, Work work){
        if(!user.alreadyHasIt(work))
            return 0;
        return 1;
    }

} 