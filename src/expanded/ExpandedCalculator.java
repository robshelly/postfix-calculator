package expanded;

/**
 * The main class of a simple calculator. Create one of these and you'll
 * get the calculator on screen.
 * 
 * @author Rob Shelly
 * @version 31 July 2000
 */
public class ExpandedCalculator
{
	// TODO Delete
	//private CalcEngine engine;
	private ExpandedCalculatorEngine engine;
	private ExpandedUserInterface gui;

	/**
	 * Create a new calculator and show it.
	 */
	public ExpandedCalculator()
	{
		engine = new ExpandedCalculatorEngine();
		gui = new ExpandedUserInterface(engine);
	}

	/**
	 * In case the window was closed, show it again.
	 */
	public void show()
	{
		gui.setVisible(true);
	}

	public static void main(String[] args)
	{
		ExpandedCalculator cTest;

		cTest = new ExpandedCalculator();
		while(true);
	}
}