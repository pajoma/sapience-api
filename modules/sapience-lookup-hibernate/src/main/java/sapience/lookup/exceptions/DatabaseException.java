package sapience.lookup.exceptions;

import java.io.IOException;

/**
 * This Exception is a superclass for all database exceptions.
 * 
 * @author Henry Michels
 *
 */
public class DatabaseException extends Exception {

	private static final long serialVersionUID = -6900368471853550881L;

	/**
	 * empty Constructor
	 */
	public DatabaseException() {
		super();
	}

	/**
	 * @param message
	 */
	public DatabaseException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DatabaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
