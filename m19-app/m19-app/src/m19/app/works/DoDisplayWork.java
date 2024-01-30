package m19.app.works;

import m19.LibraryManager;
import m19.Work;
import m19.NotExistWorkException;
import m19.app.exceptions.NoSuchWorkException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;

/**
 * 4.3.1. Display work.
 */
public class DoDisplayWork extends Command<LibraryManager> {

  Input<Integer> _id;

  /**
   * @param receiver
   */
  public DoDisplayWork(LibraryManager receiver) {
    super(Label.SHOW_WORK, receiver);
    _id = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      Work work=_receiver.getWorkfromLibrary(_id.value());
      _display.popup(work.toString());
    } catch(NotExistWorkException e){
      throw new NoSuchWorkException(_id.value());
    }
  }
}
