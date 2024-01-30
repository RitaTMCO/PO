package m19.app;

import static pt.tecnico.po.ui.Dialog.IO;

import m19.LibraryManager;
import m19.exceptions.ImportFileException;

/**
 * Main driver for the library management application.
 */
public class App {
  /**
   * @param args
   */
  public static void main(String[] args) {
    LibraryManager mgr = new LibraryManager();

    String datafile = System.getProperty("import"); //$NON-NLS-1$
    if (datafile != null) {
      try {
        mgr.importFile(datafile);
      } catch (ImportFileException e) {
        // no behavior described: just present the problem
        e.printStackTrace();
      }
    }

    try {
      m19.app.main.Menu menu = new m19.app.main.Menu(mgr);
      menu.open();
    } finally {
      IO.close();
    }

  }

}
