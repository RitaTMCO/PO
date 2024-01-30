package m19.app.requests;

// FIXME import core concepts
// FIXME import ui concepts
import pt.tecnico.po.ui.Command;
import m19.LibraryManager;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import m19.WorkNotWithUserException;
import m19.app.exceptions.WorkNotBorrowedByUserException;
import m19.NotExistUserException;
import m19.NotExistWorkException;
import m19.HasFineToPayException;
import m19.IsActiveException;
import m19.app.exceptions.NoSuchUserException;
import m19.app.exceptions.NoSuchWorkException;
import m19.app.exceptions.UserIsActiveException;
import m19.User;



/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {

  Input<Integer> _userId;
  Input<Integer> _workId;
  Input<String> _answer;
  /**
   * @param receiver
   */
  public DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    _workId = _form.addIntegerInput(Message.requestWorkId()); 
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.returnWork(_userId.value(), _workId.value());
    }
    catch(HasFineToPayException e){
      try{
        _display.popup(Message.showFine(_userId.value(), _receiver.fineValueFromLibrary(_userId.value())));
        _form.clear();
        _answer = _form.addStringInput(Message.requestFinePaymentChoice());
        _form.parse();
        _receiver.wantToPayFineFromLibrary(_answer.value(), _userId.value(), _workId.value());
        _form.clear();
        _userId = _form.addIntegerInput(Message.requestUserId());
        _workId = _form.addIntegerInput(Message.requestWorkId()); 
      }
      catch(NotExistUserException ex){
        throw new NoSuchUserException(_userId.value());
      }
      catch(NotExistWorkException ex){
        throw new NoSuchWorkException(_workId.value());
      }
      catch(IsActiveException ex){
        throw new UserIsActiveException(_userId.value());
      }

    }
    catch(WorkNotWithUserException e){
      throw new WorkNotBorrowedByUserException( _workId.value(), _userId.value());
    }
    catch(NotExistUserException e){
      throw new NoSuchUserException(_userId.value());
    }
    catch(NotExistWorkException e){
      throw new NoSuchWorkException(_workId.value());
    }
    
    
  }
}
