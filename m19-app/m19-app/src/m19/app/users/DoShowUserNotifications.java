package m19.app.users;

import m19.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import m19.Notification;
import m19.NotExistUserException;
import m19.app.exceptions.NoSuchUserException;
/**
 * 4.2.3. Show notifications of a specific user.
 */
public class DoShowUserNotifications extends Command<LibraryManager> {

  Input<Integer> _id;

  /**
   * @param receiver
   */
  public DoShowUserNotifications(LibraryManager receiver) {
    super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    _id=_form.addIntegerInput(Message.requestUserId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      for(Notification notification:_receiver.showUserNotifications(_id.value()))
        _display.addLine(notification.toString());
      _display.display();
      _receiver.clearAllUserNotifications(_id.value());
    }catch(NotExistUserException e){
      throw new NoSuchUserException(_id.value());
    }
  }
}
