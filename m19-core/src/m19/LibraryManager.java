package m19;

import java.io.IOException;
import java.lang.ClassNotFoundException;
import m19.exceptions.MissingFileAssociationException;
import m19.exceptions.FailedToOpenFileException;
import m19.exceptions.ImportFileException;
import m19.exceptions.BadEntrySpecificationException;
  
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
  
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager {

  private Library _library = new Library();
  private String _filename;
  
  public int getCurrentDate(){
    return _library.getCurrentDate();
  }

  public void advanceDate(int nDays){
    _library.advanceDate(nDays);
  }      
  public String getFileName(){
    return _filename;
  }

  public void setFileName(String fileName){
    _filename = fileName;
  }
  
  public User getUserfromLibrary(int id) throws NotExistUserException{
    return _library.getUser(id);
  }
  
  public Work getWorkfromLibrary(int id) throws NotExistWorkException{
    return _library.getWork(id);
  }
  
  public List<User> getAllUsersfromLibrary(){
    return _library.getAllUsers();
  } 
  
  public Collection<Work> getAllWorksfromLibrary(){
    return _library.getAllWorks();
  } 
  
  public int registerNewUser(String name,String email) throws NullAttributesUserException{
    return _library.addUser(name,email);
  }

  public List<Notification> showUserNotifications(int id) throws NotExistUserException{
    return _library.getUserNotifications(id);
  }

  public void clearAllUserNotifications(int id) throws NotExistUserException{
    _library.cleanUserNotifications(id);
  }

  public String searching(String term){
    return _library.searchingTerm(term);
  }

  public int requestWork(int userId, int workId) throws NoCopiesException, FailedRequestException, NotExistUserException, NotExistWorkException{
    return _library.requestWork(userId, workId);
 }
  public void returnWork(int userId, int workId) throws NotExistWorkException, NotExistUserException,WorkNotWithUserException, HasFineToPayException{
    _library.returnWork(userId, workId);
  }
  public void wantNotificationReturnWorkFromLibrary(String answer, int _userId, int _workId) throws NotExistUserException, NotExistWorkException{
    _library.wantNotificationReturnWork(answer, _userId, _workId);
  }
  public void wantToPayFineFromLibrary(String answer, int _userId, int _workId) throws IsActiveException, NotExistUserException, NotExistWorkException{
    _library.wantToPayFine(answer, _userId, _workId);
  }
  public void payFineFromLibrary(int userId) throws IsActiveException, NotExistUserException{
    _library.payFine(userId);
  } 
  public int fineValueFromLibrary(int userId) throws NotExistUserException{
    return _library.fineValue(userId);
  }


  /**
   * @param file
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void save() throws MissingFileAssociationException, IOException {
    if(_filename==null)
      throw new MissingFileAssociationException();
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
    oos.writeObject(_library);
    oos.close();
  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws FailedToOpenFileExceptiqon
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void load(String filename) throws FailedToOpenFileException, IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
    _library = (Library) ois.readObject();
    ois.close();
    if(_library == null){
      throw new FileNotFoundException(filename);
    }
    _filename=filename;
  }


  /**
   * @param datafile
   * @throws ImportFileException
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _library.importFile(datafile);
    } catch (IOException | BadEntrySpecificationException e) {
      throw new ImportFileException(e);
    }
  }
}
