package main;

/**
 * The main class of a simple calculator. Create one of these and you'll
 * get the calculator on screen.
 * 
 * @author Rob Shelly
 * @version 31 July 2000
 */
public class Calculator
{
	private CalculatorEngine engine;
	private UserInterface gui;

	/**
	 * Create a new calculator and show it.
	 */
	public Calculator()
	{
		engine = new CalculatorEngine();
		gui = new UserInterface(engine);
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
		Calculator cTest;

		cTest = new Calculator();
		while(true);
	}
}