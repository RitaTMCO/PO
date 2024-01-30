package m19;

import java.io.Serializable;

public abstract class Rule implements Serializable{
    public abstract int canRequest(User user, Work work);
}