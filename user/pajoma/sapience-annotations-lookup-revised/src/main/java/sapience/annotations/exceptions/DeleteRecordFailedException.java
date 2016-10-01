package sapience.annotations.exceptions;

import org.hibernate.Query;

/**
 * This Exception is thrown if one or more records could not be deleted.
 * 
 * @author Henry
 *
 */
public class DeleteRecordFailedException extends FailedQueryException {
	public DeleteRecordFailedException(Query query) {
		super(query);
	}

	@Override
	public String getMessage() {
		return "Records could not be deleted!";
	}


	
	
}
