package m19.app.main;

import m19.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import java.io.IOException;
import m19.exceptions.MissingFileAssociationException;
/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<LibraryManager> {
  
  private Input<String> _fileName;

  /**
   * @param receiver
   */
  public DoSave(LibraryManager receiver) {
    super(Label.SAVE, receiver);
  }
  

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try{
      _receiver.save();
    }
    catch(MissingFileAssociationException e){
      try{
        _fileName = _form.addStringInput(Message.newSaveAs());
        _form.parse();
        _receiver.saveAs(_fileName.value());
      }
      catch(MissingFileAssociationException | IOException ex){
        ex.printStackTrace();
      }
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
