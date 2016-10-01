/**
 * 
 */
package sapience.annotations.exceptions;

/**
 * This exception is thrown if the system tries to access a table which doesn't exist
 * 
 * @author Henry
 *
 */
public class TableNotExistException extends DatabaseException {
	private static final long serialVersionUID = 874859068241377837L;
	
	private final String name;

	public TableNotExistException(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return "The following table does not exist: ".concat(name);
	}
}
