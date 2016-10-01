package sapience.lookup.exceptions;

/**
 * This exception is thrown if the system could not update a record.
 * @author Henry Michels
 *
 */
public class FailedUpdateRecordException extends FailedUpdateException {

	private static final long serialVersionUID = -3390577402231096401L;

	private String message = "Record could not be updated!";
	
	private String action = "session.update()";
	
	@Override
	public String getMessage() {
		return renderMessage(message, action);
	}
	
}
