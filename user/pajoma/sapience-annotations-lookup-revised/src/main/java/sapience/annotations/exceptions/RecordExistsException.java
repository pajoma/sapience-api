/**
 * 
 */
package sapience.annotations.exceptions;

/**
 * This Exception is thrown if the system trys to add a record which already exists.
 * 
 * @author Henry
 *
 */
public class RecordExistsException extends DatabaseException {

	/**
	 * 
	 */
	public RecordExistsException() {
		super();
	}

	/**
	 * @param message
	 */
	public RecordExistsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RecordExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RecordExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
