package sapience.lookup.exceptions;

import sapience.lookup.hibernate.model.DatabaseEntity;

/**
 * This exception is thrown if the system tries to access a table using the wrong entity.
 * 
 * @author Henry Michels
 *
 */
public class WrongEntityException extends DatabaseException {

	private static final long serialVersionUID = 4904269797721933054L;
	
	private DatabaseEntity entity;
	
	/**
	 * Constructor, which gets a database entity
	 * @param entity
	 */
	public WrongEntityException(DatabaseEntity entity) {
		this.entity = entity;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("Entity '");
		sb.append(entity.getClass().getName());
		sb.append("' is not provided by this controller!");
		return sb.toString();
	}
}
