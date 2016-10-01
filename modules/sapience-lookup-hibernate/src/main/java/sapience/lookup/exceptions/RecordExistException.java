package sapience.lookup.exceptions;


/**
 * This Exception is thrown if the system trys to add a record which already exists.
 * 
 * @author Henry Michels
 *
 */
public class RecordExistException extends FailedUpdateException {

	private static final long serialVersionUID = -7721190802212634738L;

	private String message = "Record could not be added, because this record already exists!";
	
	private String action = "session.save()";
	
	@Override
	public String getMessage() {
		return renderMessage(message, action);
	}
	
}
