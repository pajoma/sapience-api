package sapience.annotations.exceptions;

/**
 * This Exception is thrown if a record could not be added.
 * 
 * @author Henry
 *
 */
public class AddRecordFailedException extends DatabaseException {

	/**
	 * 
	 */
	public AddRecordFailedException() {
		super();
	}

	/**
	 * @param message
	 */
	public AddRecordFailedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AddRecordFailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AddRecordFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
