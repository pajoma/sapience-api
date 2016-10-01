package sapience.lookup.exceptions;


/**
 * @author Henry Michels
 * 
 * This Exception is a superclass for the Exceptions concerning errors produced by update or add actions.
 *
 */
public abstract class FailedUpdateException extends DatabaseException {
	
	private static final long serialVersionUID = 4901601844649380520L;

	/**
	 * @return Exception message
	 */
	public abstract String getMessage(); 
	
	/**
	 * This method implements the creation of a well defined Exception message.
	 * 
	 * @param message
	 * @param method
	 * @return Exception message
	 */
	protected String renderMessage(String message, String method) {
		StringBuffer sb = new StringBuffer();
		sb.append(message);
		sb.append("\n Hibernate method: ");
		sb.append(method);
		return sb.toString();
	}

}
