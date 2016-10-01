package sapience.annotations.exceptions;

/**
 * This Exception is thrown if a table could not be created.
 * 
 * @author Henry
 *
 */
public class CreationTableFailedException extends DatabaseException {

	/**
	 * 
	 */
	public CreationTableFailedException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CreationTableFailedException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public CreationTableFailedException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CreationTableFailedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
