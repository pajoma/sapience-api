package sapience.annotations.exceptions;

import org.hibernate.Query;

public abstract class FailedQueryException extends DatabaseException {
	public abstract String getMessage(); 
	
	private final Query query;

	public FailedQueryException(Query query) {
		this.query = query;
	}
	
	protected String renderMessage(Query query) {
		StringBuffer sb = new StringBuffer();
		sb.append(getMessage());
		sb.append("\n Query: ");
		sb.append(query.getQueryString());
		return sb.toString();
	}
}
