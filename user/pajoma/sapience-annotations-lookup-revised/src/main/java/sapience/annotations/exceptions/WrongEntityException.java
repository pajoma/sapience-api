/**
 * 
 */
package sapience.annotations.exceptions;

/**
 * This exception is thrown if the system tries to access a table using the wrong entity.
 * 
 * @author Henry
 *
 */
public class WrongEntityException extends DatabaseException {

	private final String name;
	private final String expected;

	public WrongEntityException(String name, String expected) {
		this.expected = expected;
		this.name = name;
	}
	
	public String getMessage() {
		return name+"is the wrong entity. I expected :"+expected; 
	}
}
