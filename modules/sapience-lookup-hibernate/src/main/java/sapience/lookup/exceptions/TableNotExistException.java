package sapience.lookup.exceptions;


/**
 * This exception is thrown if the system tries to access a table which doesn't exist
 * 
 * @author Henry Michels
 *
 */
public class TableNotExistException extends DatabaseException {

	private static final long serialVersionUID = 6605394999466548343L;
	
	private String tableName;
	
	/**
	 * Constructor, which gets a table name
	 * @param tableName
	 */
	public TableNotExistException(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("Table '");
		sb.append(tableName);
		sb.append("' doesn't exist!");
		return sb.toString();
	}
	
}
