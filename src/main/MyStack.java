package main;

import java.util.LinkedList;

/**
 * @author Rob Shelly
 * 
 * @version 16 February 2016
 * 
 * An implementation of a stack collection.
 *
 * @param <T>
 */
public class MyStack<T>{
	
	private LinkedList<T> stack;

	/**
	 * Constructor for the stack object.
	 */
	public MyStack() {
		stack = new LinkedList<>();
	}
	
	/**
	 * Adds an object to the opt of the stack.
	 * 
	 * @param o The object to be pushed.
	 */
	public void push(T o) {
		stack.add(o);
	}

	/**
	 * Removes the top object from the stack returns it.
	 * 
	 * @return The popped object.
	 */
	public T pop() {
		return stack.removeLast();
	}

	/**
	 * Returns the top object from the stack without
	 * removing it.
	 * 
	 * @return The top opject on the stack.
	 */
	public T peek() {
		return stack.getLast();
	}

	/**
	 * Checks if the stack contains any objects.
	 * 
	 * @return True if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns the number of components in the stack.
	 * 
	 * @return The number of components in the stack.
	 */
	public int size() {
		return stack.size();
	}
}
