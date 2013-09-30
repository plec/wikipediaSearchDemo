/**
 * 
 */
package com.plec.wikipedia;

/**
 * Exception for dealing with search
 */
@SuppressWarnings("serial")
public class SearchException extends Exception {
	/**
	 * super class constructor
	 * @param message the message exception
	 * @param t the root cause exception
	 */
	public SearchException(String message, Throwable t) {
		super(message, t);
	}
}
