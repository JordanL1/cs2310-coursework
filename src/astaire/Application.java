package astaire;

/**
 * An Application class which will start the dance program's UI when executed.
 * 
 * @author Jordan Lees
 * @version 13/12/18
 */

public class Application {

	public static void main(String[] args) {
		DanceManager dm = new DanceManager();
		TUI tui = new TUI(dm);

	}

}
