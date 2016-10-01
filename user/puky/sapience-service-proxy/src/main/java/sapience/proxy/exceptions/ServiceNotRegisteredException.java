package sapience.proxy.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ServiceNotRegisteredException extends IOException {

	private final String sid;

	public ServiceNotRegisteredException(String sid) {
	    this.sid = sid;

	}

	private static final long serialVersionUID = 4753196142010426357L;
	
	@Override
	public String getMessage() {
    		return "A service with the ID ["+sid+"] is not registered";
	}

}
