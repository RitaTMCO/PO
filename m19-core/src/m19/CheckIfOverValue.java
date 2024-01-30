package m19;

public class CheckIfOverValue extends Rule{

    @Override
    public  int canRequest(User user, Work work){
        if((work.getPrice() <= 25) || (work.getPrice()>25 && user.allowedOverPrice()))
            return 0;
        return 6;    
    }
} 