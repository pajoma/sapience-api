package sapience.core.exceptions;

public class MissingQueryParameterException extends UserInputException {
	private final String string;

	public MissingQueryParameterException(String string) {
		this.string = string;
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 5575757980344139793L;
	
	
	@Override
	public String getMessage() {
		return "Missing parameter: "+string;
	}
	
	

}
