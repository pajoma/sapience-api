package sapience.annotations.exceptions;

/**
 * This exception is thrown if the system could not update a record.
 * @author Henry
 *
 */
public class UpdateRecordFailedException extends DatabaseException {

	/**
	 * 
	 */
	public UpdateRecordFailedException() {
		super();
	}

	/**
	 * @param message
	 */
	public UpdateRecordFailedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UpdateRecordFailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UpdateRecordFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
