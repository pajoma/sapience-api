package sapience.lookup.exceptions;

import org.hibernate.Query;

/**
 * @author Henry Michels
 * 
 * This Exception is a superclass for the Exceptions concerning errors produced by queries.
 *
 */
public abstract class FailedQueryException extends DatabaseException {
	
	private static final long serialVersionUID = 4614305141475459345L;

	protected final Query query;

	/**
	 * Constructor, which gets a query
	 * @param query
	 */
	public FailedQueryException(Query query) {
		this.query = query;
	}
	
	/**
	 * @return Exception message
	 */
	public abstract String getMessage();
	
	/**
	 * This method implements the creation of a well defined Exception message.
	 * 
	 * @param query
	 * @param message
	 * @return Exception message
	 */
	protected String renderMessage(Query query, String message) {
		StringBuffer sb = new StringBuffer();
		sb.append(message);
		sb.append("\n Query: ");
		sb.append(query.getQueryString());
		return sb.toString();
	}

}
