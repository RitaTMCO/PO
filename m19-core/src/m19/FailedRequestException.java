package m19;

public class FailedRequestException extends Exception {
    private static final long serialVersionUID = 201901101348L;

    private int _index;

    public FailedRequestException(int index){
        _index = index;
    }

    public int getIndex(){
        return _index;
    }
}
