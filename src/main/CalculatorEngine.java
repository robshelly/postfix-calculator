package main;

import java.util.HashMap;
import java.util.Stack;
import com.google.common.primitives.Doubles;

/**
 * The main part of the calculator doing the calculations.
 * 
 * @author Rob Shelly
 * @version 0.1 (incomplete)
 */
public class CalculatorEngine {

	String infixExp;
	String postfixExp;
	double answer;
	static boolean DEBUG = false;
	static HashMap<Character, Integer> PRIORITIES = new HashMap<>();

	static {
		// Unary Operators
		PRIORITIES.put('n', 4);
		// Binary Operators
		PRIORITIES.put('^', 4);
		PRIORITIES.put('*', 3);
		PRIORITIES.put('/', 3);
		PRIORITIES.put('+', 2);
		PRIORITIES.put('-', 2);
		// Closing Parenthesis
		PRIORITIES.put('(', 1);
	}

	/**
	 * Create a CalcEngine instance. Initialise its state so that it is ready for
	 * use.
	 */
	public CalculatorEngine() {
		infixExp = "";
		answer = 0;
	}

	/**
	 * Return the expression that should currently be displayed on the calculators
	 * input display.
	 */
	public String getExpression() {
		return (infixExp);
	}

	/**
	 * Return the answer that should currently be displayed on the calculator
	 * 
	 * @return
	 */
	public double getAnswer() {
		return (answer);
	}

	/**
	 * The 'C' (clear) button was pressed.
	 */
	public void clear() {
		answer = 0;
		infixExp = "";
		postfixExp = "";
	}

	/**
	 * Return the title of this calculation engine.
	 */
	public String getTitle() {
		return ("My Calculator");
	}

	/**
	 * Return the author of this engine. This string is displayed as it is, so it
	 * should say something like "Written by H. Simpson".
	 */
	public String getAuthor() {
		return ("Casio");
	}

	/**
	 * Return the version number of this engine. This string is displayed as it
	 * is, so it should say something like "Version 1.1".
	 */
	public String getVersion() {
		return ("Ver. 1.0");
	}

	/**
	 * Takes in an mathematical exression and evaluates it.
	 * 
	 * @param expression
	 *          The expression to evaluate.
	 * @return True if expression was successfully evaulated.
	 * @throws Exception
	 *           If the expression cannot be calculate due to a mathamtical error.
	 */
	public boolean evaluate(String expression) throws Exception {

		checkParenthesis(expression);
		infixExp = expression.replaceAll("\\s", "");
		postfixExp = infixToPostfix(infixExp);
		answer = calculateAnswer();
		return true;
	}

	/**
	 * Calculates the answer to a postfix expression
	 * 
	 * @return The answer
	 * @throws Exception
	 */
	private double calculateAnswer() throws Exception {

		if (DEBUG)
			System.out.println("Evalutaing answer");

		Stack<Double> operandStack = new Stack<>();
		String[] postfixTokens = postfixExp.split("\\s");

		for (String str : postfixTokens) {

			if (DEBUG) System.out.println(str);
			if (DEBUG) System.out.println("Current Token ##" + str + "##");

			// If operand, push
			if (Doubles.tryParse(str) != null) {
				if (DEBUG)
					System.out.println("Pushing " + str + " to operand stack");
				operandStack.push(Double.parseDouble(str));
				
			// If unary operators pop once and evaluate
			} else if (str.equals("n") ) {
				
				// Ensure valid number of operands on stack
				if (operandStack.size() >= 1) {
					double operand = operandStack.pop();
					operandStack.push(operand * -1);
					
				} else throw new Exception("Syntax Error");
				
			// If binary operator, pop twice and evaluate
			} else {
				
				// Ensure valid number of operands on stack
				if (operandStack.size() >= 2) {
					
					Double operand2 = operandStack.pop();
					Double operand1 = operandStack.pop();
	
					// Evaluate according to operator, push answer
					if (str.equals("+"))
						operandStack.push(plus(operand1, operand2));
					else if (str.equals("-"))
						operandStack.push(minus(operand1, operand2));
					else if (str.equals("*"))
						operandStack.push(multiply(operand1, operand2));
					else if (str.equals("/"))
						operandStack.push(divide(operand1, operand2));
					else if (str.equals("^"))
						operandStack.push(power(operand1, operand2));
					
				} else throw new Exception("Syntax Error");
			}
		}

		return operandStack.pop();
	}

	/**
	 * Calculates the sum of two doubles.
	 * 
	 * @param augend
	 *          The first addend.
	 * @param addend
	 *          The second addend.
	 * @return The sum of the operators.
	 */
	private double plus(double augend, double addend) {
		return augend + addend;
	}

	/**
	 * Calculates the difference between two doubles.
	 * 
	 * @param minuend
	 *          The minuend.
	 * @param subtrahend
	 *          The subtrahend.
	 * @return The difference between the operators.
	 */
	private double minus(double minuend, double subtrahend) {
		return minuend - subtrahend;
	}

	/**
	 * Calculates the product of two doubles
	 * 
	 * @param multiplicand
	 *          The multiplicand.
	 * @param multiplier
	 *          The multiplier.
	 * @return The product of the operators.
	 */
	private double multiply(double multiplier, double multiplicand) {
		return multiplier * multiplicand;
	}

	/**
	 * Calculates the quotient of two doubles
	 * 
	 * @param dividend
	 *          The dividend.
	 * @param divisor
	 *          The divisor.
	 * @return
	 */
	private double divide(double dividend, double divisor) throws Exception {
		if (divisor == 0)
			throw new IllegalArgumentException("Error: Division by 0");
		return dividend / divisor;
	}

	/**
	 * Calculates the power of a double
	 * 
	 * @param base
	 *          The base
	 * @param exponent
	 *          The exponent
	 * @return
	 */
	private double power(double base, double exponent) {
		return Math.pow(base, exponent);
	}

	/**
	 * Converts an infix expression to postfix. Space separate all operand and/or
	 * operators in order to distinguish multi-digit numbers.
	 * 
	 * @param infixExp The infix exression to be converted.
	 * @return The postfix version of the expression.
	 * @throws Exception
	 */
	private String infixToPostfix(String infixExp) throws Exception {

		Stack<Character> operatorStack = new Stack<>();
		String postfixExp = "";
		char previousToken = ' ';
		
		// Conversion to postfix is done using the following algorithm
		// Iterate through infix expression by character c
		//    If c is operand, add to postfix exp
		//    If c is unary operator, add to postix exp
		//    If c is opening parenthesis, push to stack
		//    If c is closing parenthesis,
		//       Pop from stack and add to postix exp
		//       until opening parenthsis. Discard both parenthesis
		//    If c is any other operator
		//       Pop from stack while top of stack contains higher
		//       priority operator
		//    After iteration, pop all remaining operators from stack
		//    adding to postfix exp

		for (Character c : infixExp.toCharArray()) {

			
			// Note: Decimal point will be considered an operand
			// as it is part of an operand
			if (Character.isDigit(c) || c == '.') {
				
				// If previous token was a closing parenthesis there 
				// is an implied multiplication symbol between parenthesis
				// and operand i.e. (A+B)C <=> (A+B)*C
				if (previousToken == ')') operatorStack.push('*');
				
				// Add a space to postfix expression if current token
				// is separate operand from previous
				// Don't add space if token is part of previous operand
				// i.e. part of a multi-digit number
				if (!(Character.isDigit(previousToken) || previousToken == '.')) {
					postfixExp += " ";
				}
				postfixExp += c;
				if (DEBUG)
					System.out.println("Postfix:\tAdding: " + c);

			// Token is an operator
			} else {
				
				// Check for unary positive or negative operators
				// This occurs when current token is a plus or minus and
				// it is the first token or
				// the previous token was also an operator to
				// the previous token was a opening parenthesis
				if ( (c == '+' || c == '-') &&
						(previousToken == ' ' || 
						(!Character.isDigit(previousToken) && previousToken != ')')) ) {
					
					// Unary positive makes no change...ignore it... 

					// Repesent unary negative with letter n in postfix notation
					if (c == '-') operatorStack.push('n');

				
				// After checking for unary operators, any other instance
				// of two operators in sequence is an illegal input
				} else if (c != '(' &&previousToken != ' ' && 
						!Character.isDigit(previousToken) && previousToken != ')') {
					throw new Exception("Operator Syntax Error");

				// If token is an opening parenthesis
				} else if (c == ('(')) {
					
					// If previous token was a number, there is implied
					// multiplication between number and opening bracket
					if (Character.isDigit(previousToken)) {
						operatorStack.push('*');
					}					
					operatorStack.push(c);
					if (DEBUG)
						System.out.println("Stack\tPushing: (");
					
				// If token is a closing parenthesis
				} else if (c == (')')) {

					// Until an opening bracket is found on the stack...
					while (operatorStack.peek() != '(') {

						// ... pop from stack, append to postfix expression
						if (DEBUG)
							System.out.println("Postfix:\tAdding Pop: " + operatorStack.peek());
						postfixExp += " " + operatorStack.pop();
					}
					// Top of stack should be now be an opening bracket
					// Discard it
					operatorStack.pop();
					if (DEBUG)
						System.out.println("Stack\tDiscarding: (");

					// For any other operator
				} else {

					// Pop from stack until operator on top of stack has
					// lower priority than the current token
					while (!operatorStack.isEmpty() && PRIORITIES.get(c) <= PRIORITIES.get(operatorStack.peek())) {
						// Pop from stack, append to postfix expression
						if (DEBUG)
							System.out.println("Postfix:\tAdding Pop: " + operatorStack.peek());
						postfixExp += (" " + operatorStack.pop());
					}

					// Push token to stack
					operatorStack.push(c);
					if (DEBUG)
						System.out.println("Stack\tPushing: " + c);
				}
			}
			previousToken = c;
		}
		
		// Pop remaining operators, append to postfix expression
		while (!operatorStack.isEmpty())
			postfixExp += (" " + operatorStack.pop());
		// Trim postfix expression to remove leading space
		postfixExp = postfixExp.trim();

		if (DEBUG)
			System.out.println("Postfix Expression: " + postfixExp);
		return postfixExp;

	}

	/**
	 * Checks infix expressions for correctly matched parenthesis.
	 * 
	 * @param infixExp
	 *          The expression to check.
	 * @return True if parenthesis correctly mathced.
	 * @throws Exception
	 *           If parenthesis are mismatched.
	 */
	public boolean checkParenthesis(String infixExp) throws Exception {

		Stack<Character> parenthesisStack = new Stack<>();

		for (char c : infixExp.toCharArray()) {

			// When current token is an opening parenthesis
			if (c == '(') {
				// push to stack
				parenthesisStack.push(c);

				// When current token is closing parenthesis
			} else if (c == ')') {
				// if stack is empty, there are mismatched parenthesis
				if (parenthesisStack.isEmpty()) {
					throw new Exception("Mismatched Parenthesis");
				} else {
					// Else, pop opening parenthesis from stack
					parenthesisStack.pop();
				}
			}
		}
		// When finished, if stack is not empty there are
		// mismatched parenthesis
		if (!parenthesisStack.isEmpty()) {
			throw new Exception("Mismatched Parenthesis");
		}

		return true;
	}
}