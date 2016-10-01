package sapience.core.exceptions;

public class ConfusingQueryParameterException extends UserInputException{
	private final String string;

	public ConfusingQueryParameterException(String string) {
		this.string = string;
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 5575757980344139793L;
	
	
	@Override
	public String getMessage() {
		return "Missing parameter: "+string;
	}
	
	

}
