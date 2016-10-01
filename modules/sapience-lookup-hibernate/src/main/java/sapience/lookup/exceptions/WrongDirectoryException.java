package sapience.lookup.exceptions;

/**
 * @author Henry Michels
 *
 */
public class WrongDirectoryException extends Exception {
	
	private static final long serialVersionUID = -7617605026070978583L;
	
	private String path;
	
	/**
	 * Constructor, which gets a path as a String
	 * @param path
	 */
	public WrongDirectoryException(String path){
		this.path = path;
	}
	
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("Directory '");
		sb.append(this.path);
		sb.append("' couldn't be found");
		return sb.toString();
	}

}
