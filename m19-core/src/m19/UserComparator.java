package m19;

import java.util.Comparator;
import java.io.Serializable;

public class UserComparator implements Comparator<User>, Serializable{
  /**
   * @param user1
   * 
   * @param user2
   * 
   * @return dif
   *    difference between user and user name's first letters; if they 
   *    are equal, difference between their Id's number
   */
    @Override
    public int compare(User user1, User user2){
        int dif;
        dif = user1.getName().compareTo(user2.getName());
        if(dif==0)
            dif=user1.getId()-user2.getId();
        return dif;  
    }
}
