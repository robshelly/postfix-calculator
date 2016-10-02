package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the calculator. No calculation is being
 * done here. This class is responsible just for putting up the expression on 
 * screen. It then refers to the "CalculatorEngine" to do all the real work.
 * 
 * @author Rob Shelly
 * @version  16 February 2016
 */
public class UserInterface
	implements ActionListener
{

	private CalculatorEngine calc;
	private boolean showingAuthor;

  private JFrame frame;
  private JTextField answer;
	private JTextField expression;
	private JTextField postfix;
	private JLabel status;

	/**
	 * Create a user interface for a given calcEngine.
	 */
	public UserInterface(CalculatorEngine engine)
	{
		calc = engine;
		showingAuthor = false;
		makeFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Make this interface visible again. (Has no effect if it is already
	 * visible.)
	 */
    public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}

	/**
	 * Make the frame for the user interface.
	 */
	private void makeFrame()
	{
		frame = new JFrame(calc.getTitle());
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));


		JPanel display = new JPanel();
		display.setLayout(new BorderLayout());
		
		expression = new JTextField();
		// TODO Enable this line for handup
		// expression.setEditable(false);
		expression.setBackground(Color.WHITE);
		display.add(expression, BorderLayout.NORTH);
		
		postfix = new JTextField();
		postfix.setEditable(false);
		display.add(postfix, BorderLayout.CENTER);
		
		answer = new JTextField("0");
		answer.setEditable(false);
		answer.setHorizontalAlignment(JTextField.RIGHT);
		display.add(answer, BorderLayout.SOUTH);
		
		contentPane.add(display, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(5, 5));
			// Top Row
			addButton(buttonPanel, "C");	
			addButton(buttonPanel, "(");
			addButton(buttonPanel, ")");
			addButton(buttonPanel, "/");
			// New Row
			addButton(buttonPanel, "7");
			addButton(buttonPanel, "8");
			addButton(buttonPanel, "9");
			addButton(buttonPanel, "*");
			

			// New Row
			addButton(buttonPanel, "4");
			addButton(buttonPanel, "5");
			addButton(buttonPanel, "6");
			addButton(buttonPanel, "+");
			
			//New Row
			addButton(buttonPanel, "1");
			addButton(buttonPanel, "2");
			addButton(buttonPanel, "3");
			addButton(buttonPanel, "-");
			
			// Bottom Row
			addButton(buttonPanel, "0");
			addButton(buttonPanel, ".");
			addButton(buttonPanel, "^");
			addButton(buttonPanel, "=");
		contentPane.add(buttonPanel, BorderLayout.CENTER);

		status = new JLabel(calc.getVersion());
		contentPane.add(status, BorderLayout.SOUTH);

		frame.pack();
	}

	/**
	 * Add a button to the button panel.
	 */
	private void addButton(Container panel, String buttonText)
	{
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		panel.add(button);
	}

	/**
	 * An interface action has been performed. Find out what it was and
	 * handle it.
	 */
	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();

		if (command.equals("=")) {
			try {
				calc.evaluate(expression.getText());
				answer.setText( "" + calc.answer);
				postfix.setText(calc.postfixExp);
			} catch (Exception e) {
				answer.setText(e.getLocalizedMessage());
			}
		} else if (command.equals("C")) {
			calc.clear();
			expression.setText(calc.infixExp);
			postfix.setText(calc.postfixExp);
			answer.setText("0");
		} else {
			expression.setText(expression.getText() + command);
		}
	}

	/**
	 * Toggle the info expression in the calculator's status area between the
	 * author and version information.
	 */
	private void showInfo()
	{
		if(showingAuthor)
			status.setText(calc.getVersion());
		else
			status.setText(calc.getAuthor());

		showingAuthor = !showingAuthor;
	}
}
