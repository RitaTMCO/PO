package m19.app.users;

import m19.LibraryManager;
import m19.NullAttributesUserException;
import m19.app.exceptions.UserRegistrationFailedException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
/**
 * 4.2.1. Register new user.
 */
public class DoRegisterUser extends Command<LibraryManager> {

  Input<String> _name;
  Input<String> _email;

  /**
   * @param receiver
   */
  public DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    _name=_form.addStringInput(Message.requestUserName());
    _email=_form.addStringInput(Message.requestUserEMail());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      int id=_receiver.registerNewUser(_name.value(),_email.value());
      _display.popup(Message.userRegistrationSuccessful(id));
        
    } catch(NullAttributesUserException e){
      throw new UserRegistrationFailedException(_name.value(),_email.value());
    }     
  }
}
