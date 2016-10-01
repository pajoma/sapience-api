/**
 * 
 */
package sapience.annotations.exceptions;

/**
 * This Exception is a superclass for all database exceptions.
 * 
 * @author Henry
 *
 */
public abstract class DatabaseException extends Exception {

	/**
	 * 
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
