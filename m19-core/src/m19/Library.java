package m19;

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.lang.Integer;
import m19.exceptions.BadEntrySpecificationException;


/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201901101348L;
  private Map<Integer,User> _users = new TreeMap <Integer,User>();
  private Map<Integer,Work> _works = new TreeMap <Integer,Work>(); 

  RulesCollection _rules = new RulesCollection();
  
  private int _workNumber;   
  private int _userNumber;
  private int _currentDate;


  public Library(){
    _workNumber=0;
    _userNumber=0;
    _currentDate=0;
    _rules.addRule(new CheckIfAlreadyHasIt());
    _rules.addRule(new CheckIfActive());
    _rules.addRule(new CheckCopies());
    _rules.addRule(new CheckRequestedNow());
    _rules.addRule(new CheckIfReference());
    _rules.addRule(new CheckIfOverValue());
  }
  
  /**
   * @return current date
   */
  public int getCurrentDate(){
    return _currentDate;
  }

  /**
   * This method advances the current date according to number of 
   * days passed as an argument.
   * 
   * @param nDays
   *    days to advance 
   */
  public void advanceDate(int nDays){
    if(nDays > 0)
      _currentDate+= nDays;
      for(User user :getAllUsers())
        user.advanceDateWorkRequest(nDays);
  }

  /**
   * Dispose a new user on the library, except in the cases that it receives the empty name
   * and/or emails.
   * @param name
   *        Username
   * @param email
   *        User email
   * @return id
   *        New user id
   * @throws NullAttributesUserException
   */
  public int addUser(String name, String email) throws NullAttributesUserException{
    int id;
    if(name.compareTo("")==0 || email.compareTo("")==0){
      throw new NullAttributesUserException();
    }
    User u= new User(_userNumber,name,email);
    id=_userNumber;
    _users.put(u.getId(),u);
    _userNumber++;
    return id;
  }


  /**
   * Dispose a book on the library.
   * @param titule
   *        Book title
   * @param writer
   *        Book writer
   * @param price 
   *        Book price
   * @param Category
   *        Book category
   * @param ISBN
   *        Book ISBN
   * @param copies
   *        Number of book copies
   */
  public void addBook(String titule,String writer,int price,Category category,String ISBN,int copies){
    Book b= new Book (_workNumber,copies,titule,price,category,writer,ISBN);
    _works.put(b.getId(),b);
    _workNumber++;
  }

  /**
   * Dispose a DVD on the library.
   * @param titule
   *        DVD title
   * @param director
   *        DVD director
   * @param price 
   *        DVD price
   * @param Category
   *        DVD category
   * @param IGAC
   *        DVD IGAC
   * @param copies
   *        Number of DVD copies
   */
  public void addDVD(String titule,String director,int price,Category category,String IGAC,int copies){
    DVD d= new DVD (_workNumber,copies,titule,price,category,director,IGAC);
    _works.put(d.getId(),d);
    _workNumber++;
  }

  /**
   * Input an id and returns the user with the received id if it exists
   * @param id
   * @return the user with the inputted id
   * @throws NotExistUserException
   */
  
  public User getUser(int id) throws NotExistUserException{ 
    if(_users.get(id)==null)
      throw new NotExistUserException();
    return _users.get(id);
  }
  
  /**
   * Input an id and returns the work with the received id if it exists
   * @param id
   * @return the work with the inputted id
   * @throws NotExistWorkException
   */
  public Work getWork(int id) throws NotExistWorkException{ 
    if(_works.get(id)==null)
      throw new NotExistWorkException();
    return _works.get(id);
  }


  /**
   * @return All the users existent in the library
   */
  
  public List<User> getAllUsers(){
     List<User> listUsers = new ArrayList<User>();
     listUsers.addAll(_users.values());
     Collections.sort(listUsers,new UserComparator());
     return listUsers;
  }

  /**
   * @return All the works existent in the library
   */
  public Collection<Work> getAllWorks(){
    return Collections.unmodifiableCollection(_works.values());
  }

  public List<Notification> getUserNotifications(int id) throws NotExistUserException{
    User user=getUser(id); 
    return user.getNotifications();
  }

  public void cleanUserNotifications(int id) throws NotExistUserException{
    User user=getUser(id); 
    user.cleanNotifications();
  }

  public String searchingTerm(String term){
    Search search= new SearchTerm(term);
    String searching="";
    for(Work work:getAllWorks()){
      if(work.accept(search))
        searching+=work.toString() + "\n";
    }
    return searching;
  }

  public int requestWork(int userId, int workId ) throws NoCopiesException, FailedRequestException, NotExistUserException, NotExistWorkException{
    User user = getUser(userId);
    Work work = getWork(workId);
    int index = _rules.canRequest(user, work);
    if(index == 3)
      throw new NoCopiesException();
    else if(index > 0)
      throw new FailedRequestException(index);
    work.setAvailable(work.getAvailable()-1);
    user.addWorkRequest(work);
    work.notifyObservers(new Notification(Event.REQUISIÇÃO, work.toString()));
    work.removeAllObserver(Event.REQUISIÇÃO);
    return getCurrentDate() + user.dayRequests(work);
  }

  public void wantNotificationReturnWork(String answer, int _userId, int _workId) throws NotExistUserException, NotExistWorkException{
    User user = getUser(_userId);
    Work work = getWork(_workId);
    if(answer.compareTo("s") == 0)
      work.registerObserver(user,Event.ENTREGA);
  }

  public void returnWork(int userId, int workId) throws HasFineToPayException, NotExistUserException, NotExistWorkException, WorkNotWithUserException{

    User user = getUser(userId);
    Work work = getWork(workId);
    WorkRequest workRequest = user.getWorkRequest(workId);
    user.removeWorkRequest(work);
    work.setAvailable(work.getAvailable()+1);
    work.notifyObservers(new Notification(Event.ENTREGA, work.toString()));
    work.removeAllObserver(Event.ENTREGA);

    if(workRequest.getDays() <0){
      int fine = Math.abs((workRequest.getDays() * 5)); 
      user.setFine(user.getFine()+fine);
      user.increaseOutDueTime();
      if(user.getOutDueTime() == 3)
        user.chargeBehaviour(Behaviour.FALTOSO);
      throw new HasFineToPayException();
    }
    user.increaseInDueTime();
    if(user.getInDueTime() == 3 && user.getBehaviour() == Behaviour.FALTOSO)
      user.chargeBehaviour(Behaviour.NORMAL);
    else if (user.getInDueTime() == 5)
      user.chargeBehaviour(Behaviour.CUMPRIDOR);
  }

  public int fineValue(int userId) throws NotExistUserException{
    User user = getUser(userId);
    return user.getFine();
  }

  public void wantToPayFine(String answer, int _userId, int _workId) throws IsActiveException, NotExistUserException, NotExistWorkException{
    if(answer.compareTo("s") == 0)
      payFine(_userId);
  }

  public void payFine(int userId) throws IsActiveException, NotExistUserException{
    User user = getUser(userId);
    if(user.isAtive())
      throw new IsActiveException();
    user.setFine(0);
    if(user.noWorksToReturn())
      user.chargeAtive(true);
  }

  /**
   * It creates the object user and adds it in the library
   * @param fields
   * @throws NullAttributesUserException
   * @throws UnknownDataException
   */
  public void registerUser(String ...fields) throws NullAttributesUserException, UnknownDataException{
    if (fields[0].equals("USER")){
      addUser(fields[1], fields[2]);
    }
    else
      throw new UnknownDataException();
  }

  /**
   * It creates the category a returns it
   * @param categoryFields
   * @throws UnknownDataException
   */
  public Category obtainCatogory(String categoryFields) throws UnknownDataException{

    if (categoryFields.equals("REFERENCE")){
      return Category.REFERENCE;
    }
    else if(categoryFields.equals("FICTION")){
      return Category.FICTION;
    }
    else if(categoryFields.equals("SCITECH")){
      return Category.SCITECH;
    }
    else
      throw new UnknownDataException();
  }

  /**
   * It creates the object work and adds it in the library
   * @param fields
   * @throws UnknownDataException
   */
  public void registerWork(String ...fields) throws UnknownDataException{

    Category category= obtainCatogory(fields[4]);
    int price=Integer.parseInt(fields[3]);
    int copies=Integer.parseInt(fields[6]);
    
    if(fields[0].equals("BOOK")){
      addBook(fields[1],fields[2],price,category,fields[5],copies);
    }
    else if(fields[0].equals("DVD")){
      addDVD(fields[1],fields[2],price,category,fields[5],copies);
    }
    else
      throw new UnknownDataException();
  }

  /**
   * It creates the relevant entities in the library
   * @param fields
   * @throws NullAttributesUserException
   * @throws UnknownDataException
   */
  public void registerFields(String fields[])throws NullAttributesUserException, UnknownDataException{
    Pattern patUser=Pattern.compile("^(USER)");
    Pattern patWork=Pattern.compile("^(BOOK|DVD)");
    Pattern patCategory=Pattern.compile("^(REFERENCE|FICTION|SCITECH)");
    
    if (patUser.matcher(fields[0]).matches()) 
      registerUser(fields);
    else if(patWork.matcher(fields[0]).matches() && patCategory.matcher(fields[4]).matches()){
      registerWork(fields);
    }
    else 
      throw new UnknownDataException();         
  }

    
  /**
   * Read the text input file at the beginning of the program and populates the
   * instances of the various possible types (books, DVDs, users).
   * 
   * @param filename
   *          name of the file to load
   * @throws BadEntrySpecificationException
   * @throws IOException
   */
  void importFile(String filename) throws BadEntrySpecificationException, IOException { 
    try{
      BufferedReader in = new BufferedReader(new FileReader(filename));
      String line;

      while((line = in.readLine()) != null){
        String[] fields = line.split(":");
        registerFields(fields);
      }
      in.close();

    } catch(FileNotFoundException e){
      throw new BadEntrySpecificationException(filename);
    } catch(UnknownDataException e){
      e.printStackTrace();
    } catch(NullAttributesUserException e){
      e.printStackTrace();
    }
  } 
}
