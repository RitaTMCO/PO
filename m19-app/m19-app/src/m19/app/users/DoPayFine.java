package m19.app.users;


// FIXME import core concepts
// FIXME import ui concepts
import m19.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import m19.app.exceptions.UserIsActiveException;
import m19.NotExistUserException;
import m19.app.exceptions.NoSuchUserException;
import m19.IsActiveException;

/**
 * 4.2.5. Settle a fine.
 */
public class DoPayFine extends Command<LibraryManager> {

  Input<Integer> _userId;

  
  /**
   * @param receiver
   */
  public DoPayFine(LibraryManager receiver) {
    super(Label.PAY_FINE, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.payFineFromLibrary(_userId.value());
    }
    catch(IsActiveException e){
      throw new UserIsActiveException(_userId.value());

    }
   catch(NotExistUserException e){
      throw new NoSuchUserException(_userId.value());
    }
  }
}
