package m19.app.main;

import pt.tecnico.po.ui.Command;
import m19.LibraryManager;
import pt.tecnico.po.ui.DialogException;
import m19.app.exceptions.FileOpenFailedException;
import m19.exceptions.FailedToOpenFileException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import pt.tecnico.po.ui.Input;
import java.io.FileNotFoundException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<LibraryManager> {
  private Input<String> _fileName;
  /**
   * @param receiver
   */
  public DoOpen(LibraryManager receiver) {
    super(Label.OPEN, receiver);
    _fileName = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.load(_fileName.value());
    } catch (FileNotFoundException fnfe){
      throw new FileOpenFailedException(_fileName.value());
    } catch (ClassNotFoundException | IOException | FailedToOpenFileException e) {
      e.printStackTrace();
    }
  }
}
