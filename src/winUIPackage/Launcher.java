package winUIPackage;

import java.awt.Frame;


/**
*
* @author  SANTI DIAZ
*/
/**
* Launch the application.
*
*<P>Perform tasks in this order :
*<ul>
* <li>log basic system information 
* <li>promptly show a splash screen upon startup
* <li>show the main screen
* <li>remove the splash screen once the main screen is shown
*</ul>
*
* These tasks are performed in a thread-safe manner.
*/
public final class Launcher { 

  /**
  * Launch the application and display the main window.
  *
  * @param aArgs are ignored by this application, and may take any value.
  */
  public static void main (String... aArgs) {
    
    System.setProperty("file.encoding", "UTF-8");
    System.setProperty("user.language", "es");
    System.setProperty("user.country", "ES");
    
    try {
      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
    }
    
    /*
    * Implementation Note:
    *
    * Note that the launch thread of any GUI application is in effect an initial 
    * worker thread - it is not the event dispatch thread, where the bulk of processing
    * takes place. Thus, once the launch thread realizes a window, then the launch 
    * thread should almost always manipulate such a window through 
    * EventQueue.invokeLater. (This is done for closing the splash 
    * screen, for example.)
    */
    
    
    showMainWindow();
  }

  
  private static SplashScreen fSplashScreen;
  private static final String SPLASH_IMAGE = "loading.gif";

  /**
  * Show a simple graphical splash screen, as a quick preliminary to the main screen.
  */
  public static void showSplashScreen(){
    fSplashScreen = new SplashScreen(SPLASH_IMAGE);
    fSplashScreen.splash();
  }
  
  /**
  * Display the main window of the application to the user.
  */
  private static void showMainWindow(){
    FrmMDI mainWindow = new FrmMDI();
    mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
    mainWindow.setVisible(true);
  }

  /** FrmMDI
  * Removes the splash screen. 
  *
  * Invoke this <tt>Runnable</tt> using 
  * <tt>EventQueue.invokeLater</tt>, in order to remove the splash screen
  * in a thread-safe manner.
  */
  public static final class SplashScreenCloser implements Runnable {
    public void run(){
      fSplashScreen.dispose();
    }
  }
} 
