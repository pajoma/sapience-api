package sapience.lookup.exceptions;


/**
 * This Exception is thrown if a record could not be added.
 * 
 * @author Henry Michels
 *
 */
public class FailedAddRecordException extends FailedUpdateException {

	private static final long serialVersionUID = -598915720582706412L;

	private String message = "Record could not be added!";
	
	private String action = "session.save()";
	
	@Override
	public String getMessage() {
		return renderMessage(message, action);
	}
}
