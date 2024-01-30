package m19.app.requests;


import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import m19.FailedRequestException;


import m19.LibraryManager;
import pt.tecnico.po.ui.DialogException;
import m19.NotExistWorkException;
import m19.app.exceptions.NoSuchWorkException;
import m19.app.exceptions.RuleFailedException;
import m19.NotExistUserException;
import m19.NoCopiesException;
import m19.app.exceptions.NoSuchUserException;
import m19.User;
import m19.Work;


/**
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {

  Input<Integer> _userId;
  Input<Integer> _workId;
  Input<String> _answer;
  /**
   * @param receiver
   */
  public DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    _workId = _form.addIntegerInput(Message.requestWorkId()); 
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _display.popup(Message.workReturnDay(_workId.value(), _receiver.requestWork(_userId.value(),_workId.value()))); 
    }

    catch(FailedRequestException e){
      throw new RuleFailedException(_userId.value(), _workId.value(), e.getIndex());
    }

    catch(NoCopiesException e){
        try{
          _form.clear();
          _answer = _form.addStringInput(Message.requestReturnNotificationPreference());
          _form.parse();
          _receiver.wantNotificationReturnWorkFromLibrary(_answer.value(), _userId.value(), _workId.value());
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
      }
    
    catch(NotExistUserException e){
      throw new NoSuchUserException(_userId.value());
    }
    catch(NotExistWorkException e){
      throw new NoSuchWorkException(_workId.value());
    }
  }
}  


