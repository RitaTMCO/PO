package m19;

public class CheckIfActive extends Rule{

    @Override
    public  int canRequest(User user, Work work){
        if(user.isAtive())
            return 0;
        return 2;
        
    }

} 