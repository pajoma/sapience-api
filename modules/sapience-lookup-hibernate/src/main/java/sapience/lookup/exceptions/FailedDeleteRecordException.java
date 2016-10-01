package sapience.lookup.exceptions;

import org.hibernate.Query;

/**
 * This Exception is thrown if one or more records could not be deleted.
 * 
 * @author Henry Michels
 *
 */
public class FailedDeleteRecordException extends FailedQueryException {
	
	private static final long serialVersionUID = -2997105810116123519L;
	
	private String message = "Records could not be deleted!";
	
	/**
	 * Constructor, which gets a query
	 * @param query
	 */
	public FailedDeleteRecordException(Query query) {
		super(query);
	}

	@Override
	public String getMessage() {
		return renderMessage(query, message);
	}
	
}

