package sapience.lookup.exceptions;

import org.hibernate.SQLQuery;

/**
 * This Exception is thrown if a table could not be created.
 * 
 * @author Henry Michels
 *
 */
public class FailedCreateTableException extends FailedQueryException {

	private static final long serialVersionUID = -7234463405510781105L;
	
	private String message = "Table could not be created!";
	
	/**
	 * 
	 * @param query
	 */
	public FailedCreateTableException(SQLQuery query) {
		super(query);
	}

	@Override
	public String getMessage() {
		return renderMessage(query, message);
	}
	
}
